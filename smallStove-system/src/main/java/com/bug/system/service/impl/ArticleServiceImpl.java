package com.bug.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.framework.constant.SystemConstant;
import com.bug.framework.exception.SystemException;
import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.security.models.entity.LoginUser;
import com.bug.framework.utils.BeanCopyUtils;
import com.bug.framework.utils.KeyWordSearcher;
import com.bug.framework.utils.RedisCache;
import com.bug.framework.utils.SecurityUtils;
import com.bug.system.domain.entity.Article;
import com.bug.system.domain.entity.SysUser;
import com.bug.system.domain.entity.Type;
import com.bug.system.domain.vo.ArticleVo;
import com.bug.system.domain.vo.PageVo;
import com.bug.system.domain.vo.TypeVo;
import com.bug.system.mapper.ArticleMapper;
import com.bug.system.mapper.UserMapper;
import com.bug.system.mapper.TypeMapper;
import com.bug.system.service.ArticleService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 源
 * @description 针对表【sys_article(文章表)】的数据库操作Service实现
 * @createDate 2022-06-09 20:06:28
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RedisCache redisCache;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Result<PageVo<ArticleVo>> getArticleList(Integer currentPage, Integer pageSize, Long typeId, Long userId) {
        // TODO: 2022/6/11 优先从redis中读取文章列表信息
        // 优先从redis中读取文章列表信息

        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();

        // 是否指定了某位作者
        if (ObjectUtils.isEmpty(userId) || userId <= 0) {
            // 查询文章列表, 文章必须全部是公开的
            articleWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_PUBLIC);
        } else {
            // 查询指定作者的文章
            articleWrapper.eq(Article::getUserId, userId);

            Long id = SecurityUtils.getUserId();
            if (!SecurityUtils.isAdmin(id)) {
                // 不是管理员, 判断是否为作者本人
                if (!userId.equals(id)) {
                    // 不是本人, 查询公开文章
                    articleWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_PUBLIC);
                }
            }
        }

        // 按照时间降序排列
        articleWrapper.orderByDesc(Article::getCreateTime);
        // 是否选择了文章类别
        if (!ObjectUtils.isEmpty(typeId) && typeId > 0) {
            List<Long> articleIdList = articleMapper.selectArticleIdByTypeId(typeId);
            articleWrapper.in(Article::getArticleId, articleIdList);
        }
        // 分页查询
        Page<Article> page = new Page<>(currentPage, pageSize);
        this.page(page, articleWrapper);

        List<ArticleVo> articleVoList = BeanCopyUtils.copyAllBean(page.getRecords(), ArticleVo.class);
        for (ArticleVo articleVo : articleVoList) {
            // 设置文章的作者昵称,头像以及查询每篇文章的类型
            SysUser author = userMapper.selectById(articleVo.getUserId());
            articleVo.setNickName(author.getNickname());
            articleVo.setAvatar(author.getAvatar());
            List<Long> typeIdList = articleMapper.selectTypeIdByArticleId(articleVo.getArticleId());
            List<Type> types = typeMapper.selectBatchIds(typeIdList);
            articleVo.setTypeVoList(BeanCopyUtils.copyAllBean(types, TypeVo.class));
            //将每一个article详情存入redis中
            redisCache.hashSet(redisCache.getArticleVoKey(), articleVo.getArticleId().toString(), articleVo);
        }

        return new ResultBuilder().success("查询文章列表成功!", new PageVo<>(articleVoList, page.getTotal()));
    }

    @Override
    public Result<ArticleVo> getArticleDetails(Long articleId) {
        // 优先从redis中读取文章信息
        if (redisCache.hashHasKey(redisCache.getArticleVoKey(), articleId.toString())) {
            // 更新redis中文章列表的过期时间
            redisCache.expire(redisCache.getArticleVoKey(), redisCache.getArticleExpirationTime());
            ArticleVo articleVo = (ArticleVo) redisCache.hashGet(redisCache.getArticleVoKey(), articleId.toString());
            return new ResultBuilder().success(ResultEnum.ARTICLE_DETAILS_SUCCESS, articleVo);
        }

        // 其次从数据库中读取文章数据
        Article article = articleMapper.selectById(articleId);
        if (ObjectUtils.isEmpty(article) || SystemConstant.ARTICLE_STATUS_PRIVATE.equals(article.getStatus())) {
            return new ResultBuilder().success(ResultEnum.ARTICLE_DETAILS_FAIL);
        }
        ArticleVo articleVo = BeanCopyUtils.copySingleBean(article, ArticleVo.class);
        // 将文章数据存入redis中
        redisCache.hashSet(redisCache.getArticleVoKey(), articleId.toString(), articleVo);

        return new ResultBuilder().success(ResultEnum.ARTICLE_DETAILS_SUCCESS, articleVo);
    }

    @Transactional
    @Override
    public Result<?> publishArticle(ArticleVo articleVo) {
        // 判断文章是否含有敏感词
        List<String> titleRetrieve = KeyWordSearcher.retrieve(articleVo.getTitle());
        List<String> thumbnailRetrieve = KeyWordSearcher.retrieve(articleVo.getThumbnail());
        List<String> contentRetrieve = KeyWordSearcher.retrieve(articleVo.getContent());
        if (!ObjectUtils.isEmpty(titleRetrieve) || !ObjectUtils.isEmpty(thumbnailRetrieve) || !ObjectUtils.isEmpty(contentRetrieve)) {
            logger.info("标题中敏感词: {} -- 缩略中敏感词: {}  -- 内容中敏感词: {}", titleRetrieve, thumbnailRetrieve, contentRetrieve);
            titleRetrieve.addAll(thumbnailRetrieve);
            titleRetrieve.addAll(contentRetrieve);
            return new ResultBuilder().error(ResultEnum.PUBLISH_FAIL, titleRetrieve);
        }

        Article article = BeanCopyUtils.copySingleBean(articleVo, Article.class);
        article.setUserId(SecurityUtils.getUserId());
        article.setViewsNumber(SystemConstant.ARTICLE_VIEWS_LIKES_DEFAULT);
        article.setLikesNumber(SystemConstant.ARTICLE_VIEWS_LIKES_DEFAULT);
        article.setCreateBy(SecurityUtils.getUsername());
        article.setCreateTime(new Date());

        // 将article添加到数据库
        if (articleMapper.insert(article) <= 0) {
            // 添加失败抛出异常
            throw new SystemException(ResultEnum.ARTICLE_PUBLISH_FAIL);
        }

        Long articleId = article.getArticleId();

        // 将article的type添加到数据库
        Map<Long, Long> map = new HashMap<>();
        for (TypeVo typeVo : articleVo.getTypeVoList()) {
            map.put(typeVo.getTypeId(), articleId);
        }
        if (articleMapper.setTypeByBatch(map) <= 0) {
            // 添加失败抛出异常
            throw new SystemException(ResultEnum.ARTICLE_PUBLISH_FAIL);
        }

        return new ResultBuilder().success(ResultEnum.ARTICLE_PUBLISH_SUCCESS);
    }

    @Override
    public Result<?> updateArticle(ArticleVo articleVo, List<Long> typeIdList) {
        // TODO: 2022/6/10 修改请多加考虑, 如何提升效率，是否要传入articleId
        return null;
    }

    @Override
    public Result<?> deleteArticle(Long articleId) {
        Long userId = SecurityUtils.getUserId();
        Article article = articleMapper.selectById(articleId);

        // 判断文章是否真实存在且是否为此用户发表 管理员权限
        if (ObjectUtils.isEmpty(article) || SecurityUtils.isAdmin(userId) || !article.getUserId().equals(userId)) {
            logger.error("有人试图篡改数据库文章信息!");
            return new ResultBuilder().error(ResultEnum.ARTICLE_DELETE_FAIL);
        }

        // 删除失败
        if (articleMapper.deleteById(articleId) <= 0) {
            throw new SystemException(ResultEnum.ARTICLE_DELETE_FAIL);
        }

        return new ResultBuilder().success(ResultEnum.ARTICLE_DELETE_SUCCESS);
    }

    @Override
    public Result<?> updateViews(Long articleId) {
        // 获取redis中对应文章的浏览量(Integer)和点赞数(Integer)
        ArticleVo articleVo = (ArticleVo) redisCache.hashGet(redisCache.getArticleVoKey(), articleId.toString());
        // 更新浏览量
        articleVo.setViewsNumber(articleVo.getViewsNumber() + 1);
        // 将更新后的数据存入redis中
        redisCache.hashSet(redisCache.getArticleVoKey(), articleId.toString(), articleVo);
        return new ResultBuilder().success(ResultEnum.VIEWS_SUCCESS);
    }

    @Transactional
    @Override
    public Result<?> updateLikes(Long articleId) {
        // TODO: 2022/6/12 结合redis实现点赞功能
        LoginUser loginUser = SecurityUtils.getLoginUser();

        // 检查用户对此文章的点赞状态
        if (articleMapper.selectLikesByUserIdArticleId(loginUser.getId(), articleId) <= 0) {
            // 未点赞，则新增点赞记录，并修改文章点赞数
            articleMapper.insertLikes(loginUser.getId(), articleId);
            articleMapper.updateLikesNumberByArticleId(articleMapper.selectLikesCount(articleId), articleId);
            return new ResultBuilder().success(ResultEnum.LIKES_SUCCESS);
        }

        // 已点赞，则删除点赞记录，并修改文章点赞数
        articleMapper.deleteLikes(loginUser.getId(), articleId);
        articleMapper.updateLikesNumberByArticleId(articleMapper.selectLikesCount(articleId), articleId);
        return new ResultBuilder().success(ResultEnum.LIKES_CANCEL_SUCCESS);
    }

    @Override
    public Result<?> getTags() {
        List<Type> types = typeMapper.selectList(null);

        if (ObjectUtils.isEmpty(types)) {
            return new ResultBuilder().error(ResultEnum.TAGS_GET_FAIL);
        }

        List<TypeVo> typeVoList = BeanCopyUtils.copyAllBean(types, TypeVo.class);
        return new ResultBuilder().success(ResultEnum.TAGS_GET_SUCCESS, typeVoList);
    }
}

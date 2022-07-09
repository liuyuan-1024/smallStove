package com.bug.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bug.system.domain.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author 源
 * @description 针对表【sys_article(文章表)】的数据库操作Mapper
 * @createDate 2022-06-09 20:06:28
 * @Entity com.bug.system.domain.entity.Article
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询文章类别ID
     */
    @Select("select type_id from sys_article_type where article_id =${articleId}")
    List<Long> selectTypeIdByArticleId(@Param("articleId") Long articleId);

    /**
     * 批量设置文章类别
     */
    int setTypeByBatch(@Param("map") Map<Long, Long> map);

    /**
     * 查询指定类别的文章的ID
     */
    @Select("select sa.article_id from sys_article sa join sys_article_type sat on sa.article_id = sat.article_id where type_id =${typeId}")
    List<Long> selectArticleIdByTypeId(@Param("typeId") Long typeId);

    /**
     * 修改文章点赞数
     */
    int updateLikesNumberByArticleId(@Param("likesNumber") Integer likesNumber, @Param("articleId") Long articleId);

    /**
     * 查询用户是否未指定文章点赞，返回0或1
     */
    @Select("select count(*) from sys_article_like sal where user_id=${userId} and article_id = ${articleId}")
    int selectLikesByUserIdArticleId(@Param("userId") Long userId, @Param("articleId") Long articleId);

    /**
     * 查询文章的点赞数
     */
    @Select("select count(*) from sys_article_like sal where article_id = ${articleId}")
    int selectLikesCount(@Param("articleId") Long articleId);

    /**
     * 新增点赞记录
     */
    @Insert("insert into sys_article_like(user_id,article_id) values(${userId},${articleId})")
    int insertLikes(@Param("userId") Long userId, @Param("articleId") Long articleId);

    /**
     * 删除点赞记录
     */
    @Delete("delete from sys_article_like where user_id=${userId} and article_id = ${articleId}")
    int deleteLikes(@Param("userId") Long userId, @Param("articleId") Long articleId);
}





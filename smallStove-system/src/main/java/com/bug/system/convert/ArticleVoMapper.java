package com.bug.system.convert;

import com.bug.system.domain.entity.Article;
import com.bug.system.domain.vo.ArticleVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleVoMapper {

    ArticleVoMapper INSTANCE = Mappers.getMapper(ArticleVoMapper.class);

    ArticleVo toArticleVo(Article article);
}

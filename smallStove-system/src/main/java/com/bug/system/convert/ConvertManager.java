package com.bug.system.convert;

import com.bug.system.domain.entity.Article;
import com.bug.system.domain.vo.ArticleVo;

import java.util.HashMap;
import java.util.Map;

/**
 * MapStruct的mapper的管理器
 */
public class ConvertManager {
    private static final Map<String, Class<?>> mapperMap = new HashMap<>(16);


    static {
        put(Article.class, ArticleVo.class, ArticleVoMapper.class);
    }

    private static void put(Class<?> source, Class<?> target, Class<?> mapper) {
        StringBuilder builder = new StringBuilder().append(source.getName()).append("-").append(target.getName());
        mapperMap.put(builder.toString(), mapper);
    }

    public static Class<?> getMapper(String key) {
        return mapperMap.get(key);
    }
}

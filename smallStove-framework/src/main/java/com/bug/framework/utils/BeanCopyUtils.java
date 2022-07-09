package com.bug.framework.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装Bean拷贝工具类
 */
// TODO: 2022/6/3 使用mapstruct来进行Bean拷贝
public class BeanCopyUtils {

    /**
     * 拷贝单个Bean
     */
    public static <T> T copySingleBean(Object source, Class<T> clazz) {
        // 待返回的vo对象
        T vo = null;

        try {
            // 创建vo对象
            vo = clazz.getConstructor().newInstance();
            // bean拷贝
            BeanUtils.copyProperties(source, vo);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return vo;
    }

    /**
     * 拷贝单个Bean
     */
    public static <T, V> List<T> copyAllBean(List<V> source, Class<T> clazz) {
        // 待返回的vo对象
        List<T> voList = new ArrayList<>();

        try {
            // 循环bean拷贝
            for (V v : source) {
                // 创建vo对象
                T vo = clazz.getConstructor().newInstance();
                BeanUtils.copyProperties(v, vo);
                voList.add(vo);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return voList;
    }

}

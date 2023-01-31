package com.link.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Link
 * @Description
 * @date 2022-12-27 14:42
 */
public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    /**
     * 拷贝单个Bean
     *
     * @param source
     * @param clazz
     * @param <V>
     * @return
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {

        V result = null;
        try {
            //创建目标对象
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回
        return result;
    }

    /**
     * 拷贝Bean的集合
     *
     * @param list
     * @param clazz
     * @param <O>
     * @param <V>
     * @return
     */
    public static <O, V> List<V> copyListBean(List<O> list, Class<V> clazz) {
        return list.stream().map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}

package com.hdiata.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 复制对象或集合属性
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 17:09
 */
public class BeanCopyUtils {

    /**
     * 复制对象
     * @param source 源
     * @param target 目标
     * @return {@link T}
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 拷贝集合
     * @param source 源
     * @param target 目标
     * @return {@link List <T>} 集合
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtils.copyObject(obj, target));
            }
        }
        return list;
    }

}

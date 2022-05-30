package com.hdiata.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * @return 操作类型
     */
    String optType() default "";

}

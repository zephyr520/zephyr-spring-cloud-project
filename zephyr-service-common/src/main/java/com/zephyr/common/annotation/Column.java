package com.zephyr.common.annotation;


import java.lang.annotation.*;

/**
 * @author enpy cheung
 * @DATE 2018/12/12
 * 字段属性描述
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    /**
     * 字段描述
     * @return
     */
    String desc() default "";
}

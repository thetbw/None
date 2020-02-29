package xyz.thetbw.blog.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {

    /**
     * 允许访问的次数
     * @return
     */
    int count() default Integer.MAX_VALUE;

    /**
     * 时间，单位为秒
     * @return
     */
    long time() default 60000;

}

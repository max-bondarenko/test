package org.max.spring.data.config.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.QueryAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Maksym_Bondarenko on 2/12/2016.
 */
@QueryAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DruidQuery {

    @AliasFor("templateName")
    String value() default "";
    @AliasFor("value")
    String templateName() default "";

    String dataSource() default "";
}

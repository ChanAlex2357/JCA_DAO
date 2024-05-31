package jca.dao.models.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jca.dao.helpers.StringParser;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityModels {
    public String name() default "";
    public String parseOption() default StringParser.NORMAL;
}

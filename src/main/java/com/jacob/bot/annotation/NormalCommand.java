package com.jacob.bot.annotation;

import org.springframework.stereotype.Controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jacob
 * 普通命令,格式"/anyCommand"
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Controller
public @interface NormalCommand {

    public String name() default "";

    public String description() default "";

}

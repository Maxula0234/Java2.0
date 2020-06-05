package org.levelup.lessons.lesson4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Target
//Retention
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomInt {
    //min,max
    int min() default 0;
    int max();
}

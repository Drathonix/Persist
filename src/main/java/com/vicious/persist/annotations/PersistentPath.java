package com.vicious.persist.annotations;

import com.vicious.persist.shortcuts.NotationFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface PersistentPath {
    NotationFormat value() default NotationFormat.GON;
}

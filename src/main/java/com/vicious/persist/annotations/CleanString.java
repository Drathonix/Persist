package com.vicious.persist.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface CleanString {
    int maxLength() default Integer.MAX_VALUE;
    Replacement[] replacements() default {};
    @interface Replacement {
        String target();
        String replacement();
    }
}

package com.vicious.persist.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Warning: This does not work yet for all object types. If an object is not immutable then this setting may not work.
 * In addition, when used non-statically the declaring class must have a default constructor.
 * Known working objects: All primitives, Strings, Immutable Maps and Collections. Arrays are the exception to the
 * immutability rule as persist always re-instantiates them when unmapping then.
 * If a field has this annotation it will not be saved if its current value is equal to the initial value of the field.
 * The field should be initialized for this to work, although nulls are supported.
 * This reduces the amount of information needed to save an object and can make user accessed files easier to read.
 * @since 1.4.8
 * @author Jack Andersen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface DoNotSaveDefault {
}

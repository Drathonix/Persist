package com.vicious.persist.mappify.reflect;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Builds an {@link FieldData} instance without prematurely throwing errors.
 * @author Jack Andersen
 * @since 1.5.0
 * @param <T>
 */
public class FieldDataBuilder<T extends AccessibleObject & Member> {
    private T getter;
    private Method setter;
    public FieldDataBuilder() {}

    /**
     * Sets the getter if it isn't present yet.
     * @param getter the new getter.
     */
    public void getterNoOverride(Object getter) {
        if(this.getter == null) {
            if(setter != null){
                if(!ClassData.staticMatches((T)getter,setter)){
                    return;
                }
            }
            this.getter = (T)getter;
        }
    }

    /**
     * Sets the setter method if it isn't present yet.
     * @param setter the new setter.
     */
    public void setterNoOverride(Method setter) {
        if(this.setter == null) {
            if(getter != null){
                if(!ClassData.staticMatches(getter,setter)){
                    return;
                }
            }
            this.setter = setter;
        }
    }

    @Override
    public String toString() {
        return "FieldDataBuilder{" +
                "getter=" + getter +
                ", setter=" + setter +
                '}';
    }

    /**
     * Builds the {@link FieldData} instance, may throw an error due to a missing getter or some other invalid data.
     * @param source the source object, could be a static class or instance.
     * @param hasInitializer whether the declaring class has a valid initializer in {@link com.vicious.persist.mappify.registry.Initializers}
     * @return A complete FieldData.
     */
    public @NotNull FieldData<T> build(Object source, boolean hasInitializer) {
        return new FieldData<>(source,getter,setter,hasInitializer);
    }

    public static class Builders extends LinkedHashMap<String,FieldDataBuilder> {
        public FieldDataBuilder<?> getBuilder(String name){
            return computeIfAbsent(name,s->new FieldDataBuilder<>());
        }
    }
}

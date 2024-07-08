package com.vicious.persist.io.writer;

import org.jetbrains.annotations.NotNull;

public class WrappedObject {
    public final Object object;
    public final String comment;

    public WrappedObject(Object object) {
        this.object = object;
        comment = "";
    }

    public WrappedObject(Object object, @NotNull String comment) {
        this.object = object;
        if(comment == null){
            throw new IllegalArgumentException("Comment cannot be null, use empty string instead.");
        }
        this.comment = comment;

    }

    public static WrappedObject of(Object object) {
        return new WrappedObject(object);
    }

    public static WrappedObject of(Object object, String comment) {
        return new WrappedObject(object, comment);
    }

    public static Object unwrap(Object o){
        if(o instanceof WrappedObject){
            return ((WrappedObject) o).object;
        }
        else{
            return o;
        }
    }

    public static String unwrapComment(Object o){
        if(o instanceof WrappedObject){
            return ((WrappedObject) o).comment;
        }
        else{
            return "";
        }
    }
}

package com.vicious.persist.mappify;

import com.vicious.persist.util.ClassMap;

public class Context {
    private static final ClassMap<ClassData> classData = new ClassMap<>();

    public final Class<?> type;
    public final boolean isStatic;
    public final Object source;
    public final ClassData data;

    protected Context(Object source){
        this.isStatic = source instanceof Class<?>;
        this.type = isStatic ? (Class<?>)source : source.getClass();
        this.source=source;
        this.data = getClassData(this);
    }

    public static Context of(Object source){
        return new Context(source);
    }

    public static synchronized ClassData getClassData(Context context) {
        return classData.computeIfAbsent(context.getType(), ClassData::new);
    }

    public Class<?> getType(){
        return type;
    }

    public void forEach(ClassData.FieldAccessor fieldAccessor) {
        data.forEach(source, isStatic, fieldAccessor::access);
    }
}

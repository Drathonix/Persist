package com.vicious.persist.mappify.registry;

import com.vicious.persist.util.ClassMap;

import java.util.UUID;
import java.util.function.Function;

public class Stringify {
    private static final ClassMap<Converter<?>> converters = new ClassMap<>();

    static {
        //noinspection unchecked
        register(Byte::parseByte, Object::toString, byte.class, Byte.class);
        //noinspection unchecked
        register(Short::parseShort, Object::toString, short.class, Short.class);
        //noinspection unchecked
        register(Integer::parseInt, Object::toString, int.class, Integer.class);
        //noinspection unchecked
        register(Long::parseLong, Object::toString, long.class, Long.class);
        //noinspection unchecked
        register(Float::parseFloat, Object::toString, float.class, Float.class);
        //noinspection unchecked
        register(Double::parseDouble, Object::toString, double.class, Double.class);
        //noinspection unchecked
        register(Boolean::parseBoolean, Object::toString, boolean.class, Boolean.class);
        //noinspection unchecked
        register(str->str.charAt(0), Object::toString, char.class, Character.class);
        register(String.class,str->str,str->str);
        register(UUID.class,UUID::fromString,UUID::toString);
        register(Class.class,str-> {
            try {
                return Class.forName(str);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, Class::getName);

    }

    private static <E,T extends E> void register(Function<String,T> stringToObject, Function<T,String> objectToString, Class<T>... classes) {
        for (Class<T> aClass : classes) {
            converters.put(aClass,new Converter<>(stringToObject,objectToString));
        }
    }

    public static <T> void register(Class<T> cls, Function<String,T> stringToObject, Function<T,String> objectToString) {
        converters.put(cls,new Converter<>(stringToObject,objectToString));
    }

    @SuppressWarnings("unchecked")
    public static <T> String stringify(T obj) {
        if(obj == null){
            return "null";
        }
        Class<?> cls = obj.getClass();
        Converter<T> converter = (Converter<T>) converters.get(cls);
        if (converter == null) {
            if(obj instanceof Enum<?>){
                return "\"" + ((Enum<?>) obj).name() + "\"";
            }
            throw new IllegalArgumentException("No toString converter registered for " + cls);
        }
        else{
            return converter.objectToString.apply(obj);
        }
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static <T> T objectify(Class<T> cls, String str) {
        if(str.equals("null")){
            return null;
        }
        if(cls.isEnum()){
            return (T)Enum.valueOf((Class)cls,str);
        }
        Converter<T> converter = (Converter<T>) converters.get(cls);
        if (converter == null) {
            throw new IllegalArgumentException("No toObject converter registered for " + cls);
        }
        else{
            return converter.stringToObject.apply(str);
        }
    }

    public static boolean present(Class<?> cls) {
        return converters.containsKey(cls);
    }

    private static class Converter<T> {
        Function<String,T> stringToObject;
        Function<T,String> objectToString;
        
        public Converter(Function<String,T> stringToObject, Function<T,String> objectToString) {
            this.stringToObject = stringToObject;
            this.objectToString = objectToString;
        }
    }
}
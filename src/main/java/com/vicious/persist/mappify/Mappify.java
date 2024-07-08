package com.vicious.persist.mappify;

import com.vicious.persist.annotations.Range;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.io.writer.WrappedObject;

import java.lang.reflect.Field;
import java.util.*;

public class Mappify {
    public static Map<String, WrappedObject> mappify(Object object){
        Context context = Context.of(object);
        Map<String,WrappedObject> map = new HashMap<>();
        mappify(map,context);
        return map;
    }

    private static void mappify(Map<String,WrappedObject> map, Context context){
        context.forEach((field, value, saveData, rangeData, typing) -> {
            String key = nameOf(field,saveData);
            WrappedObject mappifiedValue = mappifyValue(field,value,saveData,rangeData,typing);
        });
    }

    private static WrappedObject mappifyValue(Field field, Object value, Save saveData, Range rangeData, Class<?>[] typing) {
        if(value == null){
            return WrappedObject.of(null);
        }
        if(value instanceof Collection<?>){
            return WrappedObject.of(mappifyCollection((Collection<?>)value,field,saveData,typing),saveData != null ? saveData.description() : "");
        }
        else if(value instanceof Map<?,?>){

        }
        else{

        }
    }

    private static WrappedObject mappifyInternalValue(Object value, Class<?> targetType, Class<?>[] typing) {
        if(value == null){
            return WrappedObject.of(null);
        }
        if(value instanceof Collection<?>){
            return WrappedObject.of(mappifyCollection((Collection<?>)value,typing));
        }
        else if(value instanceof Map<?,?>){

        }
        else{
            Class<?> type = value.getClass();
            if(targetType != type && !type.isPrimitive()){
                Map<String,WrappedObject> map = mappify(value);
                map.put(Reserved.C_NAME,WrappedObject.of(type.getName(),"DO NOT EDIT"));
                return WrappedObject.of(map);
            }
            else if(Stringify.present(type)){
                return WrappedObject.of(Stringify.stringify(value));
            }
            else {
                return WrappedObject.of(mappify(value));
            }
        }
    }

    private static Collection<WrappedObject> mappifyCollection(Collection<?> value, Class<?>[] typing) {
        Collection<WrappedObject> collection = new ArrayList<>();
        for(Object o : value){
            collection.add(mappifyInternalValue(o, typing[0], typing.length >= 1 ? Arrays.copyOfRange(typing,1,typing.length) : new Class[0]));
        }
        return collection;
    }

    private static String nameOf(Field field, Save data){
        if(data.value().isEmpty()){
            return field.getName();
        }
        else{
            return data.value();
        }
    }
}

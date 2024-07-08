package com.vicious.persist.mappify;

import com.vicious.persist.annotations.Range;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ClassData {
    private final Map<String, FieldData> savableFields = new HashMap<>();
    public ClassData(Class<?> cls){
        for (Field field : cls.getFields()) {
            if(field.isAnnotationPresent(Save.class)){
                savableFields.put(field.getName(), new FieldData(field));
            }
        }
    }

    public void whenPresent(String name, Object source, boolean isStatic, FieldAccessor accessor){
        if(savableFields.containsKey(name)){
            FieldData field = savableFields.get(name);
            try {
                if(field.matchesStaticness(isStatic)) {
                    accessor.access(field.field, field.field.get(source), field.saveData, field.rangeData, field.typing != null ? field.typing.value() : new Class[0]);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void forEach(Object source, boolean isStatic, FieldAccessor accessor){
        savableFields.forEach((name, field) -> {
            try {
                if(field.matchesStaticness(isStatic)) {
                    accessor.access(field.field, field.field.get(source), field.saveData, field.rangeData, field.typing.value());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FunctionalInterface
    public interface FieldAccessor{
        void access(Field field, Object value, Save saveData, Range rangeData, Class<?>[] typing);
    }

    private static class FieldData {
        private final Field field;
        private final Save saveData;
        private final Range rangeData;
        private final Typing typing;

        public FieldData(Field field) {
            this.field = field;
            this.saveData=field.getAnnotation(Save.class);
            this.rangeData=field.getAnnotation(Range.class);
            this.typing=field.getAnnotation(Typing.class);
        }

        public boolean matchesStaticness(boolean isStatic) {
            return (isStatic && Modifier.isStatic(field.getModifiers())) || (!isStatic && !Modifier.isStatic(field.getModifiers()));
        }
    }
}

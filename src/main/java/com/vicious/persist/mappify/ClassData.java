package com.vicious.persist.mappify;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.except.InvalidSavableElementException;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClassData {
    private final Map<String, FieldData<?>> savableFields = new HashMap<>();
    public ClassData(Class<?> cls){
        for (Field field : cls.getDeclaredFields()) {
            Save save = field.getAnnotation(Save.class);
            if(save != null){
                String name = save.value().isEmpty() ? field.getName() : save.value();
                Method setter = null;
                for (Method declaredMethod : cls.getDeclaredMethods()) {
                    Save.Setter saveSetter = declaredMethod.getAnnotation(Save.Setter.class);
                    if(saveSetter != null){
                        if(saveSetter.value().equals(name) && staticMatches(field,declaredMethod)){
                            setter = declaredMethod;
                            break;
                        }
                    }
                }
                savableFields.put(name, new FieldData<>(field,setter));
            }
        }

        for (Method m1 : cls.getDeclaredMethods()) {
            Save save = m1.getAnnotation(Save.class);
            if(save != null){
                String name = save.value().isEmpty() ? m1.getName() : save.value();
                Method setter = null;
                for (Method declaredMethod : cls.getDeclaredMethods()) {
                    Save.Setter saveSetter = declaredMethod.getAnnotation(Save.Setter.class);
                    if(saveSetter != null){
                        if(saveSetter.value().equals(name) && staticMatches(m1,declaredMethod)){
                            setter = declaredMethod;
                            break;
                        }
                    }
                }
                if(savableFields.containsKey(m1.getName())){
                    String msg = "Method " + m1.getName() + " (with @Save name of " + name + ") overrides a field with the same @Save name. You should remove the @Save annotation on the field.";
                    throw new InvalidSavableElementException(msg);
                }
                savableFields.put(name, new FieldData<>(m1,setter));
            }
        }
    }

    private boolean staticMatches(Member m1, Method declaredMethod) {
        return (Modifier.isStatic(m1.getModifiers()) && Modifier.isStatic(declaredMethod.getModifiers()))
                || (!Modifier.isStatic(m1.getModifiers()) && !Modifier.isStatic(declaredMethod.getModifiers()));
    }

    public void forEach(boolean isStatic, Consumer<FieldData<?>> accessor){
        savableFields.forEach((name, field) -> {
            if(field.matchesStaticness(isStatic)) {
                accessor.accept(field);
            }
        });
    }

    public boolean hasTraitsInContext(boolean isStatic) {
        for (FieldData<?> value : savableFields.values()) {
            if(value.matchesStaticness(isStatic)) {
                return true;
            }
        }
        return false;
    }
}

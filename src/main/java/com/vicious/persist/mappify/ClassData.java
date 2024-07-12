package com.vicious.persist.mappify;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.except.InvalidSavableElementException;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClassData {
    private final Map<String, FieldData<?>> savableFields = new HashMap<>();
    private static void forEach(Class<?> cls, Consumer<Class<?>> consumer){
        if(cls != null){
            consumer.accept(cls);
            for (Class<?> anInterface : cls.getInterfaces()) {
                consumer.accept(anInterface);
            }
            forEach(cls.getSuperclass(), consumer);
        }
    }

    public ClassData(Class<?> c){
        forEach(c,cls->{
            for (Method m1 : cls.getDeclaredMethods()) {
                Save save = m1.getAnnotation(Save.class);
                if(save != null){
                    String name = save.value().isEmpty() ? m1.getName() : save.value();
                    if(Modifier.isAbstract(m1.getModifiers())){
                        throw new InvalidSavableElementException("Abstract method " + m1.getName() + " in " + m1.getDeclaringClass() + " @Save(\"" + name + "\"), this is illegal. Maybe you should create a wrapper method instead.");
                    }
                    if(savableFields.containsKey(name)){
                        continue;
                    }
                    Method setter = null;
                    for (Method declaredMethod : cls.getDeclaredMethods()) {
                        Save.Setter saveSetter = declaredMethod.getAnnotation(Save.Setter.class);
                        if(saveSetter != null){
                            if(Modifier.isAbstract(declaredMethod.getModifiers())){
                                throw new InvalidSavableElementException("Abstract method " + declaredMethod.getName() + " in " + declaredMethod.getDeclaringClass() + " @Save.Setter(\"" + saveSetter.value() + "\"), this is illegal. Maybe you should create a wrapper method instead.");
                            }
                            if(saveSetter.value().equals(name) && staticMatches(m1,declaredMethod)){
                                setter = declaredMethod;
                                break;
                            }
                        }
                    }
                    savableFields.put(name, new FieldData<>(m1,setter));
                }
            }
            for (Field field : cls.getDeclaredFields()) {
                Save save = field.getAnnotation(Save.class);
                if(save != null){
                    String name = save.value().isEmpty() ? field.getName() : save.value();
                    if(savableFields.containsKey(name)){
                        continue;
                    }
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
        });
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

    public void whenPresent(String key, boolean isStatic, Consumer<FieldData<?>> consumer) {
        FieldData<?> field = savableFields.get(key);
        if(field != null && field.matchesStaticness(isStatic)) {
            consumer.accept(field);

        }
    }
}

package com.vicious.persist.mappify.reflect;

import com.vicious.persist.annotations.CleanString;
import com.vicious.persist.annotations.Range;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;
import com.vicious.persist.except.InvalidSavableElementException;
import com.vicious.persist.mappify.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a savable element's annotations and its getter and setter functions.
 * Savable elements can consist of Methods and Fields, what matters is that there is a way to get an element's value and set its value.
 * Possible scenarios:
 * Field = setter and getter
 * Field = getter, Method = setter
 * Method = getter, Field = setter
 * Method = getter, Method = setter
 *
 * @author Jack Andersen
 * @since 1.0
 * @param <T> the getter element type.
 */
public class FieldData<T extends AccessibleObject & Member> implements TypeInfo {
    /**
     * A Field or Method that returns an Object for the FieldData.
     */
    public final T getterElement;
    /**
     * The required {@link Save} Field annotation instance.
     */
    @NotNull
    public final Save saveData;
    /**
     * The optional {@link Range} Field annotation instance.
     */
    @Nullable
    public final Range range;

    @Nullable
    public final CleanString cleanString;

    /**
     * The optional {@link Typing} Field annotation instance.
     */
    @Nullable
    public final Typing typing;
    /**
     * The optional setter method for the Field. Marked with {@link Save.Setter}
     */
    @Nullable
    public final Method setter;

    public FieldData(T element, @Nullable Method setter) {
        if(element instanceof Method && setter == null) {
            throw new InvalidSavableElementException("Method " + element.getName() + " in " + element.getDeclaringClass() + " annotated with @Save is missing a setter method annotated with @Save.Setter(" + element.getName() + ")");
        }
        this.getterElement = element;
        this.setter = setter;
        if(setter != null) {
            setter.setAccessible(true);
        }
        element.setAccessible(true);
        this.saveData = element.getAnnotation(Save.class);
        this.range = element.getAnnotation(Range.class);
        this.cleanString = element.getAnnotation(CleanString.class);
        Typing tempTyping = element.getAnnotation(Typing.class);
        if(tempTyping == null){
            Class<?> type = element instanceof Field ? ((Field) element).getType() : (element instanceof Method ? ((Method) element).getReturnType() : null);
            if(type == null){
                throw new IllegalStateException("Getter element is somehow not a field or method, this is usually impossible.");
            }
            if(type.isArray()){
                List<Class<?>> components = new LinkedList<>();
                while(type.getComponentType() != null){
                    components.add(type.getComponentType());
                    type = type.getComponentType();
                }
                Class<?>[] typingArray = components.toArray(new Class<?>[0]);
                tempTyping = new Typing(){
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Typing.class;
                    }

                    @Override
                    public Class<?>[] value() {
                        return typingArray;
                    }
                };
            }
        }
        this.typing = tempTyping;
    }

    public boolean matchesStaticness(boolean isStatic) {
        return (isStatic && Modifier.isStatic(getterElement.getModifiers())) || (!isStatic && !Modifier.isStatic(getterElement.getModifiers()));
    }

    /**
     * Returns the field's current value.
     * @param context the context to execute in.
     * @return the current value.
     */
    public @Nullable Object get(Context context) {
        try {
            if(getterElement instanceof Field) {
                return ((Field) getterElement).get(context.source);
            }
            else if(getterElement instanceof Method){
                return ((Method) getterElement).invoke(context.source);
            }
            throw new IllegalStateException("Impossible state.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access field ",e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Could not invoke field getter method",e);
        }
    }

    /**
     * Set's the field's value
     * @param context the execution context
     * @param value the new value
     */
    public void set(Context context, @Nullable Object value) {
        try {
            if(range != null && value instanceof Number){
                double v = ((Number)value).doubleValue();
                if(v < range.minimum()){
                    v = Math.max(v, range.minimum());
                }
                else if (v > range.maximum()){
                    v = Math.min(v, range.maximum());
                }
                if(value instanceof Double) {
                    value = v;
                }
                else if(value instanceof Float){
                    value = (float)v;
                }
                else if(value instanceof Byte){
                    value = (byte)v;
                }
                else if(value instanceof Short){
                    value = (short)v;
                }
                else if(value instanceof Integer){
                    value = (int)v;
                }
                else if(value instanceof Long){
                    value = (long)v;
                }
            }
            if(cleanString != null && value instanceof String){
                for (CleanString.Replacement replacement : cleanString.replacements()) {
                    value = ((String)value).replaceAll(replacement.target(),replacement.replacement());
                }
                if(((String) value).length() > cleanString.maxLength()){
                    value = ((String) value).substring(0, cleanString.maxLength());
                }
            }
            if (setter != null) {
                setter.invoke(context.source, value);
            } else if (getterElement instanceof Field) {
                ((Field) getterElement).set(context.source, value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access field ",e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Could not invoke field setter method", e);
        }
    }

    public String getName(){
        return saveData.value().isEmpty() ? getterElement.getName() : saveData.value();
    }

    public String getFieldName() {
        return getterElement.getName();
    }

    public boolean isRaw() {
        return saveData.raw();
    }

    @Override
    public Class<?> getType() {
        if(getterElement instanceof Field) {
            return ((Field) getterElement).getType();
        }
        if(getterElement instanceof Method) {
            return ((Method) getterElement).getReturnType();
        }
        throw new IllegalStateException("Impossible state.");
    }

    public Class<?>[] getTyping(){
        if(typing == null){
            throw new InvalidSavableElementException("missing @Typing annotation!");
        }
        return typing.value();
    }

    @Override
    public String toString() {
        return "FieldData{" +
                ", saveData=" + saveData +
                ", rangeData=" + range +
                ", typing=" + typing +
                '}';
    }
}

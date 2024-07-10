package com.vicious.persist.mappify;

import com.vicious.persist.except.InvalidSavableElementException;
import com.vicious.persist.io.writer.wrapped.WrappedObjectList;
import com.vicious.persist.io.writer.wrapped.WrappedObjectMap;
import com.vicious.persist.io.writer.wrapped.WrappedObject;

import java.util.Collection;
import java.util.Map;

public class Mappifier {
    public static final Mappifier DEFAULT = new Mappifier();

    public static Mappifier create(){
        return new Mappifier();
    }

    private boolean applyCommentsOnReservedFields = false;
    private Mappifier(){}

    public Mappifier applyCommentsOnReservedFields(boolean setting){
        this.applyCommentsOnReservedFields = setting;
        return this;
    }

    public WrappedObjectMap mappify(Object object){
        return mappify(Context.of(object));
    }

    private WrappedObjectMap mappify(Context context) {
        WrappedObjectMap output = new WrappedObjectMap();
        context.forEach(fieldData -> {
            output.put(fieldData.getName(),mappify(fieldData, context, fieldData.isRaw()));
        });
        return output;
    }

    private WrappedObject mappify(FieldData<?> data, Context context, boolean raw) {
        try {
            return mappifyValue(data, data.get(context), raw,0, data.saveData.description());
        } catch (Throwable t){
            throw new InvalidSavableElementException("Could not mappify field " + data.getFieldName() + " in " + context.getType(),t);
        }
    }

    private WrappedObject mappifyValue(TypeInfo info, Object value, boolean raw, int typingIndex, String comment){
        if(value == null || raw && !info.isDataStructure()){
            return WrappedObject.of(rawObject(value),comment);
        }
        if(info.isClass()){
            return WrappedObject.of(mappifyClass(info,value,raw),comment);
        }
        else if(info.isCollection()){
            return WrappedObject.of(mappifyCollection(info, (Collection<?>) value,raw,typingIndex),comment);
        }
        else if(info.isMap()){
            return WrappedObject.of(mappifyMap(info, (Map<?,?>) value,raw,typingIndex),comment);
        }

        if(Context.of(value).hasMappifiableTraits()){
            WrappedObjectMap map = mappify(value);
            if(value instanceof Enum){
                map.put(Reserved.E_NAME,WrappedObject.of(((Enum<?>)value).name()));
            }
            return WrappedObject.of(map,comment);
        }
        else{
            return WrappedObject.of(rawObject(value),comment);
        }
    }

    private WrappedObjectMap mappifyMap(TypeInfo info, Map<?,?> map, boolean raw, int typingIndex){
        Class<?> valueType = info.getTyping(typingIndex+1);
        WrappedObjectMap out = new WrappedObjectMap();
        for (Object key : map.keySet()) {
            Object val = map.get(key);
            if(val == null){
                out.put(key,WrappedObject.nullified());
            }
            else {
                if(!valueType.isAssignableFrom(val.getClass())){
                    throw new InvalidSavableElementException("Typing does not match Map generics.");
                }
                WrappedObject wrappedObject = mappifyValue(TypeInfo.cast(info,valueType), val, raw, typingIndex + 2, null);
                if (val.getClass() != valueType && wrappedObject.object instanceof WrappedObjectMap) {
                    WrappedObjectMap wrappedMap = (WrappedObjectMap) wrappedObject.object;
                    wrappedMap.put(Reserved.C_NAME, WrappedObject.of(Stringify.stringify(val.getClass())));
                }
                out.put(key, wrappedObject);
            }
        }
        return out;
    }

    private WrappedObjectList mappifyCollection(TypeInfo info, Collection<?> collection, boolean raw, int typingIndex) {
        Class<?> valueType = info.getTyping(typingIndex);
        WrappedObjectList out = new WrappedObjectList();
        for (Object obj : collection) {
            if(obj == null){
                out.add(WrappedObject.nullified());
            }
            else{
                if(!valueType.isAssignableFrom(obj.getClass())){
                    throw new InvalidSavableElementException("Typing does not match Collection generics.");
                }
                WrappedObject wrappedObject = mappifyValue(TypeInfo.cast(info,valueType), obj, raw, typingIndex+1, null);
                if(obj.getClass() != valueType && wrappedObject.object instanceof WrappedObjectMap){
                    WrappedObjectMap wrappedMap = (WrappedObjectMap) wrappedObject.object;
                    wrappedMap.put(Reserved.C_NAME,WrappedObject.of(Stringify.stringify(obj.getClass()),reservedComment()));
                }
                out.add(wrappedObject);
            }
        }
        return out;
    }

    private String reservedComment() {
        return applyCommentsOnReservedFields ? "DO NOT EDIT" : "";
    }

    private Object mappifyClass(TypeInfo info, Object classObject, boolean raw){
        Context internalContext = Context.of(classObject);
        if(!raw && internalContext.hasMappifiableTraits()){
            WrappedObjectMap obj = mappify(internalContext);
            if(info.getType() != classObject){
                obj.put(Reserved.C_NAME,WrappedObject.of(Stringify.stringify(classObject),reservedComment()));
            }
            return obj;
        }
        return classObject;
    }

    private Object rawObject(Object obj) {
        if(obj != null && obj.getClass().isEnum()){
            return ((Enum)obj).name();
        }
        else{
            return obj;
        }
    }
}

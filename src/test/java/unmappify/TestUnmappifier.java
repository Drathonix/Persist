package unmappify;

import com.vicious.persist.Persist;
import com.vicious.persist.io.writer.wrapped.WrappedObject;
import com.vicious.persist.io.writer.wrapped.WrappedObjectMap;
import com.vicious.persist.mappify.Mappifier;
import mappify.TestObject1;
import mappify.array.ArrayTestObject;
import mappify.collection.TestObject3;
import mappify.collection.except.BadTestObject3;
import mappify.collection.except.BadTestObject3a;
import mappify.collection.except.BadTestObject3b;
import mappify.enums.TestEnumsAsKeysObject;
import mappify.enums.TestMappableEnum;
import mappify.enums.TestObject6;
import mappify.enums.TestUnmappableEnum;
import mappify.extension.TestChildObject;
import mappify.extension.TestParentObject;
import mappify.internal.TestObject4;
import mappify.map.TestObject2;
import mappify.map.except.BadTestObject2;
import mappify.map.except.BadTestObject2a;
import mappify.map.except.BadTestObject2b;
import mappify.setter.TestObject5;
import mappify.setter.except.BadTestObject5;
import mappify.special.TestObjectWeirdKeys;
import mappify.special.TestSpecialObject;
import mappify.special.WeirdKey;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@Order(999)
public class TestUnmappifier {
    Mappifier mappifier = Mappifier.DEFAULT;
    {
        Persist.doC_NAMEScan();
    }
    @Test
    public void testUnmappifyPrimitivesAndStaticDifferentiationAndNaming(){
        genEditsAndTest(new TestObject1(),(out,wom)->{
            out.add(genEdit(wom,(byte)119,"byte0"));
            out.add(genEdit(wom,(byte)-118,"thisbyteisspecial"));
            out.add(genEdit(wom,(short)-18687,"short0"));
            out.add(genEdit(wom,(short)18787,"short1"));
            out.add(genEdit(wom,217683217,"int0"));
            out.add(genEdit(wom,-217683217,"int1"));
            out.add(genEdit(wom,-321782213231123L,"long0"));
            out.add(genEdit(wom,3217822131123L,"long1"));
            out.add(genEdit(wom,23768678.382F,"float0"));
            out.add(genEdit(wom,-237678.382F,"float1"));
            out.add(genEdit(wom,2332329802308923.33,"double0"));
            out.add(genEdit(wom,-933244329802308923.33,"double1"));
            out.add(genEdit(wom,'d',"char0"));
            out.add(genEdit(wom,'6',"char1"));
            out.add(genEdit(wom,false,"boolean0"));
            out.add(genEdit(wom,true,"boolean1"));
        });

        genEditsAndTest(TestObject1.class,(out,wom)->{
            out.add(genEdit(wom,(byte)119,"sbyte0"));
            out.add(genEdit(wom,(byte)-118,"sbyte1"));
            out.add(genEdit(wom,(short)-18687,"thisshortisspecial"));
            out.add(genEdit(wom,(short)18787,"sshort1"));
            out.add(genEdit(wom,217683217,"sint0"));
            out.add(genEdit(wom,-217683217,"sint1"));
            out.add(genEdit(wom,-321782213231123L,"slong0"));
            out.add(genEdit(wom,3217822131123L,"slong1"));
            out.add(genEdit(wom,23768678.382F,"sfloat0"));
            out.add(genEdit(wom,-237678.382F,"sfloat1"));
            out.add(genEdit(wom,2332329802308923.33,"sdouble0"));
            out.add(genEdit(wom,-933244329802308923.33,"sdouble1"));
            out.add(genEdit(wom,'d',"schar0"));
            out.add(genEdit(wom,'6',"schar1"));
            out.add(genEdit(wom,false,"sboolean0"));
            out.add(genEdit(wom,true,"sboolean1"));
        });
    }


    @Test
    public void testUnmappifyMaps(){
        genEditsAndTest(new TestObject2(), (out, wom) -> {
            out.add(genEdit(wom,"2","map",1));
            out.add(genEdit(wom,"3","map",2));
            out.add(genEdit(wom,"4","map",3));
            out.add(genEdit(wom,"5","map",4));
        });
        genEditsAndTest(TestObject2.class, (out, wom) -> {
            out.add(genEdit(wom,'7',"nestedMap",0,"hi"));
            out.add(genEdit(wom,'8',"nestedMap",0,"bye"));
            out.add(genEdit(wom,'0',"nestedMap",0,"not_present"));
            out.add(genEdit(wom,'u',"nestedMap",2,"not_present"));
            out.add(genEdit(wom,'k',"nestedMap",2,"cake"));
        });
    }

    @Test
    public void testMappifyCollections(){
        String s = "abcdefghijklmnopqrstuvwxy&z";
        StringBuilder r = new StringBuilder();
        for (int i = s.length()-1; i >= 0; i--) {
            r.append(s.charAt(i));
        }
        s = r.toString();
        String finalS = s;
        genEditsAndTest(new TestObject3(), (out, wom) -> {
            for (int i = 0; i < finalS.length(); i++) {
                out.add(genEdit(wom,String.valueOf(finalS.charAt(i)),"list",i));
            }
        });
        genEditsAndTest(TestObject3.class, (out, wom) -> {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < i; j++) {
                    out.add(genEdit(wom,j + "a","nestedList",i,j));
                }
            }
        });
    }

    @Test
    public void testMappifyMappifiableObjects(){
        genEditsAndTest(new TestObject4(), (out, wom) -> {
            out.add(genEdit(wom,(byte)16,"internal","byte0"));
            out.add(genEdit(wom,"4","internalMap",1,"map",2));
            out.add(genEdit(wom,"Do","internalMap2",'a',"list",3));
            out.add(genEdit(wom,"not edit","internalMap2",'b',"list",5));
        });
    }

    @Test
    public void testMappifyWithSetterAndGetterMethods(){
        genEditsAndTest(new TestObject5(), (out, wom) -> {
            out.add(genEdit(wom,200,"int0"));
            out.add(genEdit(wom,-439821,"int1"));
        });
    }

    @Test
    public void testUnmappifyEnums(){
        genEditsAndTest(new TestObject6(), (out, wom) -> {
            out.add(genEdit(wom,TestUnmappableEnum.A,"enum1"));
            out.add(genEdit(wom,TestMappableEnum.E,"enum2"));
            for (TestMappableEnum value : TestMappableEnum.values()) {
                out.add(genEdit(wom,value.ordinal()*99,"mappableEnumList",value.ordinal(),"value"));
            }
            out.add(genEdit(wom,"F","untypedMappable","E_N_"));
            out.add(genEdit(wom,"testmappableenum","untyped","C_N_"));
            out.add(genEdit(wom,"H","untyped","E_N_"));
        });
    }

    @Test
    public void testUnmappifyExtends(){
        genEditsAndTest(new TestParentObject(), (out, wom) -> {
            out.add(genEdit(wom,4,"i"));
            out.add(genEdit(wom,5,"j"));
            out.add(genEdit(wom,6,"h"));
            out.add(genEdit(wom,7,"k"));
        });

        genEditsAndTest(new TestChildObject(), (out, wom) -> {
            out.add(genEdit(wom,4,"i"));
            out.add(genEdit(wom,5,"j"));
            out.add(genEdit(wom,60.0,"h"));
            out.add(genEdit(wom,71.0,"k"));
        });
    }

    @Test
    public void testUnmappifyStoreSpecial(){
        genEditsAndTest(new TestSpecialObject(), (out, wom) -> {
            modifyValue(wom,"testobjecta","a","C_N_");
            out.add(genEdit(wom,"TestObjectAInjected","a","name"));
            out.add(genEdit(wom,'9',"b","char"));
        });
    }

    @Test
    public void testUnmappifyEnumsAsKeys(){
        genEditsAndTest(new TestEnumsAsKeysObject(), (out, wom) -> {
            out.add(genEdit(wom,TestMappableEnum.G,"map",TestMappableEnum.G));
            out.add(genEdit(wom,TestMappableEnum.E,"map",TestMappableEnum.E));
        });
    }

    @Test
    public void testUnmappifyWeirdKeys(){
        genEditsAndTest(new TestObjectWeirdKeys(),(out,wom)->{
            out.add(genEdit(wom,new WeirdKey("apple"),"map",new WeirdKey("weird")));
            out.add(genEdit(wom,new WeirdKey("banana"),"map",new WeirdKey("beard")));
            out.add(genEdit(wom,new WeirdKey("orange"),"map",new WeirdKey("shear")));
        });
    }

    @Test
    public void testMappifyArrays(){
        genEditsAndTest(ArrayTestObject.class,(out,wom)->{
            out.add(genEdit(wom,ArrayTestObject.ints[3],"ints",3));
            out.add(genEdit(wom,ArrayTestObject.nestedBool[1][1],"nestedBool",1,1));
            out.add(genEdit(wom,ArrayTestObject.doubleNestedDoubles[1][0][1],"doubleNestedDoubles",1,0,1));
            out.add(genEdit(wom,ArrayTestObject.nonPrim[3],"nonPrim",3));
        });
    }

    private void genEditsAndTest(Object target, BiConsumer<List<SearchObj>,WrappedObjectMap> consumer) {
        WrappedObjectMap wom = mappifier.mappify(target);
        List<SearchObj> checks = new ArrayList<>();
        consumer.accept(checks,wom);
        mappifier.unmappify(target,wom);
        test(target,checks);
    }

    private void test(Object target, List<SearchObj> checks) {
        WrappedObjectMap wom = mappifier.mappify(target);
        for (SearchObj check : checks) {
            testValue(wom,check.newValue,check.route);
        }
    }

    private <T extends Throwable> void assertThrowsInternal(Executable executable, String... messages){
        try{
            executable.execute();
            assertEquals(messages[0], "DID NOT THROW");
        } catch (Throwable throwable){
            if(throwable instanceof AssertionFailedError){

                throw (AssertionFailedError)throwable;
            }
            int i = 0;
            while(throwable != null) {
                System.out.println("Checking if threw: " + messages[i]);
                assertEquals(messages[i], throwable.getMessage());
                i++;
                throwable = throwable.getCause();
            }
        }
    }

    private SearchObj genEdit(WrappedObjectMap source, Object modified, Object... route){
        SearchObj out = new SearchObj(modified,route);
        modifyValue(source,modified,route);
        return out;
    }

    private void modifyValue(Object source, Object modified, Object... route){
        System.out.println("Modifiying as " + modified + " in route " + Arrays.toString(route));
        assertTrue(route.length > 0);
        for (int i = 0; i < route.length-1; i++) {
            Object o = route[i];
            System.out.println("Descending to " + o);
            if(source instanceof WrappedObject) {
                source = ((WrappedObject) source).object;
            }
            if(source instanceof Map){
                source = ((Map<?, ?>) source).get(o);
            }
            if(source instanceof List){
                assertInstanceOf(Integer.class,o);
                source = ((List<?>) source).get((Integer) o);
            }
        }
        if(source instanceof WrappedObject){
            source = ((WrappedObject) source).object;
        }
        Object o = route[route.length-1];
        if(source instanceof Map){
            Map m = (Map) source;
            if(m.containsKey(o)){
                m.replace(o,WrappedObject.of(modified));
            }
            else{
                m.put(o,WrappedObject.of(modified));
            }
        }
        if(source instanceof List){
            assertInstanceOf(Integer.class,o);
            source = ((List) source).set((Integer) o,WrappedObject.of(modified));
        }
    }

    private void testValue(Object source, Object expected, Object... route){
        System.out.println("Checking for " + expected + " in route " + Arrays.toString(route));
        assertTrue(route.length > 0);
        for (Object o : route) {
            System.out.println("Descending to " + o);
            if(source instanceof WrappedObject) {
                source = ((WrappedObject) source).object;
            }
            if(source instanceof Map){
                source = ((Map<?, ?>) source).get(o);
            }
            if(source instanceof List){
                assertInstanceOf(Integer.class,o);
                source = ((List<?>) source).get((Integer) o);
            }
        }
        assertInstanceOf(WrappedObject.class,source);
        System.out.println("Checking " + ((WrappedObject) source).object + " against " + expected);
        assertEquals(expected, ((WrappedObject) source).object);
    }
}

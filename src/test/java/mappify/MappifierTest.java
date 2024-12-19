package mappify;

import com.vicious.persist.Persist;
import com.vicious.persist.io.writer.wrapped.WrappedObject;
import com.vicious.persist.io.writer.wrapped.WrappedObjectMap;
import com.vicious.persist.mappify.Mappifier;
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
import mappify.map.except.BadTestObject2;
import mappify.map.except.BadTestObject2a;
import mappify.map.except.BadTestObject2b;
import mappify.map.TestObject2;
import mappify.setter.TestObject5;
import mappify.setter.except.BadTestObject5;
import mappify.special.TestObjectWeirdKeys;
import mappify.special.TestSpecialObject;
import mappify.special.WeirdKey;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

//Unmappifier test modifies static attributes so this must go last (otherwise I have to reset the fields)
@Order(-298231098)
public class MappifierTest {
    Mappifier mappifier = Mappifier.DEFAULT;
    {
        Persist.doC_NAMEScan();
    }
    @Test
    public void testMappifyPrimitivesAndStaticDifferentiationAndNaming(){
        WrappedObjectMap wom = mappifier.mappify(new TestObject1());
        testValue(wom,(byte)120,"byte0");
        testValue(wom,(byte)120,"thisbyteisspecial");
        testValue(wom,(short)18787,"short0");
        testValue(wom,(short)18787,"short1");
        testValue(wom,Integer.MAX_VALUE,"int0");
        testValue(wom,Integer.MAX_VALUE,"int1");
        testValue(wom,Long.MAX_VALUE,"long0");
        testValue(wom,Long.MAX_VALUE,"long1");
        testValue(wom,23.382F,"float0");
        testValue(wom,23.382F,"float1");
        testValue(wom,233244329802308923.33,"double0");
        testValue(wom,233244329802308923.33,"double1");
        testValue(wom,'a',"char0");
        testValue(wom,'b',"char1");
        testValue(wom,true,"boolean0");
        testValue(wom,false,"boolean1");

        wom = mappifier.mappify(TestObject1.class);
        testValue(wom,(byte)120,"sbyte0");
        testValue(wom,(byte)120,"sbyte1");
        testValue(wom,(short)18787,"thisshortisspecial");
        testValue(wom,(short)18787,"sshort1");
        testValue(wom,Integer.MAX_VALUE,"sint0");
        testValue(wom,Integer.MAX_VALUE,"sint1");
        testValue(wom,Long.MAX_VALUE,"slong0");
        testValue(wom,Long.MAX_VALUE,"slong1");
        testValue(wom,23.382F,"sfloat0");
        testValue(wom,23.382F,"sfloat1");
        testValue(wom,233244329802308923.33,"sdouble0");
        testValue(wom,233244329802308923.33,"sdouble1");
        testValue(wom,'a',"schar0");
        testValue(wom,'b',"schar1");
        testValue(wom,true,"sboolean0");
        testValue(wom,false,"sboolean1");
    }

    @Test
    public void testMappifyMaps(){
        //Check that a missing Typing annotation throws an error.
        assertThrowsInternal(()->{
            mappifier.mappify(new BadTestObject2());
        },"Could not mappify field map in class mappify.map.except.BadTestObject2","missing @Typing annotation!");
        //Check that invalid typing annotations throw errors
        assertThrowsInternal(()->{
            mappifier.mappify(new BadTestObject2a());
        },"Could not mappify field map in class mappify.map.except.BadTestObject2a","Typing is of length 1 needs to be at least 2");
        assertThrowsInternal(()->{
            mappifier.mappify(new BadTestObject2b());
        },"Could not mappify field map in class mappify.map.except.BadTestObject2b","Typing does not match Map generics.");

        WrappedObjectMap wom = mappifier.mappify(new TestObject2());
        testValue(wom,"1","map",1);
        testValue(wom,"2","map",2);
        testValue(wom,"3","map",3);
        testValue(wom,"4","map",4);
        assertNull(wom.get("nestedMap"));

        wom = mappifier.mappify(TestObject2.class);
        testValue(wom,'h',"nestedMap",0,"hi");
        testValue(wom,'b',"nestedMap",0,"bye");
        testValue(wom,'y',"nestedMap",0,"yo");
        testValue(wom,'j',"nestedMap",1,"jake");
        testValue(wom,'c',"nestedMap",1,"cake");
        testValue(wom,'m',"nestedMap",1,"mike");
        testValue(wom,'p',"nestedMap",2,"pile");
        testValue(wom,'i',"nestedMap",2,"isle");
        testValue(wom,'r',"nestedMap",2,"rifle");
        assertNull(wom.get("map"));
    }

    @Test
    public void testMappifyCollections(){
        //Check that a missing Typing annotation throws an error.
        assertThrowsInternal(()->{
            mappifier.mappify(new BadTestObject3());
        },"Could not mappify field list in class mappify.collection.except.BadTestObject3","missing @Typing annotation!");
        //Check that invalid typing annotations throw errors
        assertThrowsInternal(()->{
            mappifier.mappify(new BadTestObject3a());
        },"Could not mappify field list in class mappify.collection.except.BadTestObject3a","Typing is of length 1 needs to be at least 2");
        assertThrowsInternal(()->{
            mappifier.mappify(new BadTestObject3b());
        },"Could not mappify field list in class mappify.collection.except.BadTestObject3b","Typing does not match Collection generics. Received object of type class java.util.ArrayList but expected class java.lang.String");
        WrappedObjectMap wom = mappifier.mappify(new TestObject3());
        String s ="abcdefghijklmnopqrstuvwxy&z";
        for (int i = 0; i < s.length(); i++) {
            testValue(wom,s.charAt(i) + "","list",i);
        }

        wom = mappifier.mappify(TestObject3.class);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < i; j++) {
                testValue(wom,j + "","nestedList",i,j);
            }
        }
    }

    @Test
    public void testMappifyMappifiableObjects(){
        WrappedObjectMap wom = mappifier.mappify(new TestObject4());
        testValue(wom,(byte)8,"internal","byte0");
        testValue(wom,"2","internalMap",1,"map",2);
        testValue(wom,"d","internalMap2",'a',"list",3);
        testValue(wom,"f","internalMap2",'b',"list",5);
    }

    @Test
    public void testMappifyWithSetterAndGetterMethods(){
        assertThrowsInternal(()->{
            mappifier.mappify(new BadTestObject5());
        },"Method int1 in class mappify.setter.except.BadTestObject5 annotated with @Save is missing a setter method annotated with @Save.Setter(int1)");
        WrappedObjectMap wom = mappifier.mappify(new TestObject5());
        testValue(wom,0,"int0");
        testValue(wom,1,"int1");
    }

    @Test
    public void testMappifyEnums(){
        WrappedObjectMap wom = mappifier.mappify(new TestObject6());
        testValue(wom, TestUnmappableEnum.A,"enum1");
        testValue(wom, "E","enum2","E_N_");
        testValue(wom, 0,"enum2","value");
        int i = 0;
        for (TestMappableEnum value : TestMappableEnum.values()) {
            testValue(wom, value.value,"mappableEnumList",i,"value");
            testValue(wom, value.name(),"mappableEnumList",i,"E_N_");
            i++;
        }
        testValue(wom,"testmappableenum","untypedMappable","C_N_");
        testValue(wom,"E","untypedMappable","E_N_");
        testValue(wom,"testunmappableenum","untyped","C_N_");
        testValue(wom,"A","untyped","E_N_");
    }

    @Test
    public void testMappifyExtends(){
        WrappedObjectMap wom = mappifier.mappify(new TestParentObject());
        testValue(wom,1,"i");
        testValue(wom,2,"j");
        testValue(wom,3,"h");
        testValue(wom,4,"k");

        wom = mappifier.mappify(new TestChildObject());
        testValue(wom,1,"i");
        testValue(wom,2,"j");
        testValue(wom,10.0,"h");
        testValue(wom,11.0,"k");
    }

    @Test
    public void testMappifyStoreSpecial(){
        WrappedObjectMap wom = mappifier.mappify(new TestSpecialObject());
        testValue(wom,"testobjectb","a","C_N_");
        //testValue(wom,"TestObjectA","a",);
        testValue(wom,"testobjectc","b","C_N_");
    }

    @Test
    public void testMappifyEnumsAsKeys(){
        WrappedObjectMap wom = mappifier.mappify(new TestEnumsAsKeysObject());
        testValue(wom,TestMappableEnum.H,"map",TestMappableEnum.G);
        testValue(wom,TestMappableEnum.F,"map",TestMappableEnum.E);
    }

    @Test
    public void testMappifyWeirdKeys(){
        WrappedObjectMap wom = mappifier.mappify(new TestObjectWeirdKeys());
        System.out.println(wom);
        testValue(wom, new WeirdKey("sweared"),"map",new WeirdKey("weird"));
        testValue(wom, new WeirdKey("smeared"),"map",new WeirdKey("beard"));
        testValue(wom, new WeirdKey("feared"),"map",new WeirdKey("shear"));
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

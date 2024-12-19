package io;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JSONTesterCore {
    public static void parser_read_test_file(Map<String,Object> map) {
        /*System.out.println("Keys: ");
        for (String s : map.keySet()) {
            System.out.println(s);
            System.out.println("----");
        }
        System.out.println("Values: ");
        for (Object s : map.values()) {
            System.out.println(s);
        }
        System.out.println("Mapped:");*/
        System.out.println(map);
        checkElement("value",map,"string_key");
        checkElement("c",map,"char_key");
        checkElement("true",map,"boolean_key1");
        checkElement("True",map,"boolean_key2");
        checkElement("False",map,"boolean_key3");
        checkElement("faLsE",map,"boolean_key4");
        checkElement("127",map,"byte_key1");
        checkElement("-128",map,"byte_key2");
        checkElement("25346",map,"short_key1");
        checkElement("-2346",map,"short_key2");
        checkElement("2165767",map,"int_key1");
        checkElement("-2165767",map,"int_key2");
        checkElement("2319782317897",map,"long_key1");
        checkElement("-2319782317897",map,"long_key2");
        checkElement("26423.5",map,"float_key1");
        checkElement("-26423.5",map,"float_key2");
        checkElement("26423879798.5908",map,"double_key1");
        checkElement("-26423879798.5908",map,"double_key2");
        checkElement("hi",map,"map_key","key1");
        checkElement("true",map,"map_key","key2");
        checkElement("value1",map,"list_key",0);
        checkElement("1",map,"list_key",1);
        checkElement("5",map,"list_key",2);
        checkElement("true",map,"list_key",3);
        checkElement("1",map,"nested_map_key","nested_map_key","nested_map_key","value");
        checkElement("2",map,"nested_list_key",0,0,0,0,1);
        checkElement("value",map,"chaos_key",0,"internal_map","list",0,0,"key");
        checkElement("1",map,"commented_list",0);
        checkElement("2",map,"commented_list",1);
        checkElement("3",map,"commented_list",2);
        checkElement("4",map,"commented_list",3);
        checkNull(map,"commented_list",4);
    }

    private static void checkElement(Object expected, Object source, Object... route){
        System.out.println("Checking for " + expected + " in route " + Arrays.toString(route));
        assertTrue(route.length > 0);
        for (Object o : route) {
            System.out.println("Descending to " + o);
            if(o instanceof String){
                assertInstanceOf(Map.class, source);
                source = ((Map<?, ?>) source).get(o);
            }
            if(o instanceof Integer){
                assertInstanceOf(List.class, source);
                source = ((List<?>) source).get((Integer) o);
            }
        }
        System.out.println("Checking " + source + " against " + expected);
        assertEquals(expected, source);
    }

    private static void checkNull(Object source, Object... route){
        System.out.println("Checking for null in route " + Arrays.toString(route));
        assertTrue(route.length > 0);
        for (Object o : route) {
            System.out.println("Descending to " + o);
            if(o instanceof String){
                assertInstanceOf(Map.class, source);
                if(o == route[route.length-1]) {
                    assertFalse(((Map<?, ?>) source).containsKey(o));
                }
                else{
                    assertTrue(((Map<?, ?>) source).containsKey(o));
                    source = ((Map<?, ?>) source).get(o);
                }
            }
            if(o instanceof Integer){
                assertInstanceOf(List.class, source);
                List<?> l = (List<?>) source;
                if(o == route[route.length-1]) {
                    assertFalse(l.size() > (int) o);
                }
                else{
                    assertTrue(l.size() > (int) o);
                    source = l.get((int) o);
                }
            }
        }
    }

    public static void parser_write_test_file(Map<String, Object> map) {
        checkElement("value",map,"string_key");
        checkElement("c",map,"char_key");
        checkElement(true,map,"boolean_key1");
        checkElement(true,map,"boolean_key2");
        checkElement(false,map,"boolean_key3");
        checkElement(false,map,"boolean_key4");
        checkElement(127L,map,"byte_key1");
        checkElement(-128L,map,"byte_key2");
        checkElement(25346L,map,"short_key1");
        checkElement(-2346L,map,"short_key2");
        checkElement(2165767L,map,"int_key1");
        checkElement(-2165767L,map,"int_key2");
        checkElement(2319782317897L,map,"long_key1");
        checkElement(-2319782317897L,map,"long_key2");
        checkElement(26423.5D,map,"float_key1");
        checkElement(-26423.5D,map,"float_key2");
        checkElement(26423879798.5908D,map,"double_key1");
        checkElement(-26423879798.5908D,map,"double_key2");
        checkElement("hi",map,"map_key","key1");
        checkElement(true,map,"map_key","key2");
        checkElement("value1",map,"list_key",0);
        checkElement(1L,map,"list_key",1);
        checkElement(5L,map,"list_key",2);
        checkElement(true,map,"list_key",3);
        checkElement(1L,map,"nested_map_key","nested_map_key","nested_map_key","value");
        checkElement(2L,map,"nested_list_key",0,0,0,0,1);
        checkElement("value",map,"chaos_key",0,"internal_map","list",0,0,"key");
        checkElement(1L,map,"commented_list",0);
        checkElement(2L,map,"commented_list",1);
        checkElement(3L,map,"commented_list",2);
        checkElement(4L,map,"commented_list",3);
        checkNull(map,"commented_list",4);
    }
}

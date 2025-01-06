package util;

import com.vicious.persist.util.StringTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestStringTree {
    @Test
    public void test() {
        StringTree<Integer> tree = new StringTree<>();
        tree.put("v1",1);
        assertEquals(1,tree.get("v1"));
        assertEquals(1,tree.size());
        assertEquals(1,tree.depth());
        tree.put("v5",5);
        assertEquals(5,tree.get("v5"));
        assertEquals(2,tree.size());
        assertEquals(1,tree.depth());
        tree.put("v2/v3",2);
        assertNull(tree.get("v2"));
        assertEquals(2,tree.get("v2/v3"));
        assertEquals(3,tree.size());
        assertEquals(2,tree.depth());
        tree.put("v2/v3/v4/v5/v6/v7",7);
        assertEquals(7,tree.get("v2/v3/v4/v5/v6/v7"));
        assertEquals(4,tree.size());
        assertEquals(6,tree.depth());
        tree.put("v2/v3/v4/v5/v6/v8",8);
        tree.put("v2/v3/v4/v5/v6/v9",9);
        assertEquals(8,tree.get("v2/v3/v4/v5/v6/v8"));
        assertEquals(9,tree.get("v2/v3/v4/v5/v6/v9"));
        assertEquals(6,tree.size());
        assertEquals(6,tree.depth());
        tree.remove("v2/v3/v4/v5/v6/v9");
        assertNull(tree.get("v2/v3/v4/v5/v6/v9"));
        assertEquals(5,tree.size());
        assertEquals(6,tree.depth());
        List<String> check = new ArrayList<>();
        check.add("v1");
        check.add("v5");
        check.add("v2/v3");
        check.add("v2/v3/v4/v5/v6/v7");
        check.add("v2/v3/v4/v5/v6/v8");
        assertTrue(tree.keySet().containsAll(check));
        List<Integer> check2 = new ArrayList<>();
        check2.add(1);
        check2.add(2);
        check2.add(5);
        check2.add(7);
        check2.add(8);
        assertTrue(tree.values().containsAll(check2));
        System.out.println(tree.entrySet());
        tree.clear();
        assertEquals(0,tree.size());
        assertEquals(0,tree.depth());
        System.out.println(tree.entrySet());
    }
}

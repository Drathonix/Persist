package mappify;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;
import mappify.collection.TestObject3;
import mappify.map.TestObject2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestObject4 {
    @Save
    public TestObject1 internal = new TestObject1();

    {
        internal.byte0=8;
    }

    @Save
    @Typing({Integer.class,TestObject2.class})
    public Map<Integer, TestObject2> internalMap = new HashMap<>();

    {
        internalMap.put(1, new TestObject2());
    }

    @Save
    @Typing({Character.class,TestObject3.class})
    public Map<Character, TestObject3> internalMap2 = new HashMap<>();
    {
        internalMap2.put('a', new TestObject3());
        internalMap2.put('b', new TestObject3());
    }
}

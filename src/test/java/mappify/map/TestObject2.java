package mappify.map;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.HashMap;
import java.util.Map;

public class TestObject2 {

    @Save
    @Typing({Integer.class,String.class})
    public Map<Integer,String> map = new HashMap<>();

    {
        map.put(1,"1");
        map.put(2,"2");
        map.put(3,"3");
        map.put(4,"4");
    }

    @Save
    @Typing({Integer.class,HashMap.class,String.class,Character.class})
    public static Map<Integer,HashMap<String,Character>> nestedMap = new HashMap<>();
    {
        HashMap<String,Character> nested1 = new HashMap<>();
        nested1.put("hi",'h');
        nested1.put("bye",'b');
        nested1.put("yo",'y');
        nestedMap.put(0,nested1);
        nested1 = new HashMap<>();
        nested1.put("jake",'j');
        nested1.put("cake",'c');
        nested1.put("mike",'m');
        nestedMap.put(1,nested1);
        nested1 = new HashMap<>();
        nested1.put("pile",'p');
        nested1.put("isle",'i');
        nested1.put("rifle",'r');
        nestedMap.put(2,nested1);
    }
}

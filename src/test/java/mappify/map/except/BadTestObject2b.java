package mappify.map.except;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.HashMap;
import java.util.Map;

public class BadTestObject2b {
    @Save
    @Typing({Integer.class,Integer.class})
    public Map<Integer,String> map = new HashMap<Integer,String>();

    {
        map.put(1,"1");
    }
}

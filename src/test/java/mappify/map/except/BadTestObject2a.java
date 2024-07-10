package mappify.map.except;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.HashMap;
import java.util.Map;

public class BadTestObject2a {
    @Save
    @Typing(Integer.class)
    public Map<Integer,String> map = new HashMap<Integer,String>();
}

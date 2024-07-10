package mappify.map.except;

import com.vicious.persist.annotations.Save;

import java.util.HashMap;
import java.util.Map;

public class BadTestObject2 {
    @Save
    public Map<Integer,String> map = new HashMap<Integer,String>();
}

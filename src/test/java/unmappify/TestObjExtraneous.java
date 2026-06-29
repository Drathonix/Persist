package unmappify;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.HashMap;
import java.util.Map;

public class TestObjExtraneous {
    @Save("in.k.l")
    public Inner1 in = new Inner1();


    public static class Inner2 {
        @Save
        public int x;
    }

    public static class Inner1 {
        @Save
        @Typing({String.class,Inner2.class})
        public Map<String,Inner2> inners = new HashMap<>();
    }
}

package mappify.collection;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.ArrayList;
import java.util.List;

public class TestObject3 {
    @Save
    @Typing(String.class)
    public List<String> list = new ArrayList<>();

    {
        String s ="abcdefghijklmnopqrstuvwxy&z";
        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i) + "");
        }
    }

    @Save
    @Typing({ArrayList.class,String.class})
    public static List<ArrayList<String>> nestedList = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            nestedList.add(new ArrayList<>());
            ArrayList<String> a = nestedList.get(i);
            for (int j = 0; j < i; j++) {
                a.add(j + "");
            }
        }
    }
}

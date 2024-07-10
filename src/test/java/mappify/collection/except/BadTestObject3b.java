package mappify.collection.except;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.ArrayList;
import java.util.List;

public class BadTestObject3b {
    @Save
    @Typing(String.class)
    public List<ArrayList<String>> list = new ArrayList<>();
    {
        list.add(new ArrayList<>());
    }
}

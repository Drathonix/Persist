package mappify.collection.except;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BadTestObject3a {
    @Save
    @Typing(ArrayList.class)
    public List<ArrayList<String>> list = new ArrayList<>();
    {
        list.add(new ArrayList<>());
    }
}

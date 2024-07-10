package mappify.collection.except;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.ArrayList;
import java.util.List;

public class BadTestObject3 {
    @Save
    public List<String> list = new ArrayList<>();
}

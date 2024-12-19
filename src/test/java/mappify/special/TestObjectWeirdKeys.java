package mappify.special;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.HashMap;
import java.util.Map;

public class TestObjectWeirdKeys {
    @Save
    @Typing({WeirdKey.class, WeirdKey.class})
    public Map<WeirdKey,WeirdKey> map = new HashMap<>();

    {
        map.put(new WeirdKey("weird"),new WeirdKey("sweared"));
        map.put(new WeirdKey("beard"),new WeirdKey("smeared"));
        map.put(new WeirdKey("shear"),new WeirdKey("feared"));
    }
}

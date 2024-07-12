package mappify.special;

import com.vicious.persist.annotations.C_NAME;
import com.vicious.persist.annotations.Save;

@C_NAME("testobjectb")
public class TestObjectB extends TestObjectA{
    @Save
    public int id = 1;
}

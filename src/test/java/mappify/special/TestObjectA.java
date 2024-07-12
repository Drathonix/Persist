package mappify.special;

import com.vicious.persist.annotations.C_NAME;
import com.vicious.persist.annotations.Save;

@C_NAME("testobjecta")
public class TestObjectA {
    @Save
    public String name = "TestObjectA";
}

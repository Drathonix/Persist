package mappify.enums;

import com.vicious.persist.annotations.C_NAME;
import com.vicious.persist.annotations.Save;

@C_NAME("testmappableenum")
public enum TestMappableEnum {
    E,
    F,
    G,
    H;

    @Save
    public int value = 0;
}

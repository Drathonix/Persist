package mappify.enums;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.EnumMap;
import java.util.Map;

public class TestEnumsAsKeysObject {
    @Save(raw = true)
    @Typing({TestMappableEnum.class,TestMappableEnum.class})
    public Map<TestMappableEnum,TestMappableEnum> map = new EnumMap<>(TestMappableEnum.class);

    {
        map.put(TestMappableEnum.E, TestMappableEnum.F);
        map.put(TestMappableEnum.G, TestMappableEnum.H);
    }
}
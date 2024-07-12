package mappify.enums;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.annotations.Typing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestObject6 {
    @Save
    public TestUnmappableEnum enum1 = TestUnmappableEnum.A;

    @Save
    public TestMappableEnum enum2 = TestMappableEnum.E;

    @Save
    public Object untypedMappable = TestMappableEnum.E;

    @Save
    public Object untyped = TestUnmappableEnum.A;


    @Save
    @Typing(TestMappableEnum.class)
    public List<TestMappableEnum> mappableEnumList = new ArrayList<>();

    {
        mappableEnumList.addAll(Arrays.asList(TestMappableEnum.values()));
    }
}

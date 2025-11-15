package validators;

import com.vicious.persist.annotations.DoNotSaveDefault;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.io.writer.wrapped.WrappedObjectMap;
import com.vicious.persist.mappify.Mappifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultedTest {
    @Save
    @DoNotSaveDefault
    public static int i = 0;

    @Save
    @DoNotSaveDefault
    public static String k = "";

    @Test
    @SuppressWarnings("all")
    public void testDefaulted(){
        WrappedObjectMap map = Mappifier.DEFAULT.mappify(DefaultedTest.class);
        assertEquals(null,map.get("i"));
        assertEquals(null,map.get("k"));
        i = 9;
        k="Hi";
        map = Mappifier.DEFAULT.mappify(DefaultedTest.class);
        assertEquals(9,map.get("i").object);
        assertEquals("Hi",map.get("k").object);
    }
}

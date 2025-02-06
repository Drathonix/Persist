package validators;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Range;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.shortcuts.PersistShortcuts;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RangeTest {
    @PersistentPath
    public static String path = "test_run_dir/ranger.test";

    @Save
    @Range(maximum = 10D,minimum = 0D)
    public static int i = 0;

    @Save
    @Range(maximum = 10D,minimum = 0D)
    public static float f = 0F;

    @Test
    public void testRanger(){
        assertEquals(0,i);
        assertEquals(0F,f);
        PersistShortcuts.readFromFile(RangeTest.class);
        assertEquals(10,i);
        assertEquals(10D,f);
    }
}

package mappify.resourcelocation;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.shortcuts.NotationFormat;
import com.vicious.persist.shortcuts.PersistShortcuts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test was added when I made a mistake in another project, but I'm keeping it in since it tests saving Stringifiable values with colons in them.
 */
public class ResourceLocationTest {
    @PersistentPath(NotationFormat.JSON5)
    public static String path = "test_run_dir/resourcelocation.test";
    @Save
    public static ResourceLocation RL = ResourceLocation.create("test","key");

    @Test
    public void testRL(){
        for (int i = 0; i < 3; i++) {
            l1();
        }
    }

    private void l1(){
        assertEquals(ResourceLocation.create("test","key"),RL);
        PersistShortcuts.init(ResourceLocationTest.class);
        assertEquals(ResourceLocation.create("test","key"),RL);
    }
}

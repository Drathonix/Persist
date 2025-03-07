package validators;

import com.vicious.persist.annotations.AltName;
import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Required;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.except.NoValuePresentException;
import com.vicious.persist.mappify.Mappifier;
import com.vicious.persist.shortcuts.NotationFormat;
import com.vicious.persist.shortcuts.PersistShortcuts;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AltNameTest {
    @PersistentPath
    public static String path = "test_run_dir/altname.test";

    @Save
    @AltName({"j","o"})
    public static int i = 0;

    @Test
    @SuppressWarnings("all")
    public void testAltName(){
        PersistShortcuts.saveAsFile(new Sub());
        PersistShortcuts.readFromFile(AltNameTest.class);
        assertEquals(1,i);
    }

    private static class Sub {
        @PersistentPath
        public String path = "test_run_dir/altname.test";
        @Save
        public int j = 1;
    }
}

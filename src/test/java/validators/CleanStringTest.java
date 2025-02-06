package validators;

import com.vicious.persist.annotations.CleanString;
import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Range;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.shortcuts.PersistShortcuts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CleanStringTest {
    @PersistentPath
    public static String path = "test_run_dir/cleanstring.test";

    @Save
    @CleanString(maxLength = 11)
    public static String str = "ThisIsValid";

    @Save
    @CleanString(replacements = {@CleanString.Replacement(target = "a",replacement = "o"),
            @CleanString.Replacement(target = "i",replacement = "bx")})
    public static String replaced = "ThisIsNotValid";

    @Test
    public void testCleanerTruncate(){
        str = "ThisIsValid";
        assertEquals(str,"ThisIsValid");
        str = "ThisIsNotValid";
        PersistShortcuts.saveAsFile(CleanStringTest.class);
        PersistShortcuts.readFromFile(CleanStringTest.class);
        assertEquals("ThisIsNotVa",str);
        PersistShortcuts.set(CleanStringTest.class,"ThisIsValid","str");
        assertEquals("ThisIsValid",str);
        PersistShortcuts.set(CleanStringTest.class,"ThisIsNotValid","str");
        assertEquals("ThisIsNotVa",str);
    }

    @Test
    public void testCleanerReplace(){
        replaced = "ThisIsNotValid";
        assertEquals(replaced,"ThisIsNotValid");
        PersistShortcuts.saveAsFile(CleanStringTest.class);
        PersistShortcuts.readFromFile(CleanStringTest.class);
        assertEquals("ThbxsIsNotVolbxd",replaced);
        PersistShortcuts.set(CleanStringTest.class,"ThisIsNotValid","replaced");
        assertEquals("ThbxsIsNotVolbxd",replaced);
    }
}

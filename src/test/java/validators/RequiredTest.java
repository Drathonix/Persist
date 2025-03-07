package validators;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Range;
import com.vicious.persist.annotations.Required;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.except.NoValuePresentException;
import com.vicious.persist.mappify.Mappifier;
import com.vicious.persist.shortcuts.NotationFormat;
import com.vicious.persist.shortcuts.PersistShortcuts;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RequiredTest {
    @PersistentPath
    public static String path = "test_run_dir/required.test";

    @Save
    @Required
    public static int i = 0;

    @Test
    @SuppressWarnings("all")
    public void testRequired(){
        PersistShortcuts.saveAsFile(RequiredTest.class);
        try {
            Map<String,Object> map = NotationFormat.JSON5.parse(Files.newInputStream(Paths.get(path)));
            assertDoesNotThrow(()->Mappifier.DEFAULT.unmappify(RequiredTest.class,(Map)map));
            map.remove("i");
            assertThrows(NoValuePresentException.class,()->Mappifier.DEFAULT.unmappify(RequiredTest.class,(Map)map));
         } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

package migrator;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.shortcuts.PersistShortcuts;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class MigratorTest {
    @Test
    public void testMigrator() throws IOException {
        assertEquals(0,MigratorObject2.i);
        assertEquals(10,MigratorObject1.i);
        Files.deleteIfExists(Paths.get("test_run_dir/migrator.json5"));
        PersistShortcuts.saveAsFile(MigratorObject1.class);
        assertFalse(Files.exists(Paths.get("test_run_dir/migrator.json5")));
        PersistShortcuts.readFromFile(MigratorObject2.class);
        assertEquals(10,MigratorObject2.i);
        assertTrue(Files.exists(Paths.get("test_run_dir/migrator.json5")));
        assertFalse(Files.exists(Paths.get("test_run_dir/migrator.gon")));
    }
}

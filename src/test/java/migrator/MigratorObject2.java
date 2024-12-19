package migrator;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.shortcuts.NotationFormat;

public class MigratorObject2 {
    @PersistentPath(NotationFormat.JSON5)
    public static String path = "test_run_dir/migrator.json5";

    @Save
    public static int i = 0;
}

package migrator;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Save;
import com.vicious.persist.shortcuts.NotationFormat;

public class MigratorObject1 {
    @PersistentPath(value = NotationFormat.GON,autoMigrate = false)
    public static String path = "test_run_dir/migrator.gon";

    @Save
    public static int i = 10;
}

package transformer;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.ReplaceKeys;
import com.vicious.persist.annotations.Save;

public class ReplaceKeysOld {
    @PersistentPath
    public static String path = "test_run_dir/replacer.test";

    @Save
    public static int key1 = 1;
    @Save
    public static int key2 = 1;
}

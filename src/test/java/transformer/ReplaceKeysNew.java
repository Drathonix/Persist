package transformer;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.ReplaceKeys;
import com.vicious.persist.annotations.Save;

@ReplaceKeys(staticReplacements = @ReplaceKeys.Pair(target = "key1", replacement = "translatedKey1"), transformerVersion = 1)
public class ReplaceKeysNew {
    @PersistentPath
    public static String path = "test_run_dir/replacer.test";

    @Save
    public static int translatedKey1 = 0;
    @Save
    public static int key2 = 0;
}

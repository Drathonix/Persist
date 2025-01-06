package transformer;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.ReplaceKeys;
import com.vicious.persist.annotations.Save;

@ReplaceKeys(staticReplacements = @ReplaceKeys.Pair(target = "translatedKey1", replacement = "key1"), transformerVersion = 1)
public class ReplaceKeysNew2 {
    @PersistentPath
    public static String path = "test_run_dir/replacer.test";

    @Save
    public static int key1 = 2;
    @Save
    public static int key2 = 2;
}

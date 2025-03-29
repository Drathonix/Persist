package transformer;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.ReplaceKeys;
import com.vicious.persist.annotations.Save;

@ReplaceKeys(staticReplacements = @ReplaceKeys.Pair(target = "on",replacement="inner/on"),transformerVersion = 1)
public class ReplaceKeysA2New {
    @PersistentPath
    public static String path = "test_run_dir/replacer_advanced2.test";

    @Save
    public static Inner inner = new Inner();

    public static class Inner {
        @Save
        public boolean on = false;
    }
}

package transformer;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.ReplaceKeys;
import com.vicious.persist.annotations.Save;

@ReplaceKeys(staticReplacements = @ReplaceKeys.Pair(target = "inner/inner2/k",replacement="k"),transformerVersion = 1)
public class ReplaceKeysAdvancedNew2 {
    @PersistentPath
    public static String path = "test_run_dir/replacer_advanced.test";

    @Save
    public static Inner inner = new Inner();

    @Save
    public static Inner otherInner = new Inner();

    @Save
    public static int k = 0;

    public static class Inner {
        @Save
        public Inner2 inner2 = new Inner2();
    }

    public static class Inner2 {
        @Save
        public int k = 0;
    }
}

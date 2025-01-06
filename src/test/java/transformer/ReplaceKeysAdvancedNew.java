package transformer;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.ReplaceKeys;
import com.vicious.persist.annotations.Save;

@ReplaceKeys(staticReplacements = @ReplaceKeys.Pair(target = "inner",replacement="binner"),transformerVersion = 1)
public class ReplaceKeysAdvancedNew {
    @PersistentPath
    public static String path = "test_run_dir/replacer_advanced.test";

    @Save
    public static Inner binner = new Inner();

    @Save
    public static Inner otherInner = new Inner();

    @ReplaceKeys(nonStaticReplacements = @ReplaceKeys.Pair(target = "inner2",replacement="binner2"),transformerVersion = 1)
    public static class Inner {
        @Save
        public Inner2 binner2 = new Inner2();
    }

    public static class Inner2 {
        @Save
        public int k = 0;
    }
}

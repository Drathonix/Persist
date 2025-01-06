package transformer;

import com.vicious.persist.annotations.PersistentPath;
import com.vicious.persist.annotations.Save;

public class ReplaceKeysAdvancedOld {
    @PersistentPath
    public static String path = "test_run_dir/replacer_advanced.test";

    @Save
    public static Inner inner = new Inner();

    @Save
    public static Inner otherInner = new Inner();

    public static class Inner {
        @Save
        public Inner2 inner2 = new Inner2();
    }

    public static class Inner2 {
        @Save
        public int k = 1;
    }
}

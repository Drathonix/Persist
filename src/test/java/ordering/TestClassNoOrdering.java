package ordering;

import com.vicious.persist.annotations.Ordering;
import com.vicious.persist.annotations.Save;

public class TestClassNoOrdering {
    @Save
    public static int b = 2;
    @Save
    public static String a = "p";
    @Save
    public static double c = 1.5;
}

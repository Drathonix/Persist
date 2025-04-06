package ordering;

import com.vicious.persist.annotations.Ordering;
import com.vicious.persist.annotations.Priority;
import com.vicious.persist.annotations.Save;

@Ordering({"a","b"})
public class TestClassOrdered {
    @Save
    public static int b = 2;
    @Save
    public static String a = "p";
    @Save
    public static double c = 1.5;
    @Save
    @Priority(1)
    public static float d = 1.57F;
}

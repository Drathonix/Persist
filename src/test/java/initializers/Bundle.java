package initializers;

import com.vicious.persist.annotations.Save;

public class Bundle {
    @Save
    int i;
    @Save
    String k;

    public Bundle(@Save("i") int i, @Save("k") String k) {
        this.i = i;
        this.k = k;
    }

    public static class Bundle2 extends Bundle {
        @Save.Constructor({"i","k"})
        public Bundle2(int i, String k) {
            super(i, k);
        }
    }

    public static class Bundle3 {
        @Save
        Bundle2 bundle = new Bundle2(10, "kkk");
    }
}

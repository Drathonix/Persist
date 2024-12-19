package initializers;

import java.util.Map;

public class Bundle {
    int i;
    String k;

    public Bundle(Map<Object,Object> map) {
        i = (int) map.get("i");
        k = (String) map.get("k");
    }
}

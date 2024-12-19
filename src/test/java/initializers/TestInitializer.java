package initializers;

import com.vicious.persist.mappify.registry.Initializers;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestInitializer {
    @Test
    public void customConstructor(){
        Initializers.registerCustomConstructor(Bundle.class,Bundle::new);
        Map map = new HashMap();
        map.put("i",1);
        map.put("k","yo");
        Bundle b = (Bundle) Initializers.construct(map,Bundle.class);
        assertEquals(1,b.i);
        assertEquals("yo",b.k);
    }
}

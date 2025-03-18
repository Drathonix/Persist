package initializers;

import com.vicious.persist.except.CannotInitializeException;
import com.vicious.persist.mappify.Mappifier;
import com.vicious.persist.mappify.registry.Initializers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestInitializer {
    @Test
    public void customConstructor(){
        Map<Object,Object> map = new HashMap<>();
        map.put("i",1);
        map.put("k","yo");
        Bundle b = Mappifier.DEFAULT.unmappifyThroughInit(Bundle.class,map);
        assertEquals(1,b.i);
        assertEquals("yo",b.k);
        Bundle.Bundle2 b2 = Mappifier.DEFAULT.unmappifyThroughInit(Bundle.Bundle2.class,map);
        assertEquals(1,b2.i);
        assertEquals("yo",b2.k);
        Map<Object,Object> imap = new HashMap<>();
        imap.put("bundle",map);
        Bundle.Bundle3 b3 = Mappifier.DEFAULT.unmappifyThroughInit(Bundle.Bundle3.class,imap);
        assertEquals(1,b3.bundle.i);
        assertEquals("yo",b3.bundle.k);
        List<Object> list = new ArrayList<>();
        list.add(2);
        list.add("yolo");
        b = Mappifier.DEFAULT.unmappifyThroughInit(Bundle.class,list);
        assertEquals(2,b.i);
        assertEquals("yolo",b.k);
        list.remove(1);
        map.remove("i");
        // Args too small.
        assertThrows(CannotInitializeException.class,()->Mappifier.DEFAULT.unmappifyThroughInit(Bundle.class,list));
        assertThrows(CannotInitializeException.class,()->Mappifier.DEFAULT.unmappifyThroughInit(Bundle.class,map));
        // Args not too small, but parameter is not present in the map.
        map.put("v",900);
        assertThrows(CannotInitializeException.class,()->Mappifier.DEFAULT.unmappifyThroughInit(Bundle.class,map));
    }
}

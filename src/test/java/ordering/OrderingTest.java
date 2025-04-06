package ordering;

import com.vicious.persist.io.writer.wrapped.WrappedObject;
import com.vicious.persist.io.writer.wrapped.WrappedObjectMap;
import com.vicious.persist.mappify.Mappifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderingTest {
    @Test
    public void testOrdering(){
        WrappedObjectMap wom = Mappifier.DEFAULT.mappify(TestClassNoOrdering.class);
        System.out.println(wom);
        wom = Mappifier.DEFAULT.mappify(TestClassOrdered.class);
        System.out.println(wom);
        List<WrappedObject> lst = new ArrayList<>(wom.values());
        assertEquals("p",lst.get(0).object);
        assertEquals(2,lst.get(1).object);
        assertEquals(1.57f,lst.get(2).object);
        assertEquals(1.5,lst.get(3).object);
    }
}

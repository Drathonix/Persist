package transformer;

import com.vicious.persist.io.writer.wrapped.WrappedObject;
import com.vicious.persist.io.writer.wrapped.WrappedObjectMap;
import com.vicious.persist.mappify.Mappifier;
import com.vicious.persist.mappify.reflect.ClassData;
import com.vicious.persist.shortcuts.NotationFormat;
import com.vicious.persist.shortcuts.PersistShortcuts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransformer {
    @Test
    public void testReplaceKeys() {
        //Will write a valid file to replace keys in.
        PersistShortcuts.saveAsFile(ReplaceKeysOld.class);
        assertEquals(0,ReplaceKeysNew.translatedKey1);
        assertEquals(0,ReplaceKeysNew.key2);
        //Reads the file and replaces the key.
        PersistShortcuts.readFromFile(ReplaceKeysNew.class);
        assertEquals(1,ReplaceKeysNew.key2);
        assertEquals(1,ReplaceKeysNew.translatedKey1);
        //Writes back to the file the new data and the transformation version.
        PersistShortcuts.saveAsFile(ReplaceKeysNew.class);
        assertEquals(2,ReplaceKeysNew2.key1);
        assertEquals(2,ReplaceKeysNew2.key2);
        //Reads the file but does not transform the key because the transformer ver is unchanged, resulting in the value of key1 being unchanged.
        PersistShortcuts.readFromFile(ReplaceKeysNew2.class);
        assertEquals(2,ReplaceKeysNew2.key1);
        assertEquals(1,ReplaceKeysNew2.key2);

        //Test moving value from root/map/map2 to root/map3/map4
        PersistShortcuts.saveAsFile(ReplaceKeysAdvancedOld.class);
        assertEquals(0,ReplaceKeysAdvancedNew.binner.binner2.k);
        assertEquals(0,ReplaceKeysAdvancedNew.otherInner.binner2.k);
        PersistShortcuts.readFromFile(ReplaceKeysAdvancedNew.class);
        assertEquals(1,ReplaceKeysAdvancedNew.binner.binner2.k);
        assertEquals(1,ReplaceKeysAdvancedNew.otherInner.binner2.k);

        //Test moving value from root/map/map2 to root
        assertEquals(0,ReplaceKeysAdvancedNew2.inner.inner2.k);
        assertEquals(0,ReplaceKeysAdvancedNew2.otherInner.inner2.k);
        assertEquals(0,ReplaceKeysAdvancedNew2.k);
        PersistShortcuts.readFromFile(ReplaceKeysAdvancedNew2.class);
        assertEquals(0,ReplaceKeysAdvancedNew2.inner.inner2.k);
        assertEquals(1,ReplaceKeysAdvancedNew2.otherInner.inner2.k);
        assertEquals(1,ReplaceKeysAdvancedNew2.k);

        //Test moving value from root to root/map
        WrappedObjectMap wom = Mappifier.DEFAULT.mappify(ReplaceKeysA2Old.class);
        wom.remove("inner");
        wom.put("on", WrappedObject.of(true));
        PersistShortcuts.saveAsFile(NotationFormat.JSON5,wom,ReplaceKeysA2Old.path);
        assertFalse(ReplaceKeysA2New.inner.on);
        PersistShortcuts.readFromFile(ReplaceKeysA2New.class);
        assertTrue(ReplaceKeysA2New.inner.on);
    }
}

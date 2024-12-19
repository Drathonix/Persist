package mappify.special;

import com.vicious.persist.mappify.registry.Stringify;

import java.util.Objects;

public class WeirdKey {
    final String actual;
    static {
        Stringify.register(WeirdKey.class,WeirdKey::new,WeirdKey::toString);
    }

    public WeirdKey(String actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        return actual;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WeirdKey weirdKey = (WeirdKey) object;
        return Objects.equals(actual, weirdKey.actual);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actual);
    }
}

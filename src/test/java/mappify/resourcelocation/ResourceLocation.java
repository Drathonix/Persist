package mappify.resourcelocation;

import com.vicious.persist.mappify.registry.Stringify;

import java.util.Objects;

public class ResourceLocation {
    String namespace;
    String key;

    private ResourceLocation(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }

    public static ResourceLocation create(String namespace, String key) {
        return new ResourceLocation(namespace, key);
    }

    public static ResourceLocation parse(String val) {
        String[] split = val.split(":");
        return new ResourceLocation(split[0],split[1]);
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResourceLocation that = (ResourceLocation) object;
        return Objects.equals(namespace, that.namespace) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, key);
    }

    static {
        Stringify.register(ResourceLocation.class,ResourceLocation::parse,ResourceLocation::toString);
    }
}

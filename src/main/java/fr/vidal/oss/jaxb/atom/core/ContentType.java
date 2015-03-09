package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

public class ContentType {

    @XmlValue
    private String type;

    @SuppressWarnings("unused") //jaxb
    private ContentType() {
        this(null);
    }

    private ContentType(String type) {
        this.type = type;
    }

    public static Builder builder(String type) {
        return new Builder(type);
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ContentType other = (ContentType) obj;
        return Objects.equals(this.type, other.type);
    }

    @Override
    public String toString() {
        return "ContentType{" +
            "type='" + type + '\'' +
            '}';
    }

    public static class Builder {

        private final String type;

        private Builder(String type) {
            this.type = type;
        }

        public ContentType build() {
            checkState(type != null, "type is mandatory");
            return new ContentType(type);
        }
    }
}

package fr.vidal.oss.jaxb.atom.core;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

import javax.xml.bind.annotation.XmlValue;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentType)) return false;

        ContentType that = (ContentType) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
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

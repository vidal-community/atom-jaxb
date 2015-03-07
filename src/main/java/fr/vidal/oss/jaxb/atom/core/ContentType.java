package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

public class ContentType {

    @XmlValue
    private String type;

    @SuppressWarnings("unused")
    private ContentType() {
        this(null);
    }

    public ContentType(String type) {
        this.type = type;
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
}

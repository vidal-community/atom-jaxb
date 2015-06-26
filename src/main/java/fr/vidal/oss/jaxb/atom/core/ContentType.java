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
}

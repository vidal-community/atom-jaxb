package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

public class Contents {

    public final static Contents EMPTY = new Contents();

    @XmlAttribute(name = "type")
    private final ContentType type;
    @XmlValue
    private final String contents;

    private Contents() {
        this(null, null);
    }

    private Contents(ContentType type, String contents) {
        this.type = type;
        this.contents = contents;
    }

    public static Contents contents(ContentType type, String contents) {
        return new Contents(type, contents);
    }

    public static Contents contents(String contents) {
        return new Contents(null, contents);
    }

    public ContentType getType() {
        return type;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, contents);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Contents other = (Contents) obj;
        return Objects.equals(this.type, other.type)
            && Objects.equals(this.contents, other.contents);
    }

    @Override
    public String toString() {
        return "Contents{" +
            "type=" + type +
            ", contents='" + contents + '\'' +
            '}';
    }
}

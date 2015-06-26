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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contents)) return false;

        Contents contents1 = (Contents) o;

        if (contents != null ? !contents.equals(contents1.contents) : contents1.contents != null) return false;
        if (type != null ? !type.equals(contents1.type) : contents1.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contents{" +
            "type=" + type +
            ", contents='" + contents + '\'' +
            '}';
    }
}

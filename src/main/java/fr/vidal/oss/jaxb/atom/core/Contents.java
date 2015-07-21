package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

public class Contents {

    public final static Contents EMPTY = builder().build();

    @XmlAttribute(name = "type")
    private final ContentType type;
    @XmlAttribute(name = "src")
    private final String src;
    @XmlValue
    private final String contents;

    //jaxb
    private Contents() {
        this(null, null, null);
    }

    private Contents(ContentType type, String contents, String src) {
        this.type = type;
        this.contents = contents;
        this.src = src;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ContentType getType() {
        return type;
    }

    public String getContents() {
        return contents;
    }

    public String getSrc() {
        return src;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, contents, src);
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
            && Objects.equals(this.contents, other.contents)
            && Objects.equals(this.src, other.src);
    }

    @Override
    public String toString() {
        return "Contents{" +
            "type=" + type +
            ",src=" + src +
            ", contents='" + contents + '\'' +
            '}';
    }

    public static class Builder {

        private ContentType type;
        private String src;
        private String contents;

        private Builder() {
        }

        public Builder withType(ContentType type) {
            this.type = type;
            return this;
        }

        public Builder withSrc(String src) {
            this.src = src;
            return this;
        }

        public Builder withContents(String contents) {
            this.contents = contents;
            return this;
        }

        public Contents build() {
            return new Contents(type, contents, src);
        }
    }
}

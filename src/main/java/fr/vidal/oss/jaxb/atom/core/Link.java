package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

public class Link {

    @XmlAttribute(name = "rel")
    private final LinkRel rel;
    @XmlAttribute(name = "type")
    private final String type;
    @XmlAttribute(name = "href", required = true)
    private final String href;
    @XmlAttribute(name = "title")
    private final String title;

    @SuppressWarnings("unused") // jaxb
    private Link() {
        this(null, null, null, null);
    }

    private Link(LinkRel rel, String type, String href, String title) {
        this.rel = rel;
        this.type = type;
        this.href = href;
        this.title = title;
    }

    public static Builder builder(String href) {
        return new Builder(href);
    }

    public LinkRel getRel() {
        return rel;
    }

    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rel, type, href, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        return Objects.equals(this.rel, other.rel) && Objects.equals(this.type, other.type) && Objects.equals(this.href, other.href) && Objects.equals(this.title, other.title);
    }

    @Override
    public String toString() {
        return "Link{" +
            "rel=" + rel +
            ", type='" + type + '\'' +
            ", href='" + href + '\'' +
            ", title='" + title + '\'' +
            '}';
    }

    public static class Builder {

        private final String href;
        private LinkRel rel;
        private String type;
        private String title;

        private Builder(String href) {
            this.href = href;
        }

        public Builder withRel(LinkRel rel) {
            this.rel = rel;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Link build() {
            checkState(href != null, "href is mandatory");
            return new Link(rel, type, href, title);
        }
    }
}

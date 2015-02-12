package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

public class Link {

    private LinkRel rel;
    private String type;
    private String href;
    private String title;

    Link() {}

    private Link(LinkRel rel, String type, String href, String title) {
        this.rel = rel;
        this.type = type;
        this.href = href;
        this.title = title;
    }

    @XmlAttribute(name = "rel")
    public LinkRel getRel() {
        return rel;
    }


    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }


    @XmlAttribute(name = "href", required = true)
    public String getHref() {
        return href;
    }


    @XmlAttribute(name = "title")
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

    public static class Builder {

        private LinkRel rel;
        private String type;
        private String href;
        private String title;

        public Builder withRel(LinkRel rel) {
            this.rel = rel;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withHref(String href) {
            this.href = href;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Link build() {
            return new Link(rel, type, href, title);
        }
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
}

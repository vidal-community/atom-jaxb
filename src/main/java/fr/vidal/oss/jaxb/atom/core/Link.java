package fr.vidal.oss.jaxb.atom.core;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

import javax.xml.bind.annotation.XmlAttribute;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;

        Link link = (Link) o;

        if (href != null ? !href.equals(link.href) : link.href != null) return false;
        if (rel != link.rel) return false;
        if (title != null ? !title.equals(link.title) : link.title != null) return false;
        if (type != null ? !type.equals(link.type) : link.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rel != null ? rel.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
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

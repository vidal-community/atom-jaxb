package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

public class Link {

    private LinkRel rel;
    private String type;
    private String href;
    private String title;

    Link() {}

    public Link(LinkRel rel, String type, String href, String title) {
        this.rel = rel;
        this.type = type;
        this.href = href;
        this.title = title;
    }

    @XmlAttribute(name = "rel")
    public LinkRel getRel() {
        return rel;
    }

    void setRel(LinkRel rel) {
        this.rel = rel;
    }

    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(name = "href", required = true)
    public String getHref() {
        return href;
    }

    void setHref(String uri) {
        this.href = uri;
    }

    @XmlAttribute(name = "title")
    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
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
}

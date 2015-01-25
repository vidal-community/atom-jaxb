package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

import static java.util.Collections.unmodifiableCollection;

@XmlRootElement(name = "feed")
@XmlType(propOrder = {"title", "subtitle", "links", "id", "author", "updateDate", "additionalElements", "entries"})
public class Feed {

    private Collection<Link> links = new ArrayList<>();
    private String title;
    private String subtitle;
    private String id;
    private Date updateDate;
    private Author author;
    private Collection<SimpleElement> additionalElements = new LinkedHashSet<>();
    private Collection<Entry> entries = new LinkedHashSet<>();

    @XmlElement(name = "title", required = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "subtitle")
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @XmlElement(name = "id", required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "updated", required = true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @XmlElement(name = "author")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @XmlElement(name = "link", required = true)
    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    public Feed addLink(Link link) {
        links.add(link);
        return this;
    }

    public void setLinks(Collection<Link> links) {
        this.links = links;
    }

    @XmlElement(name = "entry")
    public Collection<Entry> getEntries() {
        return unmodifiableCollection(entries);
    }

    public Feed addEntry(Entry entry) {
        entries.add(entry);
        return this;
    }

    public void setEntries(Collection<Entry> entries) {
        this.entries = entries;
    }

    @XmlAnyElement
    public Collection<SimpleElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    public Feed addAdditionalElement(SimpleElement element) {
        additionalElements.add(element);
        return this;
    }

    public void setAdditionalElements(Collection<SimpleElement> additionalElements) {
        this.additionalElements = additionalElements;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Feed other = (Feed) obj;
        return Objects.equals(this.id, other.id);
    }
}

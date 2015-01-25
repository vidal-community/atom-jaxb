package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;

import static java.util.Collections.unmodifiableCollection;

@XmlType(propOrder = {
    "title", "links", "category", "author", "id", "updateDate", "summary", "contents", "additionalElements"
})
public class Entry {

    private String title;
    private Summary summary;
    private Category category;
    private String id;
    private Date updateDate;
    private Author author;
    private Contents contents = Contents.EMPTY;
    private Collection<Link> links = new LinkedHashSet<>();
    private Collection<SimpleElement> additionalElements = new LinkedHashSet<>();

    @XmlElement(name = "title", required = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "summary")
    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @XmlElement(name = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    @XmlElement(name = "content")
    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    @XmlElement(name = "link", required = true)
    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    public Entry addLink(Link link) {
        links.add(link);
        return this;
    }

    public void setLinks(Collection<Link> links) {
        this.links = links;
    }

    @XmlAnyElement
    public Collection<SimpleElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    public Entry addAdditionalElement(SimpleElement element) {
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
        final Entry other = (Entry) obj;
        return Objects.equals(this.id, other.id);
    }
}

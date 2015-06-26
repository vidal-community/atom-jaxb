package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.unmodifiableCollection;

@XmlType(propOrder = {
    "title", "links", "category", "author", "id", "publishedDate", "updateDate", "summary", "contents", "additionalElements"
})
public class Entry {

    @XmlElement(name = "title", required = true)
    private final String title;
    @XmlElement(name = "summary")
    private final Summary summary;
    @XmlElement(name = "category")
    private final Category category;
    @XmlElement(name = "id", required = true)
    private final String id;
    @XmlElement(name = "published")
    private final Date publishedDate;
    @XmlElement(name = "updated", required = true)
    private final Date updateDate;
    @XmlElement(name = "author")
    private final Author author;
    @XmlElement(name = "content")
    private final Contents contents;
    @XmlElement(name = "link", required = true)
    private final Collection<Link> links;
    @XmlAnyElement
    private final Collection<SimpleElement> additionalElements;

    @SuppressWarnings("unused")
    private Entry() {
        this(builder());
    }

    private Entry(Builder builder) {
        additionalElements = builder.additionalElements;
        author = builder.author;
        category = builder.category;
        contents = builder.contents;
        id = builder.id;
        links = builder.links;
        summary = builder.summary;
        title = builder.title;
        publishedDate = builder.publishedDate;
        updateDate = builder.updateDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public Summary getSummary() {
        return summary;
    }

    public Category getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Author getAuthor() {
        return author;
    }

    public Contents getContents() {
        return contents;
    }

    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    public Collection<SimpleElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;

        Entry entry = (Entry) o;

        if (id != null ? !id.equals(entry.id) : entry.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Entry{" +
            "title='" + title + '\'' +
            ", summary=" + summary +
            ", category=" + category +
            ", id='" + id + '\'' +
            ", publishedDate=" + publishedDate +
            ", updateDate=" + updateDate +
            ", author=" + author +
            ", contents=" + contents +
            ", links=" + links +
            ", additionalElements=" + additionalElements +
            '}';
    }

    public static class Builder {

        private String title;
        private Summary summary;
        private Category category;
        private String id;
        private Date publishedDate;
        private Date updateDate;
        private Author author;
        private Contents contents = Contents.EMPTY;
        private Collection<Link> links = new LinkedHashSet<Link>();
        private Collection<SimpleElement> additionalElements = new LinkedHashSet<SimpleElement>();

        private Builder() {
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSummary(Summary summary) {
            this.summary = summary;
            return this;
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withPublishedDate(final Date publishedDate){
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder withUpdateDate(Date updateDate){
            this.updateDate = updateDate;
            return this;
        }

        public Builder withAuthor(Author author) {
            this.author = author;
            return this;
        }

        public Builder withContents(Contents contents) {
            this.contents = contents;
            return this;
        }

        public Builder addLink(Link link) {
            this.links.add(link);
            return this;
        }

        public Builder addSimpleElement(SimpleElement simpleElement) {
            this.additionalElements.add(simpleElement);
            return this;
        }

        public Entry build() {
            checkState(title != null, "title is mandatory");
            checkState(id != null, "id is mandatory");
            checkState(updateDate != null, "updateDate is mandatory");
            checkState(!links.isEmpty(), "links cannot be empty");
            return new Entry(this);
        }
    }
}

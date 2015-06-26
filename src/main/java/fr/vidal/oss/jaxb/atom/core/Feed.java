package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;

import static java.util.Collections.unmodifiableCollection;

@XmlRootElement(name = "feed")
@XmlType(propOrder = {"title", "subtitle", "links", "id", "author", "updateDate", "additionalElements", "entries"})
public class Feed {

    @XmlElement(name = "link", required = true)
    private final Collection<Link> links;
    @XmlElement(name = "title", required = true)
    private final String title;
    @XmlElement(name = "subtitle")
    private final String subtitle;
    @XmlElement(name = "id", required = true)
    private final String id;
    @XmlElement(name = "updated", required = true)
    private final Date updateDate;
    @XmlElement(name = "author")
    private final Author author;
    @XmlAnyElement
    private final Collection<SimpleElement> additionalElements;
    @XmlElement(name = "entry")
    private final Collection<Entry> entries;

    @SuppressWarnings("unused")
    private Feed() {
        this(new Builder());
    }

    private Feed(Builder builder) {
        links = builder.links;
        title = builder.title;
        subtitle = builder.subtitle;
        id = builder.id;
        updateDate = builder.updateDate;
        author = builder.author;
        additionalElements = builder.additionalElements;
        entries = builder.entries;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getId() {
        return id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Author getAuthor() {
        return author;
    }

    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    public Collection<Entry> getEntries() {
        return unmodifiableCollection(entries);
    }

    public Collection<SimpleElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feed)) return false;

        Feed feed = (Feed) o;

        if (id != null ? !id.equals(feed.id) : feed.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Feed{" +
            "links=" + links +
            ", title='" + title + '\'' +
            ", subtitle='" + subtitle + '\'' +
            ", id='" + id + '\'' +
            ", updateDate=" + updateDate +
            ", author=" + author +
            ", additionalElements=" + additionalElements +
            ", entries=" + entries +
            '}';
    }

    public static class Builder {

        private String title;
        private String subtitle;
        private String id;
        private Date updateDate;
        private Author author;
        private Collection<Link> links = new LinkedHashSet<Link>();
        private Collection<SimpleElement> additionalElements = new LinkedHashSet<SimpleElement>();
        private Collection<Entry> entries = new LinkedHashSet<Entry>();

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withSubtitle(String subtitle){
            this.subtitle = subtitle;
            return this;
        }

        public Builder withAuthor(Author author){
            this.author = author;
            return this;
        }

        public Builder withUpdateDate(Date updateDate){
            this.updateDate = updateDate;
            return this;
        }

        public Builder addLink(Link link){
            this.links.add(link);
            return this;
        }

        public Builder addSimpleElement(SimpleElement simpleElement){
            this.additionalElements.add(simpleElement);
            return this;
        }

        public Builder addEntry(Entry entry){
            this.entries.add(entry);
            return this;
        }

        public Feed build(){
            return new Feed(this);
        }
    }
}

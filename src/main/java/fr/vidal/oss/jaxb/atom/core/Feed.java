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

    private Collection<Link> links;
    private String title;
    private String subtitle;
    private String id;
    private Date updateDate;
    private Author author;
    private Collection<SimpleElement> additionalElements;
    private Collection<Entry> entries;

    Feed() {}

    Feed(Builder builder) {
        links = builder.links;
        title = builder.title;
        subtitle = builder.subtitle;
        id = builder.id;
        updateDate = builder.updateDate;
        author = builder.author;
        additionalElements = builder.additionalElements;
        entries = builder.entries;
    }


    @XmlElement(name = "title", required = true)
    public String getTitle() {
        return title;
    }


    @XmlElement(name = "subtitle")
    public String getSubtitle() {
        return subtitle;
    }


    @XmlElement(name = "id", required = true)
    public String getId() {
        return id;
    }


    @XmlElement(name = "updated", required = true)
    public Date getUpdateDate() {
        return updateDate;
    }


    @XmlElement(name = "author")
    public Author getAuthor() {
        return author;
    }


    @XmlElement(name = "link", required = true)
    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    @XmlElement(name = "entry")
    public Collection<Entry> getEntries() {
        return unmodifiableCollection(entries);
    }

    @XmlAnyElement
    public Collection<SimpleElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
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

    public static class Builder {

        private String title;
        private String subtitle;
        private String id;
        private Date updateDate;
        private Author author;
        private Collection<Link> links = new LinkedHashSet<>();
        private Collection<SimpleElement> additionalElements = new LinkedHashSet<>();
        private Collection<Entry> entries = new LinkedHashSet<>();

        public Builder() {
            title = null;
            subtitle = null;
            id = null;
            updateDate = null;
            author = null;
        }

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

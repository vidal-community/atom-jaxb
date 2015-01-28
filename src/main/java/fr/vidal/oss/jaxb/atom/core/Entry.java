package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
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
    private Contents contents;
    private Collection<Link> links;
    private Collection<SimpleElement> additionalElements;

    Entry() {}

    Entry(Builder builder) {
        additionalElements = builder.additionalElements;
        author = builder.author;
        category = builder.category;
        contents = builder.contents;
        id = builder.id;
        links = builder.links;
        summary = builder.summary;
        title = builder.title;
        updateDate = builder.updateDate;

    }

    @XmlElement(name = "title", required = true)
    public String getTitle() {
        return title;
    }


    @XmlElement(name = "summary")
    public Summary getSummary() {
        return summary;
    }


    @XmlElement(name = "category")
    public Category getCategory() {
        return category;
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


    @XmlElement(name = "content")
    public Contents getContents() {
        return contents;
    }


    @XmlElement(name = "link", required = true)
    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
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
        final Entry other = (Entry) obj;
        return Objects.equals(this.id, other.id);
    }

    public static class Builder {

        private String title;
        private Summary summary;
        private Category category;
        private String id;
        private Date updateDate;
        private Author author;
        private Contents contents = Contents.EMPTY;
        private Collection<Link> links = new LinkedHashSet<>();
        private Collection<SimpleElement> additionalElements = new LinkedHashSet<>();

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withSummary(Summary summary){
            this.summary = summary;
            return this;
        }

        public Builder withCategory(Category category){
            this.category = category;
            return this;
        }

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withUpdateDate(Date updateDate){
            this.updateDate = updateDate;
            return this;
        }

        public Builder withAuthor(Author author){
            this.author = author;
            return this;
        }

        public Builder withContents(Contents contents){
            this.contents = contents;
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

        public Entry build(){
           return new Entry(this);
        }
    }
}

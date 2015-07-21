package fr.vidal.oss.jaxb.atom.core;

import fr.vidal.oss.jaxb.atom.core.validation.EntryValidation;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlType(propOrder = {
    "title", "links", "categories", "authors", "contributors", "id", "publishedDate", "updateDate", "summary", "contents", "rights", "source", "additionalElements"
})
public class Entry {

    @XmlElement(name = "title", required = true)
    private final String title;
    @XmlElement(name = "summary")
    private final Summary summary;
    @XmlElement(name = "category")
    private final Collection<Category> categories;
    @XmlElement(name = "id", required = true)
    private final String id;
    @XmlElement(name = "published")
    private final Date publishedDate;
    @XmlElement(name = "updated", required = true)
    private final Date updateDate;
    @XmlElement(name = "author")
    private final Collection<Author> authors;
    @XmlElement(name = "contributor")
    private final Collection<Contributor> contributors;
    @XmlElement(name = "content")
    private final Contents contents;
    @XmlElement(name = "source")
    private final Source source;
    @XmlElement(name = "rights")
    private final String rights;
    @XmlElement(name = "link", required = true)
    private final Collection<Link> links;
    @XmlAnyElement
    private final Collection<SimpleElement> additionalElements;
    @XmlAnyAttribute
    private final Map<QName, String> additionalAttributes;

    @SuppressWarnings("unused")
    private Entry() {
        this(builder());
    }

    private Entry(Builder builder) {
        additionalElements = builder.additionalElements;
        authors = builder.authors;
        contributors = builder.contributors;
        categories = builder.categories;
        contents = builder.contents;
        rights = builder.rights;
        id = builder.id;
        links = builder.links;
        summary = builder.summary;
        title = builder.title;
        publishedDate = builder.publishedDate;
        updateDate = builder.updateDate;
        additionalAttributes = index(builder.additionalAttributes);
        source = builder.source;
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

    public Collection<Category> getCategories() {
        return unmodifiableCollection(categories);
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

    public Collection<Author> getAuthors() {
        return authors;
    }

    public Collection<Contributor> getContributors() {
        return unmodifiableCollection(contributors);
    }

    public Contents getContents() {
        return contents;
    }

    public String getRights() {
        return rights;
    }

    public Source getSource() {
        return source;
    }

    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    public Collection<SimpleElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    public Map<QName, String> getAdditionalAttributes() {
        return additionalAttributes;
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

    @Override
    public String toString() {
        return "Entry{" +
            "title='" + title + '\'' +
            ", summary=" + summary +
            ", category=" + categories +
            ", id='" + id + '\'' +
            ", publishedDate=" + publishedDate +
            ", updateDate=" + updateDate +
            ", authors=" + authors +
            ", contributors=" + contributors +
            ", contents=" + contents +
            ", rights=" + rights +
            ", links=" + links +
            ", additionalElements=" + additionalElements +
            ", source=" + source +
            '}';
    }

    private static final Map<QName, String> index(Collection<Attribute> additionalAttributes) {
        Map<QName, String> attributes = new HashMap<>(additionalAttributes.size());
        for (Attribute attribute : additionalAttributes) {
            Namespace namespace = attribute.getNamespace();
            QName name = new QName(namespace.uri(), attribute.getName(), namespace.prefix());
            attributes.put(name, attribute.getValue());
        }
        return attributes;
    }

    public static class Builder {

        private String title;
        private Summary summary;
        private Collection<Category> categories = new LinkedHashSet<>();
        private String id;
        private Date publishedDate;
        private Date updateDate;
        private Collection<Author> authors = new LinkedHashSet<>();
        private Collection<Contributor> contributors = new LinkedHashSet<>();
        private Contents contents = Contents.EMPTY;
        private Collection<Link> links = new LinkedHashSet<>();
        private Collection<SimpleElement> additionalElements = new LinkedHashSet<>();
        private Collection<Attribute> additionalAttributes = new LinkedHashSet<>();
        private String rights;
        private Source source;

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

        public Builder addCategory(Category category) {
            this.categories.add(category);
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

        public Builder addAuthor(Author author) {
            this.authors.add(author);
            return this;
        }

        public Builder addContributor(Contributor contributor) {
            this.contributors.add(contributor);
            return this;
        }

        public Builder withContents(Contents contents) {
            this.contents = contents;
            return this;
        }

        public Builder withRights(String rights) {
            this.rights = rights;
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

        public Builder addAttribute(Attribute attribute) {
            this.additionalAttributes.add(attribute);
            return this;
        }

        public Builder withSource(Source source) {
            this.source = source;
            return this;
        }

        public Entry build() {
            checkState(title != null, "title is mandatory");
            checkState(id != null, "id is mandatory");
            checkState(updateDate != null, "updateDate is mandatory");
            checkState(!links.isEmpty(), "links cannot be empty");
            checkState(EntryValidation.summaryIsMandatory(contents, summary), "Summary is mandatory");
            return new Entry(this);
        }
    }
}

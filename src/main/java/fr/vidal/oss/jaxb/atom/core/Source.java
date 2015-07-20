package fr.vidal.oss.jaxb.atom.core;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlType(propOrder = {
    "title", "subtitle", "id", "updateDate", "contributors", "generator", "icon", "logo", "rights", "authors", "categories", "links"
})
public class Source {

    @XmlElement(name = "author")
    private final Collection<Author> authors;
    @XmlElement(name = "category")
    private final Collection<Category> categories;
    @XmlElement(name = "link")
    private final Collection<Link> links;
    @XmlElement(name = "title")
    private final String title;
    @XmlElement(name = "subtitle")
    private final String subtitle;
    @XmlElement(name = "id")
    private final String id;
    @XmlElement(name = "updated")
    private final Date updateDate;
    @XmlElement(name = "contributor")
    private final Collection<Contributor> contributors;
    @XmlElement(name = "generator")
    private final Generator generator;
    @XmlElement(name = "icon")
    private final Icon icon;
    @XmlElement(name = "logo")
    private final Logo logo;
    @XmlElement(name = "rights")
    private final String rights;

    @SuppressWarnings("unused")
    private Source() {
        this(builder());
    }

    private Source(Builder builder) {
        authors = builder.authors;
        contributors = builder.contributors;
        categories = builder.categories;
        rights = builder.rights;
        id = builder.id;
        links = builder.links;
        title = builder.title;
        subtitle = builder.subtitle;
        updateDate = builder.updateDate;
        generator = builder.generator;
        icon = builder.icon;
        logo = builder.logo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public Collection<Category> getCategories() {
        return unmodifiableCollection(categories);
    }

    public String getId() {
        return id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Collection<Contributor> getContributors() {
        return unmodifiableCollection(contributors);
    }

    public String getRights() {
        return rights;
    }

    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Generator getGenerator() {
        return generator;
    }

    public Icon getIcon() {
        return icon;
    }

    public Logo getLogo() {
        return logo;
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
        final Source other = (Source) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Source{" +
            "authors=" + authors +
            ", categories=" + categories +
            ", links=" + links +
            ", title='" + title + '\'' +
            ", subtitle='" + subtitle + '\'' +
            ", id='" + id + '\'' +
            ", updateDate=" + updateDate +
            ", contributors=" + contributors +
            ", generator=" + generator +
            ", icon=" + icon +
            ", logo=" + logo +
            ", rights='" + rights + '\'' +
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
        private Collection<Category> categories = new LinkedHashSet<>();
        private String id;
        private Date updateDate;
        private Collection<Contributor> contributors = new LinkedHashSet<>();
        private Collection<Link> links = new LinkedHashSet<>();
        private String rights;
        private Collection<Author> authors = new LinkedHashSet<>();
        private String subtitle;
        private Generator generator;
        private Icon icon;
        private Logo logo;

        private Builder() {
        }

        public Builder addAuthor(Author author) {
            this.authors.add(author);
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSubtitle(String subtitle) {
            this.subtitle = subtitle;
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

        public Builder withUpdateDate(Date updateDate){
            this.updateDate = updateDate;
            return this;
        }

        public Builder addContributor(Contributor contributor) {
            this.contributors.add(contributor);
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

        public Builder withGenerator(Generator generator) {
            this.generator = generator;
            return this;
        }

        public Builder withIcon(Icon icon) {
            this.icon = icon;
            return this;
        }

        public Builder withLogo(Logo logo) {
            this.logo = logo;
            return this;
        }

        public Source build() {
            return new Source(this);
        }
    }
}

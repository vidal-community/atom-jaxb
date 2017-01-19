package fr.vidal.oss.jaxb.atom.core;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

import javax.xml.bind.annotation.XmlAttribute;

public class Category {

    @XmlAttribute(name = "term")
    private final String term;

    @XmlAttribute(name = "scheme")
    private final String scheme;

    @SuppressWarnings("unused") //jaxb
    private Category() {
        this(builder(""));
    }

    private Category(Builder builder) {
        this.term = builder.term;
        this.scheme = builder.scheme;
    }

    public static Builder builder(String term) {
        return new Builder(term);
    }

    public String getTerm() {
        return term;
    }

    public String getScheme() {
        return scheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (scheme != null ? !scheme.equals(category.scheme) : category.scheme != null) return false;
        if (term != null ? !term.equals(category.term) : category.term != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = term != null ? term.hashCode() : 0;
        result = 31 * result + (scheme != null ? scheme.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
            "term='" + term + "'," +
            "scheme='" + scheme + '\'' +
            '}';
    }

    public static class Builder {
        private final String term;
        private String scheme;

        private Builder(String term) {
            this.term = term;
        }

        public Builder withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Category build() {
            checkState(term != null, "term is mandatory");
            return new Category(this);
        }
    }
}

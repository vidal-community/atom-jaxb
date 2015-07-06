package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

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
    public int hashCode() {
        return Objects.hash(term, scheme);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        return Objects.equals(this.term, other.term)
            && Objects.equals(this.scheme, other.scheme);
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

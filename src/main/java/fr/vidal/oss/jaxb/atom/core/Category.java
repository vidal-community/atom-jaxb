package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

public class Category {

    @XmlAttribute(name = "term")
    private final String term;

    @SuppressWarnings("unused") //jaxb
    private Category() {
        this(null);
    }

    private Category(String term) {
        this.term = term;
    }

    public static Builder builder(String term) {
        return new Builder(term);
    }

    public String getTerm() {
        return term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (term != null ? !term.equals(category.term) : category.term != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return term != null ? term.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Category{" +
            "term='" + term + '\'' +
            '}';
    }

    public static class Builder {
        private final String term;

        private Builder(String term) {
            this.term = term;
        }

        public Category build() {
            checkState(term != null, "term is mandatory");
            return new Category(term);
        }
    }
}

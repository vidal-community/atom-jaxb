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
    public int hashCode() {
        return Objects.hash(term);
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
        return Objects.equals(this.term, other.term);
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

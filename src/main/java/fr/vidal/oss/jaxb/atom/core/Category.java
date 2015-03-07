package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

public class Category {

    @XmlAttribute(name = "term")
    private final String term;

    @SuppressWarnings("unused")
    private Category() {
        this(null);
    }

    public Category(String term) {
        this.term = term;
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
}

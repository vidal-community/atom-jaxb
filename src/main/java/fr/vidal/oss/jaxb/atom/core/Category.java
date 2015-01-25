package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;

public class Category {
    private String term;

    Category() {}

    public Category(String term) {
        this.term = term;
    }

    @XmlAttribute(name = "term")
    public String getTerm() {
        return term;
    }

    void setTerm(String term) {
        this.term = term;
    }
}

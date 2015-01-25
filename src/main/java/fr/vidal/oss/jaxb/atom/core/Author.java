package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;

public class Author {

    private String name;
    private String email;

    Author() {}

    public Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }
}

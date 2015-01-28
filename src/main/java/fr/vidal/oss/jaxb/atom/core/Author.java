package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;

public class Author {

    private String name;
    private String email;

    Author() {}

    private Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static Author author(String name) {
        return new Author(name, null);
    }

    public static Author author(String name, String email) {
        return new Author(name, email);
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

}

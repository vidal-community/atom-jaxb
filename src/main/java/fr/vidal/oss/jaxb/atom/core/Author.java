package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class Author {

    @XmlElement(required = true)
    private final String name;
    @XmlElement
    private final String email;

    @SuppressWarnings("unused")
    private Author() {
        this(null, null);
    }

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (email != null ? !email.equals(author.email) : author.email != null) return false;
        if (name != null ? !name.equals(author.name) : author.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}

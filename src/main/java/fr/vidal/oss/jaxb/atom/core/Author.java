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
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        return Objects.equals(this.name, other.name)
            && Objects.equals(this.email, other.email);
    }

    @Override
    public String toString() {
        return "Author{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}

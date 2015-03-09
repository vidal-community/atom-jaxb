package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

public class Author {

    @XmlElement(required = true)
    private final String name;
    @XmlElement
    private final String email;

    @SuppressWarnings("unused") //jaxb
    private Author() {
        this(null, null);
    }

    private Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static Builder builder(String name) {
        return new Builder(name);
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


    public static class Builder {

        private final String name;
        private String email;

        private Builder(String name) {
            this.name = name;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Author build() {
            checkState(name != null, "name is mandatory");
            return new Author(name, email);
        }
    }
}

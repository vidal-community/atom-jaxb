package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

public class Author {

    @XmlElement(required = true)
    private final String name;
    @XmlElement
    private final String email;
    @XmlElement
    private final String uri;

    @SuppressWarnings("unused") //jaxb
    private Author() {
        this(null, null, null);
    }

    private Author(String name, String email, String uri) {
        this.name = name;
        this.email = email;
        this.uri = uri;
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

    public String getUri() {
        return uri;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, uri);
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
            && Objects.equals(this.email, other.email)
            && Objects.equals(this.uri, other.uri);
    }

    @Override
    public String toString() {
        return "Author{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", uri='" + uri + '\'' +
            '}';
    }


    public static class Builder {

        private final String name;
        private String email;
        private String uri;

        private Builder(String name) {
            this.name = name;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Author build() {
            checkState(name != null, "name is mandatory");
            return new Author(name, email, uri);
        }
    }
}

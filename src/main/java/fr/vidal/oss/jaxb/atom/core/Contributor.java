package fr.vidal.oss.jaxb.atom.core;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

public class Contributor {

    @XmlElement(required = true)
    private final String name;
    @XmlElement
    private final String email;

    @SuppressWarnings("unused") //jaxb
    private Contributor() {
        this(null, null);
    }

    private Contributor(String name, String email) {
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
        final Contributor other = (Contributor) obj;
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

        public Contributor build() {
            checkState(name != null, "name is mandatory");
            return new Contributor(name, email);
        }
    }
}

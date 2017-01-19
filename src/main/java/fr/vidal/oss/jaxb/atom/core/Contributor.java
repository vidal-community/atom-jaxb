package fr.vidal.oss.jaxb.atom.core;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contributor that = (Contributor) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
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

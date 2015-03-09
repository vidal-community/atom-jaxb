package fr.vidal.oss.jaxb.atom.core;

import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;

public class Attribute {

    private final String name;
    private final String value;
    private final Namespace namespace;

    private Attribute(String name, String value, Namespace namespace) {
        this.name = name;
        this.value = value;
        this.namespace = namespace;
    }

    public static Builder builder(String name, String value) {
        return new Attribute.Builder(name, value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, namespace);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Attribute other = (Attribute) obj;
        return Objects.equals(this.name, other.name) && Objects.equals(this.value, other.value) && Objects.equals(this.namespace, other.namespace);
    }

    @Override
    public String toString() {
        return "Attribute{" +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            ", namespace=" + namespace +
            '}';
    }


    public static class Builder {

        private final String name;
        private final String value;
        private Namespace namespace;

        private Builder(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public Builder withNamespace(Namespace namespace) {
            this.namespace = namespace;
            return this;
        }

        public Attribute build() {
            checkState(name != null, "name is mandatory");
            checkState(value != null, "value is mandatory");
            return new Attribute(name, value, namespace);
        }
    }
}

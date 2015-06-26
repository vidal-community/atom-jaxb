package fr.vidal.oss.jaxb.atom.core;

import java.util.Objects;

public class Attribute {

    private final String name;
    private final String value;
    private final Namespace namespace;

    private Attribute(String name, String value, Namespace namespace) {
        this.name = name;
        this.value = value;
        this.namespace = namespace;
    }

    public static Attribute attribute(String name, String value) {
        return attribute(name, value, null);
    }

    public static Attribute attribute(String name, String value, Namespace namespace) {
        return new Attribute(name, value, namespace);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        if (namespace != null ? !namespace.equals(attribute.namespace) : attribute.namespace != null) return false;
        if (value != null ? !value.equals(attribute.value) : attribute.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (namespace != null ? namespace.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            ", namespace=" + namespace +
            '}';
    }
}

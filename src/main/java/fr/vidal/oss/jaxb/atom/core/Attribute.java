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
}

package fr.vidal.oss.jaxb.atom.core;

import java.util.Objects;

public class Attribute {

    private String name;
    private String value;
    private Namespace namespace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
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
}

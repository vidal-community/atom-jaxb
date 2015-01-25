package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;
import java.util.Objects;

import static java.util.Collections.unmodifiableCollection;

/**
 * Definition of a non-nested element.
 * This allows only text nodes as values in the marshalled output.
 */
public class SimpleElement {

    private Namespace namespace;
    private String tagName;
    private String value;
    private Collection<Attribute> attributes;

    SimpleElement() {}

    public SimpleElement(Namespace namespace, String tagName, String value, Collection<Attribute> attributes) {
        this.namespace = namespace;
        this.tagName = tagName;
        this.value = value;
        this.attributes = attributes;
    }

    public Namespace namespace() {
        return namespace;
    }

    public String tagName() {
        return tagName;
    }

    public String value() {
        return value;
    }

    public Collection<Attribute> attributes() {
        return unmodifiableCollection(attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, tagName, value, attributes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SimpleElement other = (SimpleElement) obj;
        return Objects.equals(this.namespace, other.namespace) && Objects.equals(this.tagName, other.tagName) && Objects.equals(this.value, other.value) && Objects.equals(this.attributes, other.attributes);
    }
}

package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;
import java.util.LinkedHashSet;
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

    SimpleElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.value = builder.value;
        this.attributes = builder.attributes;
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

    public static class Builder {

        private Namespace namespace;
        private String tagName;
        private String value;
        private Collection<Attribute> attributes = new LinkedHashSet<>();

        public SimpleElement build(){
            return new SimpleElement(this);
        }

        public Builder withNamespace(Namespace namespace){
            this.namespace = namespace;
            return this;
        }

        public Builder withTagName(String tagName){
            this.tagName = tagName;
            return this;
        }

        public Builder withValue(String value){
            this.value = value;
            return this;
        }

        public Builder addAttribute(Attribute attribute){
            this.attributes.add(attribute);
            return this;
        }
    }

    @Override
    public String toString() {
        return "SimpleElement{" +
            "namespace=" + namespace +
            ", tagName='" + tagName + '\'' +
            ", value='" + value + '\'' +
            ", attributes=" + attributes +
            '}';
    }
}

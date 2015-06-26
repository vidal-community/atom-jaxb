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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleElement)) return false;

        SimpleElement that = (SimpleElement) o;

        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) return false;
        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) return false;
        if (tagName != null ? !tagName.equals(that.tagName) : that.tagName != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private Namespace namespace;
        private String tagName;
        private String value;
        private Collection<Attribute> attributes = new LinkedHashSet<Attribute>();

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

package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.unmodifiableCollection;

@XmlType
public class AnyElement implements AdditionalElement {
    private Namespace namespace;
    private String tagName;
    private String value;
    private Collection<Attribute> attributes;
    @XmlAnyElement
    private Collection<AdditionalElement> anyElements;

    @SuppressWarnings("used by jaxb")
    public AnyElement() {
    }

    private AnyElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.value = builder.value;
        this.attributes = builder.attributes;
        this.anyElements = builder.anyElements;
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

    public Collection<AdditionalElement> anyElements() {
        return unmodifiableCollection(anyElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, tagName, value, attributes, anyElements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyElement that = (AnyElement) o;
        return Objects.equals(namespace, that.namespace) &&
            Objects.equals(tagName, that.tagName) &&
            Objects.equals(value, that.value) &&
            Objects.equals(attributes, that.attributes) &&
            Objects.equals(anyElements, that.anyElements);
    }

    @Override
    public String toString() {
        return "AnyElement{" +
            "namespace=" + namespace +
            ", tagName='" + tagName + '\'' +
            ", value='" + value + '\'' +
            ", attributes=" + attributes +
            ", anyElements=" + anyElements +
            '}';
    }

    public static Builder builder(String tagName) {
        return new Builder(tagName);
    }

    public static class Builder {
        private Namespace namespace;
        private String tagName;
        private String value;
        private Collection<Attribute> attributes = new LinkedHashSet<>();
        private Collection<AdditionalElement> anyElements = new LinkedHashSet<>();

        public Builder(String tagName) {
            this.tagName = tagName;
        }

        public Builder withNamespace(Namespace namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder withTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder addAttribute(Attribute attribute) {
            this.attributes.add(attribute);
            return this;
        }
        public Builder addAttributes(Collection<Attribute> attributes) {
            this.attributes.addAll(attributes);
            return this;
        }

        public Builder addAnyElement(AdditionalElement anyElement) {
            this.anyElements.add(anyElement);
            return this;
        }

        public Builder addAnyElements(Collection<AnyElement> anyElements) {
            this.anyElements.addAll(anyElements);
            return this;
        }

        public AnyElement build() {
            checkState(tagName != null, "The tagName is mandatory.");
            return new AnyElement(this);
        }
    }
}

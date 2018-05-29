package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.Attribute;
import fr.vidal.oss.jaxb.atom.core.ExtensionElement;
import fr.vidal.oss.jaxb.atom.core.Namespace;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;

public class AnyElement extends ExtensionElement {
    private Namespace namespace;
    private String tagName;
    private Collection<Attribute> attributes;
    @XmlAnyElement
    private Collection<ExtensionElement> anyElements;

    @SuppressWarnings("used by jaxb")
    private AnyElement() {
    }

    private AnyElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.attributes = builder.attributes;
        this.anyElements = builder.anyElements;
    }

    public Namespace namespace() {
        return namespace;
    }

    public String tagName() {
        return tagName;
    }


    @Override
    public JAXBElement toJAXBElement(ExtensionElement element) {
        return new JAXBElement<>(
            qualifiedName(element),
            AnyElement.class,
            (AnyElement) element
        );
    }

    public Collection<Attribute> attributes() {
        return unmodifiableCollection(attributes);
    }

    public Collection<ExtensionElement> anyElements() {
        return unmodifiableCollection(anyElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, tagName, attributes, anyElements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyElement that = (AnyElement) o;
        return Objects.equals(namespace, that.namespace) &&
            Objects.equals(tagName, that.tagName) &&
            Objects.equals(attributes, that.attributes) &&
            Objects.equals(anyElements, that.anyElements);
    }

    @Override
    public String toString() {
        return "AnyElement{" +
            "namespace=" + namespace +
            ", tagName='" + tagName + '\'' +
            ", attributes=" + attributes +
            ", anyElements=" + anyElements +
            '}';
    }

    public static Builder builder(String tagName) {
        return new Builder(tagName);
    }

    public static class Builder {
        private Namespace namespace;
        private final String tagName;
        private Collection<Attribute> attributes = new LinkedHashSet<>();
        private Collection<ExtensionElement> anyElements = new LinkedHashSet<>();

        public Builder(String tagName) {
            this.tagName = tagName;
        }

        public Builder withNamespace(Namespace namespace) {
            this.namespace = namespace;
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

        public Builder addAnyElement(ExtensionElement anyElement) {
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

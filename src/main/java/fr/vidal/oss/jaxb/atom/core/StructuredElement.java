package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableCollection;

/**
 * Definition of a structured extension element.
 * Refer to the ATOM specification: @link https://tools.ietf.org/html/rfc4287#section-6.4.2
 */
@XmlType
public class StructuredElement implements ExtensionElement {

    private static final String SHOULD_CONTAIN_ATTRIBUTE_OR_CHILD = "A structured element should contain at least a child element or an attribute.";

    private Namespace namespace;
    private String tagName;
    private Collection<Attribute> attributes;
    @XmlAnyElement
    private Collection<ExtensionElement> extensionElements;

    @SuppressWarnings("unused") //jaxb
    public StructuredElement() {
    }

    private StructuredElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.attributes = builder.attributes;
        this.extensionElements = builder.extensionElements;
    }

    @Override
    public Namespace namespace() {
        return namespace;
    }

    @Override
    public String tagName() {
        return tagName;
    }

    @Override
    public Collection<Attribute> attributes() {
        return unmodifiableCollection(attributes);
    }

    public Collection<ExtensionElement> getExtensionElements() {
        return unmodifiableCollection(extensionElements);
    }

    public static Builder builder(String tagName, ExtensionElement extensionElement) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(extensionElement != null, SHOULD_CONTAIN_ATTRIBUTE_OR_CHILD);

        return new Builder(tagName, extensionElement);
    }

    public static Builder builder(String tagName, Attribute attribute) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(attribute != null, SHOULD_CONTAIN_ATTRIBUTE_OR_CHILD);

        return new Builder(tagName, attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, tagName, attributes, extensionElements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructuredElement that = (StructuredElement) o;
        return Objects.equals(namespace, that.namespace) &&
            Objects.equals(tagName, that.tagName) &&
            Objects.equals(attributes, that.attributes) &&
            Objects.equals(extensionElements, that.extensionElements);
    }

    @Override
    public String toString() {
        return "StructuredElement{" +
            "namespace=" + namespace +
            ", tagName='" + tagName + '\'' +
            ", attributes=" + attributes +
            ", extensionElements=" + extensionElements +
            '}';
    }

    public static class Builder {

        private Namespace namespace;
        private String tagName;
        private Collection<Attribute> attributes;
        private Collection<ExtensionElement> extensionElements;

        private Builder(String tagName, ExtensionElement extensionElement) {
            this(tagName, Collections.<Attribute>emptyList(), singleton(extensionElement));
        }

        private Builder(String tagName, Attribute attribute) {
            this(tagName, singleton(attribute), Collections.<ExtensionElement>emptyList());
        }

        private Builder(String tagName, Collection<Attribute> attributes, Collection<ExtensionElement> extensionElements) {
            this.tagName = tagName;
            this.attributes = new LinkedHashSet<>(attributes);
            this.extensionElements = new LinkedHashSet<>(extensionElements);
        }

        public Builder withNamespace(Namespace namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder addChildElement(ExtensionElement childElement) {
            this.extensionElements.add(childElement);
            return this;
        }

        public Builder addChildElements(List<ExtensionElement> childElements) {
            this.extensionElements.addAll(childElements);
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

        public StructuredElement build() {
            return new StructuredElement(this);
        }
    }
}

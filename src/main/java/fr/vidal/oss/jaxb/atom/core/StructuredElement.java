package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Stream;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.*;

/**
 * Definition of a structured extension element.
 * Refer to the ATOM specification: @link https://tools.ietf.org/html/rfc4287#section-6.4.2
 */
public class StructuredElement extends ExtensionElement {

    static final String SHOULD_CONTAIN_ATTRIBUTE_OR_CHILD = "A structured element should contain at least a child element or an attribute.";

    private Namespace namespace;
    private String tagName;
    private Collection<Attribute> attributes;

    @XmlAnyElement
    private Collection<ExtensionElement> extensionElements;

    @SuppressWarnings("unused") //jaxb
    private StructuredElement() {
    }

    private StructuredElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.attributes = builder.attributes;
        this.extensionElements = builder.extensionElements;
    }

    @Override
    public JAXBElement toJAXBElement() {
        return new JAXBElement<>(qualifiedName(), StructuredElement.class, this);
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

    public static class Builder implements ExtensionElement.Builder<StructuredElement, Builder> {

        private Namespace namespace;
        private String tagName;
        private Collection<Attribute> attributes;
        private Collection<ExtensionElement> extensionElements;

        Builder(String tagName, ExtensionElement extensionElement) {
            this(tagName, singleton(extensionElement));
        }

        Builder(String tagName, Collection<ExtensionElement> extensionElements) {
            this(tagName, emptyList(), extensionElements);
        }

        Builder(String tagName, Attribute attribute) {
            this(tagName, singleton(attribute), emptyList());
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

        public Builder addChild(ExtensionElement child) {
            this.extensionElements.add(child);
            return this;
        }

        public Builder addChildren(ExtensionElement... children) {
            return addChildren(Arrays.asList(children));
        }

        public Builder addChildren(Iterable<ExtensionElement> children) {
            children.forEach(this::addChild);
            return this;
        }

        public Builder addChildren(Stream<ExtensionElement> children) {
            children.forEach(this::addChild);
            return this;
        }

        public Builder addAttribute(Attribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        public Builder addAttributes(Attribute... attributes) {
            return addAttributes(Arrays.asList(attributes));
        }

        public Builder addAttributes(Iterable<Attribute> attributes) {
            attributes.forEach(this::addAttribute);
            return this;
        }

        public StructuredElement build() {
            checkState(tagName != null, TAG_NAME_IS_MANDATORY);
            checkState(hasAttributeOrChild(), SHOULD_CONTAIN_ATTRIBUTE_OR_CHILD);
            return new StructuredElement(this);
        }

        private boolean hasAttributeOrChild() {
            return containsValue(attributes)
                || containsValue(extensionElements);
        }

        private boolean containsValue(Collection<?> collection) {
            return collection.stream()
                .anyMatch(Objects::nonNull);
        }
    }
}

package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableCollection;

/**
 * Definition of a structured extension element.
 * Refer to the ATOM specification: @link https://tools.ietf.org/html/rfc4287#section-6.4.2
 */
@XmlType
public class StructuredElement implements ExtensionElement {

    private Namespace namespace;
    private String tagName;
    private String value;
    private Collection<Attribute> attributes;
    @XmlAnyElement
    private Collection<ExtensionElement> extensionElements;

    @SuppressWarnings("unused") //jaxb
    public StructuredElement() {
    }

    private StructuredElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.value = builder.value;
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

    @Override
    public String value() {
        return value;
    }

    public Collection<ExtensionElement> getExtensionElements() {
        return unmodifiableCollection(extensionElements);
    }

    public static Builder builder(String tagName, ExtensionElement extensionElement) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(extensionElement != null ,
            "A structured element should contain at least a child element.");

        return new Builder(tagName, extensionElement);
    }

    public static Builder builder(String tagName, Attribute attribute) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(attribute != null ,
            "A structured element should contain at least an attribute.");

        return new Builder(tagName, attribute);
    }

    public static class Builder {

        private Namespace namespace;
        private String tagName;
        private String value;
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

        public Builder withValue(String value) {
            this.value = value;
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

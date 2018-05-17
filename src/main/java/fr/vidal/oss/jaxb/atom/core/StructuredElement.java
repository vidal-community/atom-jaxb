package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableCollection;

/**
 * Definition of a structured extension element.
 * ADD LINKS TO THE DOC
 */
public class StructuredElement implements AdditionalElement {

    private Namespace namespace;
    private String tagName;
    private Collection<Attribute> attributes;
    private Collection<AdditionalElement> additionalElements;

    @SuppressWarnings("unused") //jaxb
    private StructuredElement() {
    }

    private StructuredElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.attributes = builder.attributes;
        this.additionalElements = builder.additionalElements;
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
        return "";
    }

    public Collection<AdditionalElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    public static Builder builder(String tagName, AdditionalElement additionalElement) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(additionalElement != null ,
            "A structured element should contain at least a child element.");

        return new Builder(tagName, additionalElement);
    }

    public static Builder builder(String tagName, Attribute attribute) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(attribute != null ,
            "A structured element should contain at least an attribute.");

        return new Builder(tagName, singleton(attribute));
    }

    public static class Builder {

        private Namespace namespace;
        private String tagName;
        private Collection<Attribute> attributes;
        private Collection<AdditionalElement> additionalElements;

        private Builder(String tagName, AdditionalElement additionalElement) {
            this(tagName, Collections.<Attribute>emptyList(), singleton(additionalElement));
        }

        private Builder(String tagName, Collection<Attribute> attributes) {
            this(tagName, attributes, Collections.<AdditionalElement>emptyList());
        }

        private Builder(String tagName, Collection<Attribute> attributes, Collection<AdditionalElement> additionalElements) {
            this.tagName = tagName;
            this.attributes = new LinkedHashSet<>(attributes);
            this.additionalElements = new LinkedHashSet<>(additionalElements);
        }

        public Builder withNamespace(Namespace namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder addChildElement(AdditionalElement childElement) {
            this.additionalElements.add(childElement);
            return this;
        }

        public Builder addChildElements(Collection<AdditionalElement> childElements) {
            this.additionalElements.addAll(childElements);
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

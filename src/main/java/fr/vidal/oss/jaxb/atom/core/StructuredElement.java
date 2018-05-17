package fr.vidal.oss.jaxb.atom.core;

import java.util.*;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.*;
import static java.util.Arrays.*;
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

    public static Builder builder(String tagName, AdditionalElement additionalElement, AdditionalElement ... additionalElements) {
        return new Builder(tagName, additionalElement);
    }

    public static Builder builder(String tagName, Attribute attribute, Attribute... attributes) {
        checkState(tagName != null, "TagName is mandatory.");

        Collection<Attribute> combinedAttributes = concatAttributes(attribute, attributes);
        checkState(!combinedAttributes.isEmpty() && combinedAttributes.iterator().next() != null ,
            "A structured element should contain at least an attribute.");

        return new Builder(tagName, combinedAttributes);
    }

    private static Collection<Attribute> concatAttributes(Attribute attribute, Attribute[] attributes) {
        Collection<Attribute> attributesCombined = new ArrayList<>();
        attributesCombined.add(attribute);
        attributesCombined.addAll(asList(attributes));
        return attributesCombined;
    }

    public static class Builder {

        private Namespace namespace;
        private String tagName;
        private Collection<Attribute> attributes;
        private Collection<AdditionalElement> additionalElements;

        public Builder(String tagName, AdditionalElement additionalElement) {
            this(tagName, Collections.<Attribute>emptyList(), singleton(additionalElement));
        }

        public Builder(String tagName, Collection<Attribute> attributes) {
            this(tagName, attributes, Collections.<AdditionalElement>emptyList());
        }

        public Builder(String tagName, Collection<Attribute> attributes, Collection<AdditionalElement> additionalElements) {
            this.tagName = tagName;
            this.attributes = attributes;
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

        public StructuredElement build() {
            return new StructuredElement(this);
        }
    }
}

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

/**
 * Definition of a non-nested element.
 * This allows only text nodes as values in the marshalled output.
 */
public class SimpleElement extends ExtensionElement {

    private Namespace namespace;
    private String tagName;
    private String value;
    private Collection<Attribute> attributes;

    @SuppressWarnings("unused") // jaxb
    private SimpleElement() {
    }

    private SimpleElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.value = builder.value;
        this.attributes = builder.attributes;
    }

    public static Builder builder(String tagName, String value) {
        return new Builder(tagName, value);
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


    @Override
    public JAXBElement<String> toJAXBElement(ExtensionElement element) {
        SimpleElement simpleElement = (SimpleElement) element;
        return new JAXBElement<>(
            qualifiedName(element),
            String.class,
            simpleElement.value()
        );
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

    @Override
    public String toString() {
        return "SimpleElement{" +
            "namespace=" + namespace +
            ", tagName='" + tagName + '\'' +
            ", value='" + value + '\'' +
            ", attributes=" + attributes +
            '}';
    }

    public static class Builder {

        private final String tagName;
        private final String value;
        private Namespace namespace;
        private Collection<Attribute> attributes = new LinkedHashSet<>();

        private Builder(String tagName, String value) {
            this.tagName = tagName;
            this.value = value;
        }

        public Builder withNamespace(Namespace namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder addAttribute(Attribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        public SimpleElement build() {
            checkState(tagName != null, "tagName is mandatory");
            checkState(value != null, "value is mandatory");
            return new SimpleElement(this);
        }
    }
}

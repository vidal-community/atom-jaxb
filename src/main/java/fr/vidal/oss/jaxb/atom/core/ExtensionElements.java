package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;

public final class ExtensionElements {

    private ExtensionElements() {
    }

    public static SimpleElement.Builder simpleElement(String tagName, String value) {
        return new SimpleElement.Builder(tagName, value);
    }

    public static StructuredElement.Builder structuredElement(String tagName, Attribute attribute) {
        return new StructuredElement.Builder(tagName, attribute);
    }

    public static StructuredElement.Builder structuredElement(String tagName, ExtensionElement element) {
        return new StructuredElement.Builder(tagName, element);
    }

    public static StructuredElement.Builder structuredElement(String tagName, Collection<ExtensionElement> children) {
        return new StructuredElement.Builder(tagName, children);
    }

}

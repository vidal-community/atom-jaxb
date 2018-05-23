package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;

class StructuredElementToJAXB implements ToJAXBElement<StructuredElement, StructuredElement> {
    private ElementQNameFactory elementQNameFactory;

    public StructuredElementToJAXB(ElementQNameFactory elementQNameFactory) {
        this.elementQNameFactory = elementQNameFactory;
    }

    @Override
    public JAXBElement<StructuredElement> convert(StructuredElement element) {
        return new JAXBElement<>(
            elementQNameFactory.qualifiedName(element),
            StructuredElement.class,
            element
        );
    }
}

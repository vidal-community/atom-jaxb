package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;

class AnyElementToJAXB implements ToJAXBElement<AnyElement, AnyElement> {
    private ElementQNameFactory elementQNameFactory;

    public AnyElementToJAXB(ElementQNameFactory elementQNameFactory) {
        this.elementQNameFactory = elementQNameFactory;
    }

    @Override
    public JAXBElement<AnyElement> convert(AnyElement element) {
        return new JAXBElement<>(
            elementQNameFactory.qualifiedName(element),
            AnyElement.class,
            element
        );
    }
}

package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;

class SimpleElementToJAXB implements ToJAXBElement<String, SimpleElement> {
    private ElementQNameFactory elementQNameFactory;

    public SimpleElementToJAXB(ElementQNameFactory elementQNameFactory) {
        this.elementQNameFactory = elementQNameFactory;
    }

    @Override
    public JAXBElement<String> convert(SimpleElement element) {
        String value = element.value() == null ? "" : element.value();
        return new JAXBElement<>(
            elementQNameFactory.qualifiedName(element),
            String.class,
            value
        );
    }
}

package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;

class SimpleElementExtensionConverter implements ExtensionElementConverter {
    private ElementQNameFactory elementQNameFactory;

    SimpleElementExtensionConverter(ElementQNameFactory elementQNameFactory) {
        this.elementQNameFactory = elementQNameFactory;
    }

    @Override
    public JAXBElement<String> convert(ExtensionElement element) {
        String value = element.value() == null ? "" : element.value();
        return new JAXBElement<>(
            elementQNameFactory.qualifiedName(element),
            String.class,
            value
        );
    }

    @Override
    public boolean canConvert(ExtensionElement extensionElement) {
        return extensionElement instanceof SimpleElement;
    }
}

package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;

class AnyElementExtensionConverter implements ExtensionElementConverter {
    private ElementQNameFactory elementQNameFactory;

    AnyElementExtensionConverter(ElementQNameFactory elementQNameFactory) {
        this.elementQNameFactory = elementQNameFactory;
    }

    @Override
    public JAXBElement<AnyElement> convert(ExtensionElement element) {
        return new JAXBElement<>(
            elementQNameFactory.qualifiedName(element),
            AnyElement.class,
            (AnyElement) element
        );
    }

    @Override
    public boolean canConvert(ExtensionElement extensionElement) {
        return extensionElement instanceof AnyElement;
    }
}

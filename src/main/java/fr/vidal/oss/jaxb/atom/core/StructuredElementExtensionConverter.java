package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;

class StructuredElementExtensionConverter implements ExtensionElementConverter {
    private ElementQNameFactory elementQNameFactory;

    StructuredElementExtensionConverter(ElementQNameFactory elementQNameFactory) {
        this.elementQNameFactory = elementQNameFactory;
    }

    @Override
    public JAXBElement<StructuredElement> convert(ExtensionElement element) {
        return new JAXBElement<>(
            elementQNameFactory.qualifiedName(element),
            StructuredElement.class,
            (StructuredElement) element
        );
    }

    @Override
    public boolean canConvert(ExtensionElement extensionElement) {
        return extensionElement instanceof StructuredElement;
    }
}

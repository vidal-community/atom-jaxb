package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ElementQNameFactory;
import fr.vidal.oss.jaxb.atom.core.ExtensionElement;

import javax.xml.bind.JAXBElement;

public class StructuredElementExtensionConverter implements ExtensionElementConverter {
    private ElementQNameFactory elementQNameFactory;

    public StructuredElementExtensionConverter(ElementQNameFactory elementQNameFactory) {
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

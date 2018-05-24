package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ElementQNameFactory;
import fr.vidal.oss.jaxb.atom.core.ExtensionElement;

import javax.xml.bind.JAXBElement;

public class AnyElementExtensionConverter implements ExtensionElementConverter {
    private ElementQNameFactory elementQNameFactory;

    public AnyElementExtensionConverter(ElementQNameFactory elementQNameFactory) {
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

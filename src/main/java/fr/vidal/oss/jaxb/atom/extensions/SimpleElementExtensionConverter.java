package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ElementQNameFactory;
import fr.vidal.oss.jaxb.atom.core.ExtensionElement;

import javax.xml.bind.JAXBElement;

public class SimpleElementExtensionConverter implements ExtensionElementConverter {
    private ElementQNameFactory elementQNameFactory;

    public SimpleElementExtensionConverter(ElementQNameFactory elementQNameFactory) {
        this.elementQNameFactory = elementQNameFactory;
    }

    @Override
    public JAXBElement<String> convert(ExtensionElement element) {
        SimpleElement simpleElement = (SimpleElement) element;
        return new JAXBElement<>(
            elementQNameFactory.qualifiedName(element),
            String.class,
            simpleElement.value()
        );
    }

    @Override
    public boolean canConvert(ExtensionElement extensionElement) {
        return extensionElement instanceof SimpleElement;
    }
}

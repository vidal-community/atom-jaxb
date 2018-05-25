package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ExtensionElement;

import javax.xml.bind.JAXBElement;

class AnyElementExtensionConverter extends ExtensionElementConverter {

    @Override
    public JAXBElement<AnyElement> convert(ExtensionElement element) {
        return new JAXBElement<>(
            qualifiedName(element),
            AnyElement.class,
            (AnyElement) element
        );
    }
}

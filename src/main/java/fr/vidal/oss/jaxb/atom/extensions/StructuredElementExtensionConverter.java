package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ExtensionElement;

import javax.xml.bind.JAXBElement;

class StructuredElementExtensionConverter extends ExtensionElementConverter {

    @Override
    public JAXBElement<StructuredElement> convert(ExtensionElement element) {
        return new JAXBElement<>(
            qualifiedName(element),
            StructuredElement.class,
            (StructuredElement) element
        );
    }

}

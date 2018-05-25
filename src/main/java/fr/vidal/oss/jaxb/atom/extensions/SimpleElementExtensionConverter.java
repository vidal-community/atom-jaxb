package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ExtensionElement;

import javax.xml.bind.JAXBElement;

class SimpleElementExtensionConverter extends ExtensionElementConverter {

    @Override
    public JAXBElement<String> convert(ExtensionElement element) {
        SimpleElement simpleElement = (SimpleElement) element;
        return new JAXBElement<>(
            qualifiedName(element),
            String.class,
            simpleElement.value()
        );
    }

}

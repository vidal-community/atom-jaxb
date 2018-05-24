package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ExtensionElement;

import javax.xml.bind.JAXBElement;

public interface ExtensionElementConverter {

    boolean canConvert(ExtensionElement extensionElement);

    <T> JAXBElement<T> convert(ExtensionElement element);
}

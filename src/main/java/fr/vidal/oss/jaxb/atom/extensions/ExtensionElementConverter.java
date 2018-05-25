package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.ExtensionElement;
import fr.vidal.oss.jaxb.atom.core.Namespace;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public abstract class ExtensionElementConverter {

    public abstract <T> JAXBElement<T> convert(ExtensionElement element);

    protected QName qualifiedName(ExtensionElement simpleElement) {
        Namespace namespace = simpleElement.namespace();
        if (namespace != null) {
            return new QName(namespace.uri(), simpleElement.tagName(), namespace.prefix());
        }
        return new QName(simpleElement.tagName());
    }
}

package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public abstract class ExtensionElement {
    protected abstract Namespace namespace();

    public abstract String tagName();

    public abstract Collection<Attribute> attributes();

    public abstract <T> JAXBElement<T> toJAXBElement(ExtensionElement element);

    protected QName qualifiedName(ExtensionElement extensionElement) {
        Namespace namespace = extensionElement.namespace();
        if (namespace != null) {
            return new QName(namespace.uri(), extensionElement.tagName(), namespace.prefix());
        }
        return new QName(extensionElement.tagName());
    }
}

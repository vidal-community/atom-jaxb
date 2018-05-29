package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public abstract class ExtensionElement {
    protected abstract Namespace namespace();

    protected abstract String tagName();

    protected abstract Collection<Attribute> attributes();

    protected abstract JAXBElement toJAXBElement(ExtensionElement element);

    protected QName qualifiedName(ExtensionElement extensionElement) {
        Namespace namespace = extensionElement.namespace();
        if (namespace != null) {
            return new QName(namespace.uri(), extensionElement.tagName(), namespace.prefix());
        }
        return new QName(extensionElement.tagName());
    }
}

package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public abstract class ExtensionElement {

    static final String TAG_NAME_IS_MANDATORY = "TagName is mandatory.";

    public abstract Namespace namespace();

    public abstract String tagName();

    protected abstract Collection<Attribute> attributes();

    protected abstract JAXBElement toJAXBElement();

    QName qualifiedName() {
        Namespace namespace = namespace();
        if (namespace != null) {
            return new QName(namespace.uri(), tagName(), namespace.prefix());
        }
        return new QName(tagName());
    }

    interface Builder<E extends ExtensionElement, CURRENT_BUILDER extends Builder<E, CURRENT_BUILDER>> {

        CURRENT_BUILDER withNamespace(Namespace namespace);

        CURRENT_BUILDER addAttribute(Attribute attribute);

        CURRENT_BUILDER addAttributes(Iterable<Attribute> attributes);

        E build();
    }

}

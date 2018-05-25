package fr.vidal.oss.jaxb.atom.core;

import fr.vidal.oss.jaxb.atom.extensions.ExtensionElementConverter;

import java.util.Collection;

public interface ExtensionElement {
    Namespace namespace();

    String tagName();

    Collection<Attribute> attributes();

    ExtensionElementConverter converter();
}

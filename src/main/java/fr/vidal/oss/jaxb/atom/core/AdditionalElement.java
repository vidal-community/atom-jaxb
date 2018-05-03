package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;

public interface AdditionalElement {
    Namespace namespace();

    String tagName();

    Collection<Attribute> attributes();

    String value();
}

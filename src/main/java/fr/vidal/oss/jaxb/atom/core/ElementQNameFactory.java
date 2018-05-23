package fr.vidal.oss.jaxb.atom.core;

import javax.xml.namespace.QName;

public interface ElementQNameFactory {
    QName qualifiedName(ExtensionElement simpleElement);
}

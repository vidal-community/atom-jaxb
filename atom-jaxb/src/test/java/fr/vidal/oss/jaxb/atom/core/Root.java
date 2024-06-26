package fr.vidal.oss.jaxb.atom.core;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class Root {

    @XmlAnyElement
    ExtensionElement element;

    Root() {
    }

    Root(ExtensionElement element) {
        this.element = element;
    }
}

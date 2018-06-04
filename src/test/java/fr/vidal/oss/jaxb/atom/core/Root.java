package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

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

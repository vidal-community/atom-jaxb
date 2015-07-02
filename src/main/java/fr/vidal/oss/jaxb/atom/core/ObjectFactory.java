package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public Feed createFeed() {
        return Feed.builder().build();
    }

}

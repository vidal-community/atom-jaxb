package fr.vidal.oss.jaxb.atom.core;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

public class AtomJaxb {

    public static JAXBContext newContext() throws JAXBException {
        return JAXBContext.newInstance(Feed.class.getPackage().getName(), AtomJaxb.class.getClassLoader());
    }

    private AtomJaxb() {
    }
}

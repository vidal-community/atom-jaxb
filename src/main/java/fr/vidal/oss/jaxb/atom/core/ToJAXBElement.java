package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.JAXBElement;

public interface ToJAXBElement<T, E extends AdditionalElement> {
    JAXBElement<T> convert(E element);
}

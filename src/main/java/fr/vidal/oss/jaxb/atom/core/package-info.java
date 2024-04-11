@XmlSchema(namespace = "http://www.w3.org/2005/Atom", elementFormDefault = QUALIFIED)
@XmlAccessorType(XmlAccessType.NONE) // explicit FTW ;)
@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value = DateAdapter.class, type = Date.class),
    @XmlJavaTypeAdapter(value = ExtensionElementAdapter.class, type = ExtensionElement.class),
    @XmlJavaTypeAdapter(value = ExtensionElementAdapter.class, type = SimpleElement.class),
    @XmlJavaTypeAdapter(value = ExtensionElementAdapter.class, type = StructuredElement.class)
})
package fr.vidal.oss.jaxb.atom.core;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.Date;

import static jakarta.xml.bind.annotation.XmlNsForm.QUALIFIED;

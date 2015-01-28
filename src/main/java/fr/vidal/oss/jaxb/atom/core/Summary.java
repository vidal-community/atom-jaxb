package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Summary {

    private String value;
    private String type;

    Summary() {}

    public Summary(String value, String type) {
        this.value = value;
        this.type = type;
    }

    @XmlValue
    public String getValue() {
        return value;
    }


    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

}

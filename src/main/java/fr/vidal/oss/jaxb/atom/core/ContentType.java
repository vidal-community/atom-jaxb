package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlValue;

public class ContentType {

    private String type;

    ContentType() {}

    public ContentType(String type) {
        this.type = type;
    }

    @XmlValue
    public String getType() {
        return type;
    }

}

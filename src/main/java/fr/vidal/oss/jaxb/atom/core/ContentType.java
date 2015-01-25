package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlValue;

public class ContentType {

    private String type;

    @XmlValue
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

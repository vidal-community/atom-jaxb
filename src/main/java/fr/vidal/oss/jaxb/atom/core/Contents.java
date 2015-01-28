package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Contents {

    public final static Contents EMPTY = new Contents();

    private ContentType type;
    private String contents;

    Contents() {}

    private Contents(ContentType type, String contents) {
        this.type = type;
        this.contents = contents;
    }

    public static Contents contents(ContentType type, String contents) {
        return new Contents(type, contents);
    }

    public static Contents contents(String contents) {
        return new Contents(null, contents);
    }

    @XmlAttribute(name = "type")
    public ContentType getType() {
        return type;
    }


    @XmlValue
    public String getContents() {
        return contents;
    }


}

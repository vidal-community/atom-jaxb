package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

public class Summary {

    @XmlValue
    private final String value;
    @XmlAttribute(name = "type")
    private final String type;

    @SuppressWarnings("unused")
    private Summary() {
        this(null, null);
    }

    public Summary(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Summary other = (Summary) obj;
        return Objects.equals(this.value, other.value)
            && Objects.equals(this.type, other.type);
    }

    @Override
    public String toString() {
        return "Summary{" +
            "value='" + value + '\'' +
            ", type='" + type + '\'' +
            '}';
    }
}

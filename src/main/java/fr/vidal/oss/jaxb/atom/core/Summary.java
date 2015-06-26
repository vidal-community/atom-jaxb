package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

public class Summary {

    @XmlValue
    private final String value;
    @XmlAttribute(name = "type")
    private final String type;

    @SuppressWarnings("unused") //jaxb
    private Summary() {
        this(null, null);
    }

    private Summary(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Summary)) return false;

        Summary summary = (Summary) o;

        if (type != null ? !type.equals(summary.type) : summary.type != null) return false;
        if (value != null ? !value.equals(summary.value) : summary.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Summary{" +
            "value='" + value + '\'' +
            ", type='" + type + '\'' +
            '}';
    }

    public static class Builder {

        private String value;
        private String type;

        private Builder() {
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Summary build() {
            return new Summary(value, type);
        }
    }
}

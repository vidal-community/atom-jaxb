package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class Icon {

    @XmlElement
    private String uri;

    public Icon() {
        this(null);
    }

    public Icon(String uri) {
        this.uri = uri;
    }

    public static Builder builder(String uri) {
        return new Builder(uri);
    }

    public String getUri() {
        return uri;
    }



    @Override
    public String toString() {
        return "Icon{" +
            "uri='" + uri + '\'' +
            '}';
    }

    public static class Builder {
        private String uri;

        public Builder(String uri) {
            this.uri = uri;
        }

        public Builder withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Icon build() {
            return new Icon(uri);
        }
    }

}

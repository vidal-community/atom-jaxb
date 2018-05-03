package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;

public class Logo {

    @XmlElement
    private String uri;

    public Logo() {
        this(null);
    }

    public Logo(String uri) {
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
        return "Logo{" +
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

        public Logo build() {
            return new Logo(uri);
        }
    }

}

package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class Generator {

    @XmlElement
    private String uri;
    @XmlElement
    private String version;

    public Generator() {
        this(null, null);
    }

    public Generator(String uri, String version) {
        this.uri = uri;
        this.version = version;
    }

    public static Builder builder(String uri) {
        return new Builder(uri);
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Generator generator = (Generator) o;
        return Objects.equals(uri, generator.uri) &&
            Objects.equals(version, generator.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, version);
    }

    @Override
    public String toString() {
        return "Generator{" +
            "uri='" + uri + '\'' +
            ", version='" + version + '\'' +
            '}';
    }

    public static class Builder {
        private String uri;
        private String version;

        public Builder(String uri) {
            this.uri = uri;
        }

        public Builder withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder withVersion(String version) {
            this.version = version;
            return this;
        }

        public Generator build() {
            return new Generator(uri, version);
        }
    }

}

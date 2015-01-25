package fr.vidal.oss.jaxb.atom.core;

import java.util.Objects;

public class Namespace {

    private String uri;
    private String prefix;

    public Namespace(String uri, String prefix) {
        this.uri = uri;
        this.prefix = prefix;
    }

    public String uri() {
        return uri;
    }

    public String prefix() {
        return prefix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, prefix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Namespace other = (Namespace) obj;
        return Objects.equals(this.uri, other.uri) && Objects.equals(this.prefix, other.prefix);
    }
}

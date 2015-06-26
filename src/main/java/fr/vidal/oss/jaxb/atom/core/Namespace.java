package fr.vidal.oss.jaxb.atom.core;

import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.lang.String.format;

public class Namespace {

    private final String uri;
    private final String prefix;

    private Namespace(String uri, String prefix) {
        this.uri = uri;
        this.prefix = prefix;
    }

    public static Builder builder(String uri) {
        return new Builder(uri);
    }

    public String uri() {
        return uri;
    }

    public String prefix() {
        return prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Namespace)) return false;

        Namespace namespace = (Namespace) o;

        if (prefix != null ? !prefix.equals(namespace.prefix) : namespace.prefix != null) return false;
        if (uri != null ? !uri.equals(namespace.uri) : namespace.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return format("%s (%s)", prefix, uri);
    }


    public static class Builder {

        private final String uri;
        private String prefix;

        private Builder(String uri) {
            this.uri = uri;
        }

        public Builder withPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Namespace build() {
            checkState(uri != null, "uri is mandatory");
            return new Namespace(uri, prefix);
        }
    }
}

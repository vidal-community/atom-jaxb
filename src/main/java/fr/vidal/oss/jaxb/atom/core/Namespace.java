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

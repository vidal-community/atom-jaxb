package fr.vidal.oss.jaxb.atom.core;

import static java.lang.String.format;

public class Preconditions {

    public static void checkState(boolean condition, String msg, Object... args) {
        if (!condition) {
            throw new IllegalStateException(format(msg, args));
        }
    }
}

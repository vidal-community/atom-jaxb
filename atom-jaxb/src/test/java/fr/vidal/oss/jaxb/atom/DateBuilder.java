package fr.vidal.oss.jaxb.atom;

import java.text.SimpleDateFormat;

public final class DateBuilder {
    private DateBuilder() {}

    static SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }
}

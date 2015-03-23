package fr.vidal.oss.jaxb.atom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateBuilder {
    private DateBuilder() {}

    static Date isoDate(final String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
    }
}

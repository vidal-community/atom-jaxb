package fr.vidal.oss.jaxb.atom.core;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public String marshal(Date date) throws Exception {
        return LocalDateTime.ofInstant(date.toInstant(), UTC).format(FORMATTER);
    }

    @Override
    public Date unmarshal(String xmlDate) throws Exception {
        return Date.from(OffsetDateTime.parse(xmlDate).toInstant());
    }
}
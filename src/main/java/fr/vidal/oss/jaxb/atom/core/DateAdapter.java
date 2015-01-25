package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public String marshal(Date date) throws Exception {
        return DATE_FORMAT.format(date);
    }

    @Override
    public Date unmarshal(String xmlDate) throws Exception {
        return DATE_FORMAT.parse(xmlDate);
    }

}
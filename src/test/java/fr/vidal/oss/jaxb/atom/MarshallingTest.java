package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.*;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static fr.vidal.oss.jaxb.atom.core.DateAdapter.DATE_FORMAT;
import static fr.vidal.oss.jaxb.atom.core.LinkRel.*;
import static java.util.TimeZone.getTimeZone;
import static org.assertj.core.api.Assertions.assertThat;

public class MarshallingTest {

    private Marshaller marshaller;

    @Before
    public void prepare() throws JAXBException {
        TimeZone.setDefault(getTimeZone("Europe/Paris"));
        marshaller = JAXBContext.newInstance(Feed.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    }

    @Test
    public void marshalls_standard_atom_feed() throws JAXBException, IOException {
        Feed feed = new Feed();
        feed.setId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6");
        feed.setTitle("My standard Atom 1.0 feed");
        feed.setSubtitle("Or is it?");
        feed.setUpdateDate(new Date(510278400000L));
        Author author = new Author("VIDAL", null);
        feed.setAuthor(author);
        feed.addLink(new Link(self, null, "http://example.org/", null));

        Entry entry = new Entry();
        entry.addLink(new Link(null, null, "http://example.org/2003/12/13/atom03", null));
        entry.setTitle("Atom is not what you think");
        entry.setId("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a");
        entry.setUpdateDate(new Date(512697600000L));
        entry.setSummary(new Summary("April's fool!", null));

        feed.addEntry(entry);

        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(feed, writer);
            assertThat(writer.toString())
                .isXmlEqualTo(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                        "    <title>My standard Atom 1.0 feed</title>\n" +
                        "    <subtitle>Or is it?</subtitle>\n" +
                        "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                        "    <id>urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6</id>\n" +
                        "    <author>\n" +
                        "        <name>VIDAL</name>\n" +
                        "    </author>\n" +
                        "    <updated>1986-03-04T01:00:00Z</updated>\n" +
                        "    <entry>\n" +
                        "        <title>Atom is not what you think</title>\n" +
                        "        <link href=\"http://example.org/2003/12/13/atom03\"/>\n" +
                        "        <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>\n" +
                        "        <updated>1986-04-01T02:00:00Z</updated>\n" +
                        "        <summary>April's fool!</summary>\n" +
                        "        <content/>\n" +
                        "    </entry>\n" +
                        "</feed>");
        }
    }

    @Test
    public void marshalls_feed_with_vendor_namespace_elements() throws JAXBException, IOException {
        Feed feed = new Feed();
        feed.setTitle("Search Products - Query :sintrom");
        feed.addLink(new Link(self, "application/atom+xml", "/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25", null));
        feed.setUpdateDate(new Date(1329350400000L));
        feed.addAdditionalElement(new SimpleElement(
            new Namespace("http://purl.org/dc/elements/1.1/", "dc"),
            "date",
            DATE_FORMAT.format(new Date(1329350400000L)),
            new ArrayList<Attribute>()
        ));
        feed.addAdditionalElement(new SimpleElement(
            new Namespace("http://a9.com/-/spec/opensearch/1.1/", "opensearch"),
            "itemsPerPage",
            "25",
            new ArrayList<Attribute>()
        ));
        feed.addAdditionalElement(new SimpleElement(
            new Namespace("http://a9.com/-/spec/opensearch/1.1/", "opensearch"),
            "totalResults",
            "2",
            new ArrayList<Attribute>()
        ));
        feed.addAdditionalElement(new SimpleElement(
            new Namespace("http://a9.com/-/spec/opensearch/1.1/", "opensearch"),
            "startIndex",
            "1",
            new ArrayList<Attribute>()
        ));

        Entry firstEntry = new Entry();
        firstEntry.setTitle("SINTROM 4 mg cp quadriséc");
        firstEntry
            .addLink(new Link(alternate, "application/atom+xml", "/rest/api/product/15070", null))
            .addLink(new Link(related, "application/atom+xml", "/rest/api/product/15070/packages", "PACKAGES"))
            .addLink(new Link(related, "application/atom+xml", "/rest/api/product/15070/documents", "DOCUMENTS"))
            .addLink(new Link(related, "application/atom+xml", "/rest/api/product/15070/documents/opt", "OPT_DOCUMENT"));
        firstEntry.setCategory(new Category("PRODUCT"));
        firstEntry.setAuthor(new Author("VIDAL", null));
        firstEntry.setId("vidal://product/15070");
        firstEntry.setUpdateDate(new Date(1329350400000L));
        firstEntry.setSummary(new Summary("SINTROM 4 mg cp quadriséc", "text"));
        firstEntry.addAdditionalElement(new SimpleElement(
            new Namespace("http://api.vidal.net/-/spec/vidal-api/1.0/", "vidal"),
            "id",
            "15070",
            new ArrayList<Attribute>()
        ));
        feed.addEntry(firstEntry);

        Entry secondEntry = new Entry();
        secondEntry.setTitle("SNAKE OIL 1 mg");
        secondEntry
            .addLink(new Link(alternate, "application/atom+xml", "/rest/api/product/42", null))
            .addLink(new Link(related, "application/atom+xml", "/rest/api/product/42/packages", "PACKAGES"))
            .addLink(new Link(related, "application/atom+xml", "/rest/api/product/42/documents", "DOCUMENTS"))
            .addLink(new Link(related, "application/atom+xml", "/rest/api/product/42/documents/opt", "OPT_DOCUMENT"));
        secondEntry.setCategory(new Category("PRODUCT"));
        secondEntry.setAuthor(new Author("VIDAL", null));
        secondEntry.setId("vidal://product/42");
        secondEntry.setUpdateDate(new Date(1329350400000L));
        secondEntry.setSummary(new Summary("SNAKE OIL 1 mg", "text"));
        secondEntry.addAdditionalElement(new SimpleElement(
            new Namespace("http://api.vidal.net/-/spec/vidal-api/1.0/", "vidal"),
            "id",
            "42",
            new ArrayList<Attribute>()
        ));
        feed.addEntry(secondEntry);

        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(feed, writer);
            assertThat(writer.toString())
                .isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                    "    <title>Search Products - Query :sintrom</title>\n" +
                    "    <link\n" +
                    "        href=\"/rest/api/products?q=sintrom&amp;amp;start-page=1&amp;amp;page-size=25\"\n" +
                    "        rel=\"self\" type=\"application/atom+xml\"/>\n" +
                    "    <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "    <dc:date xmlns:dc=\"http://purl.org/dc/elements/1.1/\">2012-02-16T01:00:00Z</dc:date>\n" +
                    "    <opensearch:itemsPerPage xmlns:opensearch=\"http://a9.com/-/spec/opensearch/1.1/\">25</opensearch:itemsPerPage>\n" +
                    "    <opensearch:totalResults xmlns:opensearch=\"http://a9.com/-/spec/opensearch/1.1/\">2</opensearch:totalResults>\n" +
                    "    <opensearch:startIndex xmlns:opensearch=\"http://a9.com/-/spec/opensearch/1.1/\">1</opensearch:startIndex>\n" +
                    "    <entry>\n" +
                    "        <title>SINTROM 4 mg cp quadriséc</title>\n" +
                    "        <link href=\"/rest/api/product/15070\" rel=\"alternate\" type=\"application/atom+xml\"/>\n" +
                    "        <link href=\"/rest/api/product/15070/packages\" rel=\"related\"\n" +
                    "            title=\"PACKAGES\" type=\"application/atom+xml\"/>\n" +
                    "        <link href=\"/rest/api/product/15070/documents\" rel=\"related\"\n" +
                    "            title=\"DOCUMENTS\" type=\"application/atom+xml\"/>\n" +
                    "        <link href=\"/rest/api/product/15070/documents/opt\" rel=\"related\"\n" +
                    "            title=\"OPT_DOCUMENT\" type=\"application/atom+xml\"/>\n" +
                    "        <category term=\"PRODUCT\"/>\n" +
                    "        <author>\n" +
                    "            <name>VIDAL</name>\n" +
                    "        </author>\n" +
                    "        <id>vidal://product/15070</id>\n" +
                    "        <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "        <summary type=\"text\">SINTROM 4 mg cp quadriséc</summary>\n" +
                    "        <content/>\n" +
                    "        <vidal:id xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">15070</vidal:id>\n" +
                    "    </entry>\n" +
                    "    <entry>\n" +
                    "        <title>SNAKE OIL 1 mg</title>\n" +
                    "        <link href=\"/rest/api/product/42\" rel=\"alternate\" type=\"application/atom+xml\"/>\n" +
                    "        <link href=\"/rest/api/product/42/packages\" rel=\"related\"\n" +
                    "            title=\"PACKAGES\" type=\"application/atom+xml\"/>\n" +
                    "        <link href=\"/rest/api/product/42/documents\" rel=\"related\"\n" +
                    "            title=\"DOCUMENTS\" type=\"application/atom+xml\"/>\n" +
                    "        <link href=\"/rest/api/product/42/documents/opt\" rel=\"related\"\n" +
                    "            title=\"OPT_DOCUMENT\" type=\"application/atom+xml\"/>\n" +
                    "        <category term=\"PRODUCT\"/>\n" +
                    "        <author>\n" +
                    "            <name>VIDAL</name>\n" +
                    "        </author>\n" +
                    "        <id>vidal://product/42</id>\n" +
                    "        <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "        <summary type=\"text\">SNAKE OIL 1 mg</summary>\n" +
                    "        <content/>\n" +
                    "        <vidal:id xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">42</vidal:id>\n" +
                    "    </entry>\n" +
                    "</feed>");
        }
    }
}

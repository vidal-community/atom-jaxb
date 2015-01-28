package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.*;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.TimeZone;

import static fr.vidal.oss.jaxb.atom.core.Author.author;
import static fr.vidal.oss.jaxb.atom.core.DateAdapter.DATE_FORMAT;
import static fr.vidal.oss.jaxb.atom.core.Feed.Builder;
import static fr.vidal.oss.jaxb.atom.core.LinkRel.*;
import static fr.vidal.oss.jaxb.atom.core.Namespace.namespace;
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
        Builder feedBuilder = new Builder();
        feedBuilder.withId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6");
        feedBuilder.withTitle("My standard Atom 1.0 feed");
        feedBuilder.withSubtitle("Or is it?");
        feedBuilder.withUpdateDate(new Date(510278400000L));
        feedBuilder.withAuthor(author("VIDAL"));
        feedBuilder.addLink(new Link.Builder().withRel(self).withHref("http://example.org/").build());

        Entry.Builder builder = new Entry.Builder();
        builder.addLink(new Link.Builder().withHref("http://example.org/2003/12/13/atom03").build());
        builder.withTitle("Atom is not what you think");
        builder.withId("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a");
        builder.withUpdateDate(new Date(512697600000L));
        builder.withSummary(new Summary("April's fool!", null));

        feedBuilder.addEntry(builder.build());

        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(feedBuilder.build(), writer);
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
        Builder builder = new Builder();
        builder.withTitle("Search Products - Query :sintrom")
            .addLink(new Link.Builder().withRel(self).withType("application/atom+xml").withHref("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").build())
            .withUpdateDate(new Date(1329350400000L))
            .addSimpleElement(
                new SimpleElement.Builder()
                    .withNamespace(namespace("http://purl.org/dc/elements/1.1/", "dc"))
                    .withTagName("date")
                    .withValue(DATE_FORMAT.format(new Date(1329350400000L)))
                    .build()
            )
            .addSimpleElement(new SimpleElement.Builder()
                    .withNamespace(namespace("http://a9.com/-/spec/opensearch/1.1/", "opensearch"))
                    .withTagName("itemsPerPage")
                    .withValue(String.valueOf(25))
                    .build()
            )
            .addSimpleElement(new SimpleElement.Builder()
                    .withNamespace(namespace("http://a9.com/-/spec/opensearch/1.1/", "opensearch"))
                    .withTagName("totalResults")
                    .withValue(String.valueOf(2))
                    .build()
            )
            .addSimpleElement(new SimpleElement.Builder()
                    .withNamespace(namespace("http://a9.com/-/spec/opensearch/1.1/", "opensearch"))
                    .withTagName("startIndex")
                    .withValue(String.valueOf(1))
                    .build()
            )
            .addEntry(
                new Entry.Builder()
                    .withTitle("SINTROM 4 mg cp quadriséc")
                    .addLink(new Link.Builder().withRel(alternate).withType("application/atom+xml").withHref("/rest/api/product/15070").build())
                    .addLink(new Link.Builder().withRel(related).withType("application/atom+xml").withHref("/rest/api/product/15070/packages").withTitle("PACKAGES").build())
                    .addLink(new Link.Builder().withRel(related).withType("application/atom+xml").withHref("/rest/api/product/15070/documents").withTitle("DOCUMENTS").build())
                    .addLink(new Link.Builder().withRel(related).withType("application/atom+xml").withHref("/rest/api/product/15070/documents/opt").withTitle("OPT_DOCUMENT").build())
                    .withCategory(new Category("PRODUCT"))
                    .withAuthor(author("VIDAL"))
                    .withId("vidal://product/15070")
                    .withUpdateDate(new Date(1329350400000L))
                    .withSummary(new Summary("SINTROM 4 mg cp quadriséc", "text"))
                    .addSimpleElement(
                        new SimpleElement.Builder()
                            .withNamespace(namespace("http://api.vidal.net/-/spec/vidal-api/1.0/", "vidal"))
                            .withTagName("id")
                            .withValue(String.valueOf(15070))
                            .build()
                    )
                    .build()
            ).addEntry(
                new Entry.Builder()
                    .withTitle("SNAKE OIL 1 mg")
                    .addLink(new Link.Builder().withRel(alternate).withType("application/atom+xml").withHref("/rest/api/product/42").build())
                    .addLink(new Link.Builder().withRel(related).withType("application/atom+xml").withHref("/rest/api/product/42/packages").withTitle("PACKAGES").build())
                    .addLink(new Link.Builder().withRel(related).withType("application/atom+xml").withHref("/rest/api/product/42/documents").withTitle("DOCUMENTS").build())
                    .addLink(new Link.Builder().withRel(related).withType("application/atom+xml").withHref("/rest/api/product/42/documents/opt").withTitle("OPT_DOCUMENT").build())
                    .withCategory(new Category("PRODUCT"))
                    .withAuthor(author("VIDAL"))
                    .withId("vidal://product/42")
                    .withUpdateDate(new Date(1329350400000L))
                    .withSummary(new Summary("SNAKE OIL 1 mg", "text"))
                    .addSimpleElement(new SimpleElement.Builder()
                            .withNamespace(namespace("http://api.vidal.net/-/spec/vidal-api/1.0/", "vidal"))
                            .withTagName("id")
                            .withValue(String.valueOf(42))
                            .build()
                    ).build()
        );

        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(builder.build(), writer);
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

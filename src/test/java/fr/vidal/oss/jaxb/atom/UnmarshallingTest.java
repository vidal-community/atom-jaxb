package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.Category;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import fr.vidal.oss.jaxb.atom.core.LinkRel;
import fr.vidal.oss.jaxb.atom.core.SimpleElement;
import fr.vidal.oss.jaxb.atom.core.Summary;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import static fr.vidal.oss.jaxb.atom.Assertions.assertThat;
import static fr.vidal.oss.jaxb.atom.core.Attribute.attribute;
import static fr.vidal.oss.jaxb.atom.core.Author.author;
import static fr.vidal.oss.jaxb.atom.core.Contents.contents;
import static fr.vidal.oss.jaxb.atom.core.DateAdapter.DATE_FORMAT;
import static fr.vidal.oss.jaxb.atom.core.Namespace.namespace;
import static java.util.TimeZone.getTimeZone;

public class UnmarshallingTest {

    private Unmarshaller unmarshaller;

    @Before
    public void prepare() throws JAXBException {
        TimeZone.setDefault(getTimeZone("Europe/Paris"));
        unmarshaller = JAXBContext.newInstance(Feed.class).createUnmarshaller();
    }

    @Test
    public void unmarshall_standard_atom_feed() throws Exception {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
            "        <content>Entry content</content>\n" +
            "    </entry>\n" +
            "</feed>";

        Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

        assertThat(result)
            .hasTitle("My standard Atom 1.0 feed")
            .hasSubtitle("Or is it?")
            .hasLinks(
                new Link.Builder()
                    .withHref("http://example.org/")
                    .withRel(LinkRel.self)
                    .build())
            .hasId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6")
            .hasAuthor(author("VIDAL"))
            .hasUpdateDate(new Date(510278400000L))
            .hasEntries(entry("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a"));

        assertThat(first(result.getEntries()))
            .hasTitle("Atom is not what you think")
            .hasLinks(
                new Link.Builder()
                    .withHref("http://example.org/2003/12/13/atom03")
                    .build())
            .hasUpdateDate(new Date(512697600000L))
            .hasSummary(new Summary("April's fool!", null))
            .hasContents(contents("Entry content"));
    }

    @Test
    public void unmarshall_feed_with_vendor_specific_element() throws Exception {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <link\n" +
            "        href=\"/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25\"\n" +
            "        rel=\"self\" type=\"application/atom+xml\"/>\n" +
            "    <dc:date df:format=\"yyyy-MM-dd'T'HH:mm:ss'Z'\"\n" +
            "             xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:df=\"http://date-formats.com\">2012-02-16T01:00:00Z</dc:date>\n" +
            "    <opensearch:itemsPerPage xmlns:opensearch=\"http://a9.com/-/spec/opensearch/1.1/\">25</opensearch:itemsPerPage>\n" +
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
            "        <summary type=\"text\">SINTROM 4 mg cp quadriséc</summary>\n" +
            "        <vidal:id xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">15070</vidal:id>\n" +
            "    </entry>\n" +
            "    <entry>\n" +
            "        <id>vidal://product/42</id>\n" +
            "    </entry>\n" +
            "</feed>";

        Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

        assertThat(result)
            .hasLinks(
                new Link.Builder()
                    .withHref("/rest/api/products?q=sintrom&start-page=1&page-size=25")
                    .withRel(LinkRel.self)
                    .withType("application/atom+xml")
                    .build())
            .hasAdditionalElements(
                new SimpleElement.Builder()
                    .withTagName("date")
                    .withNamespace(namespace("http://purl.org/dc/elements/1.1/", "dc"))
                    .addAttribute(attribute("format", "yyyy-MM-dd'T'HH:mm:ss'Z'", namespace("http://date-formats.com", "df")))
                    .addAttribute(attribute("xmlns", "http://www.w3.org/2005/Atom", namespace("http://www.w3.org/2000/xmlns/", null)))
                    .addAttribute(attribute("dc", "http://purl.org/dc/elements/1.1/", namespace("http://www.w3.org/2000/xmlns/", "xmlns")))
                    .addAttribute(attribute("df", "http://date-formats.com", namespace("http://www.w3.org/2000/xmlns/", "xmlns")))
                    .withValue(DATE_FORMAT.format(new Date(1329350400000L)))
                    .build(),
                new SimpleElement.Builder()
                    .withTagName("itemsPerPage")
                    .withNamespace(namespace("http://a9.com/-/spec/opensearch/1.1/", "opensearch"))
                    .addAttribute(attribute("xmlns", "http://www.w3.org/2005/Atom", namespace("http://www.w3.org/2000/xmlns/", null)))
                    .addAttribute(attribute("opensearch", "http://a9.com/-/spec/opensearch/1.1/", namespace("http://www.w3.org/2000/xmlns/", "xmlns")))
                    .withValue("25")
                    .build())
            .hasEntries(
                entry("vidal://product/15070"),
                entry("vidal://product/42")
            );

        assertThat(first(result.getEntries()))
            .hasTitle("SINTROM 4 mg cp quadriséc")
            .hasLinks(
                new Link.Builder()
                    .withHref("/rest/api/product/15070")
                    .withRel(LinkRel.alternate)
                    .withType("application/atom+xml")
                    .build(),
                new Link.Builder()
                    .withHref("/rest/api/product/15070/packages")
                    .withRel(LinkRel.related)
                    .withTitle("PACKAGES")
                    .withType("application/atom+xml")
                    .build(),
                new Link.Builder()
                    .withHref("/rest/api/product/15070/documents")
                    .withRel(LinkRel.related)
                    .withTitle("DOCUMENTS")
                    .withType("application/atom+xml")
                    .build(),
                new Link.Builder()
                    .withHref("/rest/api/product/15070/documents/opt")
                    .withRel(LinkRel.related)
                    .withTitle("OPT_DOCUMENT")
                    .withType("application/atom+xml")
                    .build())
            .hasCategory(new Category("PRODUCT"))
            .hasAuthor(author("VIDAL"))
            .hasSummary(new Summary("SINTROM 4 mg cp quadriséc", "text"))
            .hasAdditionalElements(
                new SimpleElement.Builder()
                    .withTagName("id")
                    .withNamespace(namespace("http://api.vidal.net/-/spec/vidal-api/1.0/", "vidal"))
                    .addAttribute(attribute("xmlns", "http://www.w3.org/2005/Atom", namespace("http://www.w3.org/2000/xmlns/", null)))
                    .addAttribute(attribute("vidal", "http://api.vidal.net/-/spec/vidal-api/1.0/", namespace("http://www.w3.org/2000/xmlns/", "xmlns")))
                    .withValue("15070")
                    .build()
            );
    }

    private static Entry first(Collection<Entry> entries) {
        return entries.iterator().next();
    }

    private static Entry entry(String id) {
        return new Entry.Builder().withId(id).build();
    }
}

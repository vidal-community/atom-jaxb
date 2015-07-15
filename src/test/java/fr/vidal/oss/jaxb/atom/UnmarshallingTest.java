package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.AtomJaxb;
import fr.vidal.oss.jaxb.atom.core.Attribute;
import fr.vidal.oss.jaxb.atom.core.Author;
import fr.vidal.oss.jaxb.atom.core.Category;
import fr.vidal.oss.jaxb.atom.core.Contents;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import fr.vidal.oss.jaxb.atom.core.LinkRel;
import fr.vidal.oss.jaxb.atom.core.Namespace;
import fr.vidal.oss.jaxb.atom.core.SimpleElement;
import fr.vidal.oss.jaxb.atom.core.Summary;

import static fr.vidal.oss.jaxb.atom.Assertions.assertThat;
import static fr.vidal.oss.jaxb.atom.core.DateAdapter.DATE_FORMAT;
import static java.util.TimeZone.getTimeZone;

import java.io.StringReader;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

public class UnmarshallingTest {

    private Unmarshaller unmarshaller;

    @Before
    public void prepare() throws JAXBException {
        TimeZone.setDefault(getTimeZone("Europe/Paris"));
        unmarshaller = AtomJaxb.newContext().createUnmarshaller();
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
            "        <published>1977-02-05T01:00:00Z</published>\n" +
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
                Link.builder("http://example.org/")
                    .withRel(LinkRel.self)
                    .build())
            .hasId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6")
            .hasAuthor(Author.builder("VIDAL").build())
            .hasUpdateDate(new Date(510278400000L))
            .hasEntries(entry("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a"));

        assertThat(first(result.getEntries()))
            .hasTitle("Atom is not what you think")
            .hasLinks(
                Link.builder("http://example.org/2003/12/13/atom03")
                    .build())
            .hasPublishedDate(DateBuilder.isoDate("1977-02-05T01:00:00"))
            .hasUpdateDate(new Date(512697600000L))
            .hasSummary(Summary.builder().withValue("April's fool!").withType(null).build())
            .hasContents(Contents.builder().withContents("Entry content").build());
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
                Link.builder("/rest/api/products?q=sintrom&start-page=1&page-size=25")
                    .withRel(LinkRel.self)
                    .withType("application/atom+xml")
                    .build())
            .hasAdditionalElements(
                SimpleElement.builder("date", DATE_FORMAT.format(new Date(1329350400000L)))
                    .withNamespace(Namespace.builder("http://purl.org/dc/elements/1.1/").withPrefix("dc").build())
                    .addAttribute(Attribute.builder("format", "yyyy-MM-dd'T'HH:mm:ss'Z'").withNamespace(Namespace.builder("http://date-formats.com").withPrefix("df").build()).build())
                    .addAttribute(Attribute.builder("xmlns", "http://www.w3.org/2005/Atom").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").build()).build())
                    .addAttribute(Attribute.builder("dc", "http://purl.org/dc/elements/1.1/").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build()).build())
                    .addAttribute(Attribute.builder("df", "http://date-formats.com").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build()).build())
                    .build(),
                SimpleElement.builder("itemsPerPage", "25")
                    .withNamespace(Namespace.builder("http://a9.com/-/spec/opensearch/1.1/").withPrefix("opensearch").build())
                    .addAttribute(Attribute.builder("xmlns", "http://www.w3.org/2005/Atom").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").build()).build())
                    .addAttribute(Attribute.builder("opensearch", "http://a9.com/-/spec/opensearch/1.1/").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build()).build())
                    .build())
            .hasEntries(
                entry("vidal://product/15070"),
                entry("vidal://product/42")
            );

        assertThat(first(result.getEntries()))
            .hasTitle("SINTROM 4 mg cp quadriséc")
            .hasLinks(
                Link.builder("/rest/api/product/15070")
                    .withRel(LinkRel.alternate)
                    .withType("application/atom+xml")
                    .build(),
                Link.builder("/rest/api/product/15070/packages")
                    .withRel(LinkRel.related)
                    .withTitle("PACKAGES")
                    .withType("application/atom+xml")
                    .build(),
                Link.builder("/rest/api/product/15070/documents")
                    .withRel(LinkRel.related)
                    .withTitle("DOCUMENTS")
                    .withType("application/atom+xml")
                    .build(),
                Link.builder("/rest/api/product/15070/documents/opt")
                    .withRel(LinkRel.related)
                    .withTitle("OPT_DOCUMENT")
                    .withType("application/atom+xml")
                    .build())
            .hasCategories(Category.builder("PRODUCT").build())
            .hasAuthor(Author.builder("VIDAL").build())
            .hasSummary(Summary.builder().withValue("SINTROM 4 mg cp quadriséc").withType("text").build())
            .hasAdditionalElements(
                SimpleElement.builder("id", "15070")
                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                    .addAttribute(Attribute.builder("xmlns", "http://www.w3.org/2005/Atom").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").build()).build())
                    .addAttribute(Attribute.builder("vidal", "http://api.vidal.net/-/spec/vidal-api/1.0/").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build()).build())
                    .build()
            );
    }

    @Test
    public void unmarshalls_custom_entry_attributes() throws JAXBException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <title>Search Products - Query :sintrom</title>\n" +
            "    <link\n" +
            "        href=\"/rest/api/products?q=sintrom&amp;amp;start-page=1&amp;amp;page-size=25\"\n" +
            "        rel=\"self\" type=\"application/atom+xml\"/>\n" +
            "    <id>Heidi</id>\n" +
            "    <updated>2012-02-16T01:00:00Z</updated>\n" +
            "    <entry vidal:type=\"PRODUCT,PACK\" xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">\n" +
            "        <title>SINTROM 4 mg cp quadriséc</title>\n" +
            "        <link href=\"/rest/api/product/15070\" rel=\"alternate\" type=\"application/atom+xml\"/>\n" +
            "        <category term=\"PRODUCT\"/>\n" +
            "        <category term=\"PACK\"/>\n" +
            "        <author>\n" +
            "            <name>VIDAL</name>\n" +
            "        </author>\n" +
            "        <id>vidal://product/15070</id>\n" +
            "        <updated>2012-02-16T01:00:00Z</updated>\n" +
            "        <summary type=\"text\">SINTROM 4 mg cp quadriséc</summary>\n" +
            "        <content/>\n" +
            "        <vidal:id>15070</vidal:id>\n" +
            "    </entry>\n" +
            "</feed>";

        Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

        org.assertj.core.api.Assertions.assertThat(result.getEntries()).hasSize(1);
        Entry entry = result.getEntries().iterator().next();

        org.assertj.core.api.Assertions.assertThat(entry.getAdditionalAttributes())
            .hasSize(1)
            .containsEntry(
                new QName("http://api.vidal.net/-/spec/vidal-api/1.0/", "type", "vidal"),
                "PRODUCT,PACK"
            );
    }

    private static Entry first(Collection<Entry> entries) {
        return entries.iterator().next();
    }

    private static Entry entry(String id) {
        return Entry.builder()
            .withId(id)
            .withTitle("title - " + id)
            .withUpdateDate(new Date())
            .addLink(Link.builder("self").build())
            .build();
    }

}

package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.AtomJaxb;
import fr.vidal.oss.jaxb.atom.core.Attribute;
import fr.vidal.oss.jaxb.atom.core.Author;
import fr.vidal.oss.jaxb.atom.core.Category;
import fr.vidal.oss.jaxb.atom.core.Contents;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.ExtensionElements;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import fr.vidal.oss.jaxb.atom.core.LinkRel;
import fr.vidal.oss.jaxb.atom.core.Namespace;
import fr.vidal.oss.jaxb.atom.core.Summary;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import static fr.vidal.oss.jaxb.atom.Assertions.assertThat;
import static fr.vidal.oss.jaxb.atom.DateBuilder.dateFormat;
import static java.util.TimeZone.getTimeZone;

import java.io.StringReader;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

public class UnmarshallingTest {

    private static final Namespace EXTENSION_NAMESPACE = Namespace.builder("http://foo.bar.org/extension/").withPrefix("extension").build();
    private static final Attribute XMLNS_ATTRIBUTE = Attribute.builder("xmlns", "http://www.w3.org/2005/Atom").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").build()).build();
    private static final Namespace XMLNS_NAMESPACE = Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build();
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
            .hasUpdateDate(Date.from(Instant.parse("1986-03-04T01:00:00Z")))
            .hasEntries(entry("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a"));

        assertThat(first(result.getEntries()))
            .hasTitle("Atom is not what you think")
            .hasLinks(
                Link.builder("http://example.org/2003/12/13/atom03")
                    .build())
            .hasPublishedDate(Date.from(Instant.parse("1977-02-05T01:00:00Z")))
            .hasUpdateDate(Date.from(Instant.parse("1986-04-01T02:00:00Z")))
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
            "        <vidal:galenicForm xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\" vidalId=\"476\">solution injectable</vidal:galenicForm>\n" +
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
            .hasExtensionElements(
                ExtensionElements.simpleElement("date", dateFormat().format(new Date(1329350400000L)))
                    .withNamespace(Namespace.builder("http://purl.org/dc/elements/1.1/").withPrefix("dc").build())
                    .addAttribute(Attribute.builder("format", "yyyy-MM-dd'T'HH:mm:ss'Z'").withNamespace(Namespace.builder("http://date-formats.com").withPrefix("df").build()).build())
                    .addAttribute(Attribute.builder("xmlns", "http://www.w3.org/2005/Atom").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").build()).build())
                    .addAttribute(Attribute.builder("dc", "http://purl.org/dc/elements/1.1/").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build()).build())
                    .addAttribute(Attribute.builder("df", "http://date-formats.com").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build()).build())
                    .build(),
                ExtensionElements.simpleElement("itemsPerPage", "25")
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
            .hasExtensionElements(
                ExtensionElements.simpleElement("id", "15070")
                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                    .addAttribute(XMLNS_ATTRIBUTE)
                    .addAttribute(Attribute.builder("vidal", "http://api.vidal.net/-/spec/vidal-api/1.0/").withNamespace(XMLNS_NAMESPACE).build())
                    .build(),
                ExtensionElements.simpleElement("galenicForm", "solution injectable")
                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                    .addAttribute(XMLNS_ATTRIBUTE)
                    .addAttribute(Attribute.builder("vidal", "http://api.vidal.net/-/spec/vidal-api/1.0/").withNamespace(XMLNS_NAMESPACE).build())
                    .addAttribute(Attribute.builder("vidalId", "476").build())
                    .build()
            );
    }

    @Test
    public void unmarshall_feed_with_extended_elements() throws Exception {

        //language=XML
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <extension:simple xmlns:extension=\"http://foo.bar.org/extension/\">Text content</extension:simple>\n" +
            "    <extension:withattribute xmlns:extension=\"http://foo.bar.org/extension/\" customAttribute=\"attribute-value\">Text content</extension:withattribute>\n" +
            "    <extension:wrapper xmlns:extension=\"http://foo.bar.org/extension/\">\n" +
            "        <extension:item>\n" +
            "            <extension:name>Item name</extension:name>\n" +
            "            <extension:content>Item content</extension:content>\n" +
            "        </extension:item>\n" +
            "        <extension:content>Wrapped content</extension:content>\n" +
            "    </extension:wrapper>\n" +
            "</feed>";

        Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

        assertThat(result)
            .hasExtensionElements(
                ExtensionElements.simpleElement("simple", "Text content")
                    .withNamespace(EXTENSION_NAMESPACE)
                    .addAttribute(XMLNS_ATTRIBUTE)
                    .addAttribute(Attribute.builder("extension", "http://foo.bar.org/extension/")
                        .withNamespace(XMLNS_NAMESPACE)
                        .build())
                    .build(),
                ExtensionElements.simpleElement("withattribute", "Text content")
                    .withNamespace(EXTENSION_NAMESPACE)
                    .addAttribute(XMLNS_ATTRIBUTE)
                    .addAttribute(Attribute.builder("extension", "http://foo.bar.org/extension/")
                        .withNamespace(XMLNS_NAMESPACE)
                        .build())
                    .addAttribute(Attribute.builder("customAttribute", "attribute-value")
                        .build())
                    .build(),
                ExtensionElements.structuredElement("wrapper",
                    ExtensionElements.structuredElement("item", ExtensionElements.simpleElement("name", "Item name")
                        .withNamespace(EXTENSION_NAMESPACE)
                        .build())
                        .addChild(ExtensionElements.simpleElement("content", "Item content")
                            .withNamespace(EXTENSION_NAMESPACE)
                            .build())
                        .withNamespace(EXTENSION_NAMESPACE)
                        .build())
                    .addChild(ExtensionElements.simpleElement("content", "Wrapped content")
                        .withNamespace(EXTENSION_NAMESPACE)
                        .build())
                    .withNamespace(EXTENSION_NAMESPACE)
                    .addAttribute(XMLNS_ATTRIBUTE)
                    .addAttribute(Attribute.builder("extension", "http://foo.bar.org/extension/")
                        .withNamespace(XMLNS_NAMESPACE)
                        .build())
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

    @Test
    public void can_unmarshall_an_entry() throws JAXBException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><entry xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                     "        <title>Atom is not what you think</title>\n" +
                     "        <link href=\"http://example.org/2003/12/13/atom03\"/>\n" +
                     "        <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>\n" +
                     "        <published>1977-02-05T01:00:00Z</published>\n" +
                     "        <updated>1986-04-01T02:00:00Z</updated>\n" +
                     "        <summary>April's fool!</summary>\n" +
                     "        <content/>\n" +
                     "    </entry>";

        Entry result = (Entry) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

        assertThat(result)
              .hasTitle("Atom is not what you think")
              .hasLinks(Link.builder("http://example.org/2003/12/13/atom03").build())
              .hasId("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a")
              .hasPublishedDate(Date.from(Instant.parse("1977-02-05T01:00:00Z")))
              .hasUpdateDate(Date.from(Instant.parse("1986-04-01T02:00:00Z")))
              .hasSummary(Summary.builder().withValue("April's fool!").build());
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

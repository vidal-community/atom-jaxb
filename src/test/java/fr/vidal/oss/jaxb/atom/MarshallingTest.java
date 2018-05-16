package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.*;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
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
        marshaller = AtomJaxb.newContext().createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    }

    @Test
    public void marshalls_standard_atom_feed() throws JAXBException, IOException {
        Feed.Builder feedBuilder = Feed.builder()
            .withId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6")
            .withTitle("My standard Atom 1.0 feed")
            .withSubtitle("Or is it?")
            .withUpdateDate(new Date(510278400000L))
            .withAuthor(Author.builder("VIDAL").build())
            .addLink(Link.builder("http://example.org/").withRel(self).build());

        Entry.Builder builder = Entry.builder()
            .addLink(Link.builder("http://example.org/2003/12/13/atom03").build())
            .withTitle("Atom is not what you think")
            .withId("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a")
            .withPublishedDate(new Date(223948800000L))
            .withUpdateDate(new Date(512697600000L))
            .withSummary(Summary.builder().withValue("April's fool!").build());

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
                        "        <published>1977-02-05T01:00:00Z</published>\n" +
                        "        <updated>1986-04-01T02:00:00Z</updated>\n" +
                        "        <summary>April's fool!</summary>\n" +
                        "        <content/>\n" +
                        "    </entry>\n" +
                        "</feed>");
        }
    }

    @Test
    public void marshalls_feed_with_vendor_namespace_elements() throws JAXBException, IOException {
        Feed.Builder builder = Feed.builder()
            .withId("Heidi")
            .withTitle("Search Products - Query :sintrom")
            .addLink(Link.builder("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").withRel(self).withType("application/atom+xml").build())
            .withUpdateDate(new Date(1329350400000L))
            .addSimpleElement(
                SimpleElement.builder("date", DATE_FORMAT.format(new Date(1329350400000L)))
                    .withNamespace(Namespace.builder("http://purl.org/dc/elements/1.1/").withPrefix("dc").build())
                    .addAttribute(Attribute.builder("format", "yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .withNamespace(Namespace.builder("http://date-formats.com").withPrefix("df").build()).build())
                    .build()
            )
            .addSimpleElement(SimpleElement.builder("itemsPerPage", String.valueOf(25))
                .withNamespace(Namespace.builder("http://a9.com/-/spec/opensearch/1.1/").withPrefix("opensearch").build())
                .build()
            )
            .addSimpleElement(SimpleElement.builder("totalResults", String.valueOf(2))
                .withNamespace(Namespace.builder("http://a9.com/-/spec/opensearch/1.1/").withPrefix("opensearch").build())
                .build()
            )
            .addSimpleElement(SimpleElement.builder("startIndex", String.valueOf(1))
                .withNamespace(Namespace.builder("http://a9.com/-/spec/opensearch/1.1/").withPrefix("opensearch").build())
                .build()
            )
            .addEntry(
                Entry.builder()
                    .withTitle("SINTROM 4 mg cp quadriséc")
                    .addLink(Link.builder("/rest/api/product/15070").withRel(alternate).withType("application/atom+xml").build())
                    .addLink(Link.builder("/rest/api/product/15070/packages").withRel(related).withType("application/atom+xml").withTitle("PACKAGES").build())
                    .addLink(Link.builder("/rest/api/product/15070/documents").withRel(related).withType("application/atom+xml").withTitle("DOCUMENTS").build())
                    .addLink(Link.builder("/rest/api/product/15070/documents/opt").withRel(related).withType("application/atom+xml").withTitle("OPT_DOCUMENT").build())
                    .addCategory(Category.builder("PRODUCT").build())
                    .withAuthor(Author.builder("VIDAL").build())
                    .withId("vidal://product/15070")
                    .withUpdateDate(new Date(1329350400000L))
                    .withSummary(Summary.builder().withValue("SINTROM 4 mg cp quadriséc").withType("text").build())
                    .addSimpleElement(
                        SimpleElement.builder("id", String.valueOf(15070))
                            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                            .build()
                    )
                    .build()
            ).addEntry(
                Entry.builder()
                    .withTitle("SNAKE OIL 1 mg")
                    .addLink(Link.builder("/rest/api/product/42").withRel(alternate).withType("application/atom+xml").build())
                    .addLink(Link.builder("/rest/api/product/42/packages").withRel(related).withType("application/atom+xml").withTitle("PACKAGES").build())
                    .addLink(Link.builder("/rest/api/product/42/documents").withRel(related).withType("application/atom+xml").withTitle("DOCUMENTS").build())
                    .addLink(Link.builder("/rest/api/product/42/documents/opt").withRel(related).withType("application/atom+xml").withTitle("OPT_DOCUMENT").build())
                    .addCategory(Category.builder("PRODUCT").build())
                    .withAuthor(Author.builder("VIDAL").build())
                    .withId("vidal://product/42")
                    .withUpdateDate(new Date(1329350400000L))
                    .withSummary(Summary.builder().withValue("SNAKE OIL 1 mg").withType("text").build())
                    .addSimpleElement(SimpleElement.builder("id", String.valueOf(42))
                        .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
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
                    "    <id>Heidi</id>\n" +
                    "    <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "    <dc:date df:format=\"yyyy-MM-dd'T'HH:mm:ss'Z'\"\n" +
                    "             xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:df=\"http://date-formats.com\">2012-02-16T01:00:00Z</dc:date>\n" +
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


    @Test
    public void marshalls_custom_entry_attributes() throws IOException, JAXBException {
        Feed feed = Feed.builder()
            .withId("Heidi")
            .withTitle("Search Products - Query :sintrom")
            .addLink(Link.builder("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").withRel(self).withType("application/atom+xml").build())
            .withUpdateDate(new Date(1329350400000L))
            .addEntry(
                Entry.builder()
                    .withTitle("SINTROM 4 mg cp quadriséc")
                    .addLink(Link.builder("/rest/api/product/15070").withRel(alternate).withType("application/atom+xml").build())
                    .addCategory(Category.builder("PRODUCT").build())
                    .addCategory(Category.builder("PACK").build())
                    .withAuthor(Author.builder("VIDAL").build())
                    .withId("vidal://product/15070")
                    .withUpdateDate(new Date(1329350400000L))
                    .addAttribute(Attribute.builder("type", "PRODUCT,PACK")
                        .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/")
                            .withPrefix("vidal")
                            .build())
                        .build()
                    )
                    .withSummary(Summary.builder().withValue("SINTROM 4 mg cp quadriséc").withType("text").build())
                    .addSimpleElement(
                        SimpleElement.builder("id", String.valueOf(15070))
                            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                            .build()
                    )
                    .build()
            )
            .build();

        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(feed, writer);
            assertThat(writer.toString())
                .isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
                    "</feed>");
        }
    }

    @Test
    public void marshalls_custom_structured_element_with_attribute() throws IOException, JAXBException {
        Feed.Builder builder = Feed.builder()
            .withId("Heidi")
            .withTitle("Search Products - Query :sintrom")
            .addLink(Link.builder("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").withRel(self).withType("application/atom+xml").build())
            .withUpdateDate(new Date(1329350400000L))

            .addSimpleElement(
                StructuredElement.builder("structured", Attribute.builder("type", "PRODUCT,PACK")
                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/")
                        .withPrefix("vidal")
                        .build())
                    .build())
                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                    .build()
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
                    "    <id>Heidi</id>\n" +
                    "    <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "    <vidal:structured vidal:type=\"PRODUCT,PACK\" xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\"/>\n" +
                    "</feed>\n");
        }
    }

    @Test
    public void marshalls_custom_structured_element_with_child() throws IOException, JAXBException {
        Feed.Builder builder = Feed.builder()
            .withId("Heidi")
            .withTitle("Search Products - Query :sintrom")
            .addLink(Link.builder("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").withRel(self).withType("application/atom+xml").build())
            .withUpdateDate(new Date(1329350400000L))

            .addSimpleElement(
                StructuredElement.builder("structured",
                    SimpleElement.builder("child", "Child content")
                        .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                        .build())
                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                    .build()
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
                    "    <id>Heidi</id>\n" +
                    "    <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "    <vidal:structured xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">\n" +
                    "            <vidal:child>Child content</vidal:child>\n" +
                    "    </vidal:structured>\n" +
                    "</feed>\n");
        }
    }

    @Test
    public void marshalls_custom_structured_element_with_children() throws IOException, JAXBException {
        SimpleElement child1 = SimpleElement.builder("child1", "Child content1")
            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
            .build();
        SimpleElement child2 = SimpleElement.builder("child2", "Child content2")
            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
            .build();
        SimpleElement child3 = SimpleElement.builder("child3", "Child content3")
            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
            .build();

        Feed.Builder builder = Feed.builder()
            .withId("Heidi")
            .withTitle("Search Products - Query :sintrom")
            .addLink(Link.builder("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").withRel(self).withType("application/atom+xml").build())
            .withUpdateDate(new Date(1329350400000L))

            .addSimpleElement(
                StructuredElement.builder("structured",
                    child1)
                    .addChildElement(child2)
                    .addChildElement(child3)
                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
                    .build()
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
                    "    <id>Heidi</id>\n" +
                    "    <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "    <vidal:structured xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">\n" +
                    "        <vidal:child1>Child content1</vidal:child1>\n" +
                    "        <vidal:child2>Child content2</vidal:child2>\n" +
                    "        <vidal:child3>Child content3</vidal:child3>\n" +
                    "    </vidal:structured>\n" +
                    "</feed>\n");
        }
    }

    @Test
    public void marshalls_custom_structured_element_with_children_duplicate() throws IOException, JAXBException {
//        SimpleElement child1 = SimpleElement.builder("child1", "Child content1")
//            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
//            .build();
//        SimpleElement child2 = SimpleElement.builder("child2", "Child content2")
//            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
//            .build();
//        SimpleElement child3 = SimpleElement.builder("child3", "Child content3")
//            .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
//            .build();

        Feed.Builder builder = Feed.builder()
            .withId("Heidi")
            .withTitle("Search Products - Query :sintrom")
            .addLink(Link.builder("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").withRel(self).withType("application/atom+xml").build())
            .withUpdateDate(new Date(1329350400000L));

//            .addSimpleElement(
//                StructuredElement.builder("structured", "Text one")
//                    .addText("Text two")
//                    .addText("Text three")
//                    .addChildElement()
//                    .withNamespace(Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build())
//                    .build()
//            );

        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(builder.build(), writer);
            assertThat(writer.toString())
                .isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                    "    <title>Search Products - Query :sintrom</title>\n" +
                    "    <link\n" +
                    "        href=\"/rest/api/products?q=sintrom&amp;amp;start-page=1&amp;amp;page-size=25\"\n" +
                    "        rel=\"self\" type=\"application/atom+xml\"/>\n" +
                    "    <id>Heidi</id>\n" +
                    "    <updated>2012-02-16T01:00:00Z</updated>\n" +
                    "    <vidal:structured xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">\n" +
                    "        Text one\n" +
                    "        Text two\n" +
                    "        Text three\n" +
                    "    </vidal:structured>\n" +
                    "</feed>\n");
        }
    }
}

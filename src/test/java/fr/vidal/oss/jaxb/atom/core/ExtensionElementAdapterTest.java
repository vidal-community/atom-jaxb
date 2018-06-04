package fr.vidal.oss.jaxb.atom.core;

import org.junit.Test;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.TimeZone;

import static java.util.TimeZone.getTimeZone;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtensionElementAdapterTest {

    private static final Namespace ANY_NAMESPACE = Namespace.builder("http://foo.bar.net/-/any/").withPrefix("any").build();
    private static final Attribute XMLNS_ATTRIBUTE = Attribute.builder("xmlns", "http://www.w3.org/2005/Atom").withNamespace(Namespace.builder("http://www.w3.org/2000/xmlns/").build()).build();
    private static final Namespace XMLNS_NAMESPACE = Namespace.builder("http://www.w3.org/2000/xmlns/").withPrefix("xmlns").build();

    @Test
    public void marshal_single_simple_element() throws Exception {
        ExtensionElement element = element("element", "with value");

        String xml = marshalElement(element);

        assertThat(xml).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <any:element xmlns:any=\"http://foo.bar.net/-/any/\">with value</any:element>\n" +
            "</root>");
    }

    @Test
    public void marshal_structured_element_with_single_child() throws Exception {
        ExtensionElement element = element("wrapper",
            element("element", "with value")
        );

        String xml = marshalElement(element);

        assertThat(xml).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <any:wrapper xmlns:any=\"http://foo.bar.net/-/any/\">\n" +
            "        <any:element>with value</any:element>\n" +
            "    </any:wrapper>\n" +
            "</root>");
    }

    @Test
    public void marshal_structured_element_with_multiple_children() throws Exception {
        ExtensionElement element = ExtensionElements
            .structuredElement("wrapper", element("element", "with value"))
            .addChild(element("sub-wrapper", element("second-level-child", "value")))
            .withNamespace(ANY_NAMESPACE).build();

        String xml = marshalElement(element);

        assertThat(xml).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <any:wrapper xmlns:any=\"http://foo.bar.net/-/any/\">\n" +
            "        <any:element>with value</any:element>\n" +
            "        <any:sub-wrapper>\n" +
            "            <any:second-level-child>value</any:second-level-child>\n" +
            "        </any:sub-wrapper>\n" +
            "    </any:wrapper>\n" +
            "</root>");
    }

    @Test
    public void unmarshal_single_simple_element() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <any:element xmlns:any=\"http://foo.bar.net/-/any/\">with value</any:element>\n" +
            "</root>";

        ExtensionElement result = unmarshalElement(xml);

        assertThat(result).isEqualTo(ExtensionElements.simpleElement("element", "with value")
            .withNamespace(ANY_NAMESPACE)
            .addAttribute(XMLNS_ATTRIBUTE)
            .addAttribute(Attribute.builder("any", "http://foo.bar.net/-/any/")
                .withNamespace(XMLNS_NAMESPACE)
                .build())
            .build());
    }

    @Test
    public void unmarshal_structured_element_with_single_child() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <any:wrapper xmlns:any=\"http://foo.bar.net/-/any/\">\n" +
            "        <any:element>with value</any:element>\n" +
            "    </any:wrapper>\n" +
            "</root>";

        ExtensionElement result = unmarshalElement(xml);

        assertThat(result).isEqualTo(ExtensionElements.structuredElement("wrapper", element("element", "with value"))
            .withNamespace(ANY_NAMESPACE)
            .addAttribute(XMLNS_ATTRIBUTE)
            .addAttribute(Attribute.builder("any", "http://foo.bar.net/-/any/")
                .withNamespace(XMLNS_NAMESPACE)
                .build())
            .build());
    }

    @Test
    public void unmarshal_structured_element_with_multiple_children() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "    <any:wrapper xmlns:any=\"http://foo.bar.net/-/any/\">\n" +
            "        <any:element>with value</any:element>\n" +
            "        <any:sub-wrapper>\n" +
            "            <any:second-level-child>value</any:second-level-child>\n" +
            "        </any:sub-wrapper>\n" +
            "    </any:wrapper>\n" +
            "</root>";

        ExtensionElement result = unmarshalElement(xml);

        assertThat(result).isEqualTo(ExtensionElements.structuredElement("wrapper", element("element", "with value"))
            .withNamespace(ANY_NAMESPACE)
            .addAttribute(XMLNS_ATTRIBUTE)
            .addAttribute(Attribute.builder("any", "http://foo.bar.net/-/any/")
                .withNamespace(XMLNS_NAMESPACE)
                .build())
            .addChild(element("sub-wrapper", element("second-level-child", "value")))
            .build());
    }

    private String marshalElement(ExtensionElement element) throws IOException, JAXBException {
        try (StringWriter writer = new StringWriter()) {
            Marshaller marshaller = marshaller();
            marshaller.marshal(new Root(element), writer);
            return writer.toString();
        }
    }

    private ExtensionElement unmarshalElement(String xml) throws JAXBException {
        Unmarshaller unmarshaller = context().createUnmarshaller();
        Root root = (Root) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));
        return root.element;
    }

    private Marshaller marshaller() throws JAXBException {
        TimeZone.setDefault(getTimeZone("Europe/Paris"));
        Marshaller marshaler = context().createMarshaller();
        marshaler.setProperty(marshaler.JAXB_FORMATTED_OUTPUT, true);
        marshaler.setProperty(marshaler.JAXB_ENCODING, "UTF-8");
        return marshaler;
    }

    private JAXBContext context() throws JAXBException {
        return JAXBContext.newInstance(
            ExtensionElement.class, StructuredElement.class, SimpleElement.class, Root.class);
    }

    private ExtensionElement element(String tag, String value) {
        return ExtensionElements.simpleElement(tag, value).withNamespace(ANY_NAMESPACE).build();
    }

    private ExtensionElement element(String tag, ExtensionElement value) {
        return ExtensionElements.structuredElement(tag, value).withNamespace(ANY_NAMESPACE).build();
    }

}

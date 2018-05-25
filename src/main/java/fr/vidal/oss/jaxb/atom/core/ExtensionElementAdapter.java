package fr.vidal.oss.jaxb.atom.core;

import fr.vidal.oss.jaxb.atom.extensions.AnyElement;
import fr.vidal.oss.jaxb.atom.extensions.SimpleElement;
import fr.vidal.oss.jaxb.atom.extensions.StructuredElement;

import static java.lang.String.format;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ExtensionElementAdapter extends XmlAdapter<Element, ExtensionElement> {

    private static final Class[] ADAPTED_TYPES = {StructuredElement.class, SimpleElement.class, AnyElement.class};

    private DocumentBuilder builder;
    private JAXBContext context;

    @Override
    public Element marshal(ExtensionElement extensionElement) throws Exception {
        if (extensionElement == null) {
            return null;
        }

        JAXBElement jaxbElement = extensionElement.converter().convert(extensionElement);

        Document document = builder().newDocument();
        context().createMarshaller().marshal(jaxbElement, document);
        Element element = document.getDocumentElement();

        addAttributes(element, extensionElement);
        return element;
    }

    @Override
    public ExtensionElement unmarshal(Element element) {
        if (isStructuredElement(element)) {
            return unmarshalStructured(element);
        }
        if (isSimpleElement(element)) {
            return unmarshalSimple(element);
        }
        return null;
    }

    private boolean isStructuredElement(Element element) {
        return false;
    }

    private boolean isSimpleElement(Element element) {
        return true;
    }

    public SimpleElement unmarshalSimple(Element element) {
        if (element == null) {
            return null;
        }

        SimpleElement.Builder result =
            SimpleElement.builder(element.getLocalName(), element.getTextContent())
                .withNamespace(namespace(element));

        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node item = attributes.item(i);
            result.addAttribute(Attribute.builder(item.getLocalName(), item.getTextContent()).withNamespace(namespace(item)).build());
        }
        return result.build();
    }

    public SimpleElement unmarshalStructured(Element element) {
        return null;
    }

    private DocumentBuilder builder() throws Exception {
        if (builder == null) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            builder = dbf.newDocumentBuilder();
        }
        return builder;
    }

    private JAXBContext context() throws Exception {
        if (context == null) {
            context = JAXBContext.newInstance(ADAPTED_TYPES);
        }
        return context;
    }

    private void addAttributes(Element element, ExtensionElement extensionElement) {
        for (Attribute attribute : extensionElement.attributes()) {
            addAttribute(element, attribute);
        }
    }

    private void addAttribute(Element element, Attribute attribute) {
        Namespace namespace = attribute.getNamespace();
        String attributeName = attribute.getName();
        String attributeValue = attribute.getValue();

        if (namespace == null) {
            element.setAttribute(attributeName, attributeValue);
            return;
        }

        element.setAttributeNS(
            namespace.uri(),
            format("%s:%s", namespace.prefix(), attributeName),
            attributeValue
        );
    }

    private static Namespace namespace(Node item) {
        return Namespace.builder(item.getNamespaceURI()).withPrefix(item.getPrefix()).build();
    }
}

package fr.vidal.oss.jaxb.atom.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.String.format;

public class AdditionalElementAdapter extends XmlAdapter<Element, AdditionalElement> {

    private DocumentBuilder builder;
    private JAXBContext context;

    @Override
    public Element marshal(AdditionalElement additionalElement) throws Exception {
        if (additionalElement == null) {
            return null;
        }

        Element element = createElement(additionalElement);
        addAttributes(element, additionalElement);

        if (additionalElement instanceof StructuredElement) {
            addChildren(element, (StructuredElement) additionalElement);
        }
        return element;
    }

    @Override
    public AdditionalElement unmarshal(Element element) {
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

    private Element createElement(AdditionalElement additionalElement) throws Exception {
        Document document = builder().newDocument();
        marshallAsChild(additionalElement, document);
        return document.getDocumentElement();
    }

    private void marshallAsChild(AdditionalElement element, Node parent) throws Exception {
        context(element.getClass())
            .createMarshaller()
            .marshal(jaxbElement(element), parent);
    }

    private DocumentBuilder builder() throws Exception {
        if (builder == null) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            builder = dbf.newDocumentBuilder();
        }
        return builder;
    }

    private JAXBContext context(Class<?> type) throws Exception {
        if (context == null) {
            context = JAXBContext.newInstance(type);
        }
        return context;
    }

    private JAXBElement<String> jaxbElement(AdditionalElement element) {
        return new JAXBElement<>(
            qualifiedName(element),
            String.class,
            element.value()
        );
    }

    private void addAttributes(Element element, AdditionalElement additionalElement) {
        for (Attribute attribute : additionalElement.attributes()) {
            addAttribute(element, attribute);
        }
    }

    private void addChildren(Element element, StructuredElement structuredElement) throws Exception {
        for (AdditionalElement additionalChild : structuredElement.getAdditionalElements()) {
            marshallAsChild(additionalChild, element);
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

    private QName qualifiedName(AdditionalElement simpleElement) {
        Namespace namespace = simpleElement.namespace();
        if (namespace != null) {
            return new QName(namespace.uri(), simpleElement.tagName(), namespace.prefix());
        }
        return new QName(simpleElement.tagName());
    }

    private static Namespace namespace(Node item) {
        return Namespace.builder(item.getNamespaceURI()).withPrefix(item.getPrefix()).build();
    }
}

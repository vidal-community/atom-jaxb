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

        Element element;
        if (additionalElement instanceof SimpleElement) {
            element = createSimpleElement((SimpleElement) additionalElement);
        }else
        if (additionalElement instanceof StructuredElement) {
            element = createStructuredElement((StructuredElement) additionalElement);
        } else {
            element = createAnyElement((AnyElement) additionalElement);
        }

        return element;
    }

    private Element createSimpleElement(SimpleElement additionalElement) throws Exception {
        Document document = builder().newDocument();
        context(additionalElement.getClass())
            .createMarshaller()
            .marshal(jaxbSimpleElement(additionalElement), document);
        Element element = document.getDocumentElement();

        addAttributes(element, additionalElement);

        return element;
    }

    private Element createStructuredElement(StructuredElement additionalElement) throws Exception {
        Document document = builder().newDocument();
        context(additionalElement.getClass())
            .createMarshaller()
            .marshal(jaxbStructuredElement(additionalElement), document);
        Element element = document.getDocumentElement();

        addAttributes(element, additionalElement);

            addChildren(element, additionalElement);

        return element;
    }

    private Element createAnyElement(AnyElement additionalElement) throws Exception {
        Document document = builder().newDocument();
        context(additionalElement.getClass())
            .createMarshaller()
            .marshal(jaxbAnyElement(additionalElement), document);
        Element element = document.getDocumentElement();

        addAttributes(element, additionalElement);

    /*    if (additionalElement instanceof StructuredElement) {
            addChildren(element, (AnyElement) additionalElement);
        }*/
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

//    private Document createDocument(AdditionalElement additionalElement) throws Exception {
//        Document document = builder().newDocument();
//        marshallAsChild(additionalElement, document);
//        return document;
//    }

//    private void marshallAsChild(AdditionalElement element, Node parent) throws Exception {
//        context(element.getClass())
//            .createMarshaller()
//            .marshal(jaxbElement(element), parent);
//    }

    private DocumentBuilder builder() throws Exception {
        if (builder == null) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            builder = dbf.newDocumentBuilder();
        }
        return builder;
    }

    private JAXBContext context(Class<?> type) throws Exception {
        if (context == null) {
            context = JAXBContext.newInstance(StructuredElement.class, SimpleElement.class, AnyElement.class);
        }
        return context;
    }

    private JAXBElement<String> jaxbSimpleElement(SimpleElement element) {
        String value = element.value() == null ? "" : element.value();
        JAXBElement<String> jaxbElement = new JAXBElement<>(
            qualifiedName(element),
            String.class,
            value
        );
        return jaxbElement;
    }

    private JAXBElement<StructuredElement> jaxbStructuredElement(StructuredElement element) {
        String value = element.value() == null ? "" : element.value();
        JAXBElement<StructuredElement> jaxbElement = new JAXBElement<>(
            qualifiedName(element),
            StructuredElement.class,
            element
        );
        return jaxbElement;
    }

    private JAXBElement<AnyElement> jaxbAnyElement(AnyElement element) {
        String value = element.value() == null ? "" : element.value();
        JAXBElement<AnyElement> jaxbElement = new JAXBElement<>(
            qualifiedName(element),
            AnyElement.class,
            element
        );
        return jaxbElement;
    }

    private void addAttributes(Element element, AdditionalElement additionalElement) {
        for (Attribute attribute : additionalElement.attributes()) {
            addAttribute(element, attribute);
        }
    }

    private void addChildren(Element element, StructuredElement structuredElement) throws Exception {
//        Element rootElement = document.getDocumentElement();
        for (AdditionalElement additionalChild : structuredElement.getAdditionalElements()) {
//            marshallAsChild(additionalChild, element);
//            AnyElement asAnyElement = (AnyElement) additionalChild;
//            rootElement.appendChild(createElement(element, asAnyElement));
        }
    }

    private Node createElement(Document document, AdditionalElement additionalElement) {
        Element element = document.createElement(additionalElement.tagName());
        return element;
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

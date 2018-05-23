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

        if (additionalElement instanceof SimpleElement) {
            return createSimpleElement((SimpleElement) additionalElement);
        }
        if (additionalElement instanceof StructuredElement) {
            return createStructuredElement((StructuredElement) additionalElement);
        }

        if (additionalElement instanceof AnyElement) {
            return createAnyElement((AnyElement) additionalElement);
        }

        throw new IllegalArgumentException("Cannot handle Additional element: " + additionalElement);
    }

    private Element createSimpleElement(SimpleElement additionalElement) throws Exception {
        ToJAXBElement simple = new ToJAXBElement<String, SimpleElement>() {
            @Override
            public JAXBElement<String> convert(SimpleElement element) {
                return jaxbSimpleElement(element);
            }
        };

        return convertElement(additionalElement, simple);
    }

    private Element createStructuredElement(StructuredElement additionalElement) throws Exception {
        ToJAXBElement structured = new ToJAXBElement<StructuredElement, StructuredElement>() {
            @Override
            public JAXBElement<StructuredElement> convert(StructuredElement element) {
                return jaxbStructuredElement(element);
            }
        };

        return convertElement(additionalElement, structured);
    }

    private Element createAnyElement(AnyElement additionalElement) throws Exception {
        ToJAXBElement any = new ToJAXBElement<AnyElement, AnyElement>() {
            @Override
            public JAXBElement<AnyElement> convert(AnyElement element) {
                return jaxbAnyElement(element);
            }
        };

        return convertElement(additionalElement, any);
    }

    private Element convertElement(AdditionalElement additionalElement, ToJAXBElement converter) throws Exception {
        Document document = builder().newDocument();

        context(additionalElement.getClass())
            .createMarshaller()
            .marshal(converter.convert(additionalElement), document);
        Element element = document.getDocumentElement();

        addAttributes(element, additionalElement);
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

    private interface ToJAXBElement<T, E extends AdditionalElement> {
        JAXBElement<T> convert(E element);
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

    private JAXBElement<StructuredElement> jaxbStructuredElement(StructuredElement structuredElement) {
        JAXBElement<StructuredElement> jaxbElement = new JAXBElement<>(
            qualifiedName(structuredElement),
            StructuredElement.class,
            structuredElement
        );
        return jaxbElement;
    }

    private JAXBElement<AnyElement> jaxbAnyElement(AnyElement anyElement) {
        JAXBElement<AnyElement> jaxbElement = new JAXBElement<>(
            qualifiedName(anyElement),
            AnyElement.class,
            anyElement
        );
        return jaxbElement;
    }

    private void addAttributes(Element element, AdditionalElement additionalElement) {
        for (Attribute attribute : additionalElement.attributes()) {
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

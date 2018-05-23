package fr.vidal.oss.jaxb.atom.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;

public class ExtensionElementAdapter extends XmlAdapter<Element, ExtensionElement> implements ElementQNameFactory {

    private static final Class[] ADAPTED_TYPES = {StructuredElement.class, SimpleElement.class, AnyElement.class};

    private DocumentBuilder builder;
    private JAXBContext context;
    private Collection<ExtensionElementConverter> possibleConversions;

    @Override
    public Element marshal(ExtensionElement extensionElement) throws Exception {
        if (extensionElement == null) {
            return null;
        }

        for (ExtensionElementConverter converter : possibleConversions()) {
            if (converter.canConvert(extensionElement)) {
                return convertElement(extensionElement, converter);
            }
        }

        throw new IllegalArgumentException("Cannot handle Additional element: " + extensionElement);
    }

    private Collection<ExtensionElementConverter> possibleConversions() {
        if(possibleConversions == null) {
            possibleConversions = Arrays.asList(
                new SimpleElementExtensionConverter(this),
                new StructuredElementExtensionConverter(this),
                new AnyElementExtensionConverter(this));
        }
        return possibleConversions;
    }

    private Element convertElement(ExtensionElement extensionElement, ExtensionElementConverter converter) throws Exception {
        Document document = builder().newDocument();

        context()
            .createMarshaller()
            .marshal(converter.convert(extensionElement), document);
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

    @Override
    public QName qualifiedName(ExtensionElement simpleElement) {
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

package fr.vidal.oss.jaxb.atom.core;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class ExtensionElementAdapter extends XmlAdapter<Element, ExtensionElement> {

    private static final Class[] ADAPTED_TYPES = {StructuredElement.class, SimpleElement.class};
    private static final JAXBContext context;
    private static final ThreadLocal<DocumentBuilder> getDocumentBuilder = ThreadLocal.withInitial(() -> {
        try {
           return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new AtomExtensionException("Cannot instantiate DocumentBuilder.", e);
        }
    });

    static {
       try {
          context = JAXBContext.newInstance(ADAPTED_TYPES);
       } catch (JAXBException e) {
           throw new AtomExtensionException("Cannot instantiate JAXBContext for classes : " + Arrays.toString(ADAPTED_TYPES), e);
       }
    }

    @Override
    public Element marshal(ExtensionElement extensionElement) throws Exception {
        if (extensionElement == null) {
            return null;
        }

        JAXBElement jaxbElement = extensionElement.toJAXBElement();

        DocumentBuilder documentBuilder = getDocumentBuilder.get();
        documentBuilder.reset();
        Document document = documentBuilder.newDocument();
        context.createMarshaller().marshal(jaxbElement, document);
        Element element = document.getDocumentElement();

        addAttributes(element, extensionElement);
        return element;
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
    public ExtensionElement unmarshal(Element element) {
        if (element == null) {
            return null;
        }
        return extensionElement(element);
    }

    private ExtensionElement extensionElement(Node node) {

        ExtensionElement.Builder elementBuilder;
        if (isSimpleElementNode(node)) {
            elementBuilder = ExtensionElements.simpleElement(node.getLocalName(), node.getTextContent());
        } else {
            elementBuilder = ExtensionElements.structuredElement(node.getLocalName(), children(node));
        }

        return elementBuilder
            .withNamespace(namespace(node))
            .addAttributes(attributes(node))
            .build();
    }

    private boolean isSimpleElementNode(Node node) {
        return node.getChildNodes().getLength() == 1
            && isTextualNode(node.getFirstChild());
    }

    private boolean isTextualNode(Node node) {
        return Text.class.isAssignableFrom(node.getClass());
    }

    private Collection<ExtensionElement> children(Node node) {
        return toElements(node.getChildNodes());
    }

    private Collection<ExtensionElement> toElements(NodeList nodes) {
        return IntStream.range(0, nodes.getLength())
            .mapToObj(nodes::item)
            .filter(n -> !isTextualNode(n))
            .map(this::extensionElement)
            .collect(Collectors.toList());
    }

    private Collection<Attribute> attributes(Node node) {
        NamedNodeMap attributes = node.getAttributes();

        return IntStream.range(0, attributes.getLength())
            .mapToObj(attributes::item)
            .map(Attr.class::cast)
            .map(ExtensionElementAdapter::attribute)
            .collect(Collectors.toList());
    }

    private static Attribute attribute(Attr attribute) {
        Attribute.Builder builder = Attribute.builder(attribute.getLocalName(), attribute.getTextContent());
        if (hasProperNamespace(attribute)) {
            builder.withNamespace(namespace(attribute));
        }
        return builder.build();
    }

    private static boolean hasProperNamespace(Attr attribute) {
        return attribute.getNamespaceURI() != null;
    }

    private static Namespace namespace(Node item) {
        return Namespace.builder(item.getNamespaceURI()).withPrefix(item.getPrefix()).build();
    }
}

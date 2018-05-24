package fr.vidal.oss.jaxb.atom.extensions;

import fr.vidal.oss.jaxb.atom.core.Attribute;
import fr.vidal.oss.jaxb.atom.core.ExtensionElement;
import fr.vidal.oss.jaxb.atom.extensions.AnyElement;
import fr.vidal.oss.jaxb.atom.extensions.StructuredElement;

import static fr.vidal.oss.jaxb.atom.extensions.StructuredElement.builder;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.Test;

public class StructuredElementTest {

    public static final String SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE = "A structured element should contain at least a child element or an attribute.";

    @Test
    public void raise_exception_when_tagName_is_missing() {
        Attribute attribute = null;
        assertThatThrownBy(() -> builder(null, attribute).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("TagName is mandatory.");
    }

    @Test
    public void raise_exception_when_attribute_is_null() {
        Attribute attribute = null;
        assertThatThrownBy(() -> builder("rootElement", attribute).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE);
    }

    @Test
    public void raise_exception_when_tagName_is_missing_element_case() {
        ExtensionElement childElement = null;
        assertThatThrownBy(() -> builder(null, childElement).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("TagName is mandatory.");
    }

    @Test
    public void raise_exception_when_child_element_is_null() {
        ExtensionElement childElement = null;
        assertThatThrownBy(() -> builder("rootElement", childElement).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE);
    }

    @Test
    public void construct_a_structured_element_with_multiple_attributes_and_elements() {
        Attribute attribute = anAttribute("type", "text");
        AnyElement childElement = aChildElement("dosages");

        StructuredElement rootElement = builder("rootElement", attribute)
            .addAttributes(attributes(
                anAttribute("attr", "value"),
                anAttribute("attr2", "value2"),
                anAttribute("attr3", "value3")))
            .addChildElements(childElements(
                childElement))
            .build();

        assertThat(rootElement.attributes()).containsExactly(
            attribute,
            anAttribute("attr", "value"),
            anAttribute("attr2", "value2"),
            anAttribute("attr3", "value3"));

        assertThat(rootElement.getExtensionElements()).containsExactly(childElement);
    }

    private List<ExtensionElement> childElements(ExtensionElement... childElements) {
        return asList(childElements);
    }

    private AnyElement aChildElement(String tagName) {
        return AnyElement.builder(tagName)
            .addAnyElement(AnyElement.builder("dose").build())
            .build();
    }

    private List<Attribute> attributes(Attribute... attributes) {
        return asList(attributes);
    }

    private Attribute anAttribute(String name, String value) {
        return Attribute.builder(name, value).build();
    }
}

package fr.vidal.oss.jaxb.atom.core;

import org.junit.Test;

import java.util.List;

import static fr.vidal.oss.jaxb.atom.core.StructuredElement.builder;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class StructuredElementTest {

    @Test
    public void raise_exception_when_tagName_is_missing() {
        Attribute attribute = null;
        try {
            builder(null, attribute).build();
            fail("Missing tagName");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("TagName is mandatory.");
        }
    }

    @Test
    public void raise_exception_when_attribute_is_null() {
        Attribute attribute = null;
        try {
            builder("rootElement", attribute).build();
            fail("Missing attribute");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("A structured element should contain at least an attribute.");
        }
    }

    @Test
    public void construct_a_structured_element_with_many_attributes_and_elements() {
        Attribute attribute = anAttribute("type", "text");
        SimpleElement childElement = aChildElement("vidal", "id");

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

        assertThat(rootElement.getAdditionalElements()).containsExactly(childElement);
    }

    private List<AdditionalElement> childElements(AdditionalElement... childElements) {
        return asList(childElements);
    }

    private SimpleElement aChildElement(String tagName, String value) {
        return SimpleElement.builder(tagName, value).build();
    }

    private List<Attribute> attributes(Attribute... attributes) {
        return asList(attributes);
    }

    private Attribute anAttribute(String name, String value) {
        return Attribute.builder(name, value).build();
    }
}

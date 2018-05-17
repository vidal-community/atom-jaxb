package fr.vidal.oss.jaxb.atom.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class StructuredElementTest {

    @Test
    public void raise_exception_when_tagName_is_missing() {
        Attribute attribute = null;
        try {
            StructuredElement.builder(null, attribute).build();
            fail("Missing tagName");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("TagName is mandatory.");
        }
    }

    @Test
    public void raise_exception_when_attribute_is_null() {
        Attribute attribute = null;
        try {
            StructuredElement.builder("rootElement", attribute).build();
            fail("Missing attribute");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("A structured element should contain at least an attribute.");
        }
    }

    @Test
    public void construct_a_structured_element_when_other_attributes_are_missing() {
        Attribute attribute = Attribute.builder("type", "text").build();

        StructuredElement rootElement = StructuredElement.builder("rootElement", attribute).build();

        assertThat(rootElement.attributes()).contains(attribute);
    }

    @Test
    public void construct_a_structured_element_when_other_attributes_are_null() {
        Attribute attribute = Attribute.builder("type", "text").build();

        StructuredElement rootElement = StructuredElement.builder("rootElement", attribute, null, null, null).build();

        assertThat(rootElement.attributes()).contains(attribute);
    }
}

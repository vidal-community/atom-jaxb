package fr.vidal.oss.jaxb.atom.core;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.Test;

public class ExtensionElementTest {

    private static final String SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE = StructuredElement.SHOULD_CONTAIN_ATTRIBUTE_OR_CHILD;
    private static final String SHOULD_CONTAIN_VALUE = SimpleElement.SHOULD_CONTAIN_VALUE;
    private static final String TAG_NAME_IS_MANDATORY = ExtensionElement.TAG_NAME_IS_MANDATORY;

    @Test
    public void raise_exception_when_tagName_is_missing() {
        assertThatThrownBy(() -> ExtensionElements.simpleElement(null, "value").build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(TAG_NAME_IS_MANDATORY);

        assertThatThrownBy(() -> ExtensionElements.structuredElement(null,  anAttribute("type", "text")).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(TAG_NAME_IS_MANDATORY);

        assertThatThrownBy(() -> ExtensionElements.structuredElement(null, aChildElement("child")).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(TAG_NAME_IS_MANDATORY);
    }

    @Test
    public void raise_exception_when_value_is_null() {
        assertThatThrownBy(() -> ExtensionElements.simpleElement("rootElement", null).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(SHOULD_CONTAIN_VALUE);
    }

    @Test
    public void raise_exception_when_attribute_is_null() {
        assertThatThrownBy(() -> ExtensionElements.structuredElement("rootElement", (Attribute) null).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE);
    }

    @Test
    public void raise_exception_when_child_element_is_null() {
        assertThatThrownBy(() -> ExtensionElements.structuredElement("rootElement", (ExtensionElement) null).build())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE);
    }

    @Test
    public void should_have_expected_tag_name() {
        String tagName = "a_special_tag_name";

        ExtensionElement element = ExtensionElements.simpleElement(tagName, "aValue").build();
        assertThat(element.tagName()).isEqualTo(tagName);

        element = ExtensionElements.structuredElement(tagName, anAttribute("type", "text")).build();
        assertThat(element.tagName()).isEqualTo(tagName);

        element = ExtensionElements.structuredElement(tagName, aChildElement("child")).build();
        assertThat(element.tagName()).isEqualTo(tagName);
    }

    @Test
    public void should_contains_value_when_build_with() {
        SimpleElement rootElement = ExtensionElements.simpleElement("rootElement", "content").build();

        assertThat(rootElement.value()).isEqualTo("content");
    }

    @Test
    public void should_contains_attributes_when_build_with() {
        ExtensionElement rootElement = ExtensionElements.structuredElement("rootElement", anAttribute("type", "text"))
            .addAttributes(attributes(
                anAttribute("attr", "value"),
                anAttribute("attr2", "value2"),
                anAttribute("attr3", "value3")))
            .build();

        assertThat(rootElement.attributes()).containsExactly(
            anAttribute("type", "text"),
            anAttribute("attr", "value"),
            anAttribute("attr2", "value2"),
            anAttribute("attr3", "value3"));
    }

    @Test
    public void should_contains_children_when_build_with() {
        StructuredElement rootElement = ExtensionElements.structuredElement("rootElement", aChildElement("mandatory-child"))
            .addChildren(childElements(
                aChildElement("first"),
                aChildElement("second"),
                aChildElement("third")))
            .build();

        assertThat(rootElement.getExtensionElements()).containsExactly(
            aChildElement("mandatory-child"),
            aChildElement("first"),
            aChildElement("second"),
            aChildElement("third")
        );
    }

    private List<ExtensionElement> childElements(ExtensionElement... childElements) {
        return asList(childElements);
    }

    private ExtensionElement aChildElement(String childName) {
        return ExtensionElements.simpleElement(childName, childName).build();
    }

    private List<Attribute> attributes(Attribute... attributes) {
        return asList(attributes);
    }

    private Attribute anAttribute(String name, String value) {
        return Attribute.builder(name, value).build();
    }
}

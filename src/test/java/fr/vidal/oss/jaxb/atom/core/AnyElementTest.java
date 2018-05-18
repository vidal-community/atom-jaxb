package fr.vidal.oss.jaxb.atom.core;

import org.junit.Test;

import static fr.vidal.oss.jaxb.atom.core.AnyElement.builder;
import static fr.vidal.oss.jaxb.atom.core.Attribute.builder;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class AnyElementTest {
    @Test
    public void should_raise_exception_when_tagName_is_null() {
        try {
            builder(null).build();
            fail("The tagName is mandatory.");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("The tagName is mandatory.");
        }
    }

    @Test
    public void should_construct_an_element_with_attributes_and_child_elements() {
        Attribute attribute = attribute("type", "text");
        AnyElement dosage = dosage();

        AnyElement dosages = builder("dosages")
            .addAttribute(attribute)
            .addAnyElement(dosage)
            .build();

        assertThat(dosages.attributes()).containsExactly(attribute);
        assertThat(dosages.anyElements()).containsExactly(dosage);
    }

    private AnyElement dosage() {
        return builder("dosage")
            .addAnyElements(
                asList(builder("dose").build(),builder("interval").build()))
            .build();
    }

    private Attribute attribute(String name, String value) {
        return builder(name, value).build();
    }
}

package fr.vidal.oss.jaxb.atom.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class StructuredElementTest {

    @Test
    public void should_raise_exception_when_attribute_and_element_are_absent() {
        try {
            StructuredElement.builder("rootElement", null).build();
            fail("Should not construct the structured element");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Mandatory");
        }
    }
}


//    @Test(expected = NullPointerException.class)
//    public void should_use_default_limit_if_not_provided() {
//        LimitedDataHint<Object> paging = new LimitedDataHint<>(null, null, (Integer) null);
//    }

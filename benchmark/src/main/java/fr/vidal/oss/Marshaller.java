/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package fr.vidal.oss;

import fr.vidal.oss.jaxb.atom.core.AtomJaxb;
import fr.vidal.oss.jaxb.atom.core.Attribute;
import fr.vidal.oss.jaxb.atom.core.Author;
import fr.vidal.oss.jaxb.atom.core.Category;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.ExtensionElements;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import fr.vidal.oss.jaxb.atom.core.Namespace;
import fr.vidal.oss.jaxb.atom.core.Summary;

import static fr.vidal.oss.jaxb.atom.core.DateAdapter.DATE_FORMAT;
import static fr.vidal.oss.jaxb.atom.core.LinkRel.alternate;
import static fr.vidal.oss.jaxb.atom.core.LinkRel.related;
import static fr.vidal.oss.jaxb.atom.core.LinkRel.self;

import java.io.StringWriter;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class Marshaller {

    Feed feed;
    JAXBContext jc;

    @Setup
    public void prepare() throws JAXBException {
        jc = AtomJaxb.newContext();

        Namespace vidalNamespace = Namespace.builder("http://api.vidal.net/-/spec/vidal-api/1.0/").withPrefix("vidal").build();
       feed = Feed.builder()
             .withId("Heidi")
             .withTitle("Search Products - Query :sintrom")
             .addLink(Link.builder("/rest/api/products?q=sintrom&amp;start-page=1&amp;page-size=25").withRel(self).withType("application/atom+xml").build())
             .withUpdateDate(new Date(1329350400000L))
             .addExtensionElement(
                   ExtensionElements.simpleElement("date", DATE_FORMAT.format(new Date(1329350400000L)))
                         .withNamespace(Namespace.builder("http://purl.org/dc/elements/1.1/").withPrefix("dc").build())
                         .addAttribute(Attribute.builder("format", "yyyy-MM-dd'T'HH:mm:ss'Z'")
                               .withNamespace(Namespace.builder("http://date-formats.com").withPrefix("df").build()).build())
                         .build()
             )
             .addExtensionElement(ExtensionElements.simpleElement("itemsPerPage", String.valueOf(25))
                   .withNamespace(Namespace.builder("http://a9.com/-/spec/opensearch/1.1/").withPrefix("opensearch").build())
                   .build()
             )
             .addExtensionElement(ExtensionElements.simpleElement("totalResults", String.valueOf(2))
                   .withNamespace(Namespace.builder("http://a9.com/-/spec/opensearch/1.1/").withPrefix("opensearch").build())
                   .build()
             )
             .addExtensionElement(ExtensionElements.simpleElement("startIndex", String.valueOf(1))
                   .withNamespace(Namespace.builder("http://a9.com/-/spec/opensearch/1.1/").withPrefix("opensearch").build())
                   .build()
             )
             .addEntry(
                   Entry.builder()
                         .withTitle("SINTROM 4 mg cp quadriséc")
                         .addLink(Link.builder("/rest/api/product/15070").withRel(alternate).withType("application/atom+xml").build())
                         .addLink(Link.builder("/rest/api/product/15070/packages").withRel(related).withType("application/atom+xml").withTitle("PACKAGES").build())
                         .addLink(Link.builder("/rest/api/product/15070/documents").withRel(related).withType("application/atom+xml").withTitle("DOCUMENTS").build())
                         .addLink(Link.builder("/rest/api/product/15070/documents/opt").withRel(related).withType("application/atom+xml").withTitle("OPT_DOCUMENT").build())
                         .addCategory(Category.builder("PRODUCT").build())
                         .withAuthor(Author.builder("VIDAL").build())
                         .withId("vidal://product/15070")
                         .withUpdateDate(new Date(1329350400000L))
                         .withSummary(Summary.builder().withValue("SINTROM 4 mg cp quadriséc").withType("text").build())
                         .addExtensionElement(
                               ExtensionElements.simpleElement("id", String.valueOf(15070))
                                     .withNamespace(vidalNamespace)
                                     .build()
                         )
                         .build()
             ).addEntry(
                   Entry.builder()
                         .withTitle("SNAKE OIL 1 mg")
                         .addLink(Link.builder("/rest/api/product/42").withRel(alternate).withType("application/atom+xml").build())
                         .addLink(Link.builder("/rest/api/product/42/packages").withRel(related).withType("application/atom+xml").withTitle("PACKAGES").build())
                         .addLink(Link.builder("/rest/api/product/42/documents").withRel(related).withType("application/atom+xml").withTitle("DOCUMENTS").build())
                         .addLink(Link.builder("/rest/api/product/42/documents/opt").withRel(related).withType("application/atom+xml").withTitle("OPT_DOCUMENT").build())
                         .addCategory(Category.builder("PRODUCT").build())
                         .withAuthor(Author.builder("VIDAL").build())
                         .withId("vidal://product/42")
                         .withUpdateDate(new Date(1329350400000L))
                         .withSummary(Summary.builder().withValue("SNAKE OIL 1 mg").withType("text").build())
                         .addExtensionElement(ExtensionElements.simpleElement("id", String.valueOf(42))
                               .withNamespace(vidalNamespace)
                               .build())
                         .addExtensionElement(ExtensionElements.structuredElement("dosages", ExtensionElements.structuredElement("dosage", ExtensionElements.simpleElement("dose", "10.0").withNamespace(vidalNamespace).build())
                                     .addChild(ExtensionElements.simpleElement("unitId", "129").withNamespace(vidalNamespace).build())
                                     .withNamespace(vidalNamespace)
                                     .build())
                               .withNamespace(vidalNamespace)
                               .build()
                         ).build())
             .build();
    }

    @Benchmark
    public String testMarshallingFeed() throws JAXBException {
       StringWriter result = new StringWriter();
       jc.createMarshaller().marshal(feed, result);
       return result.toString();
    }

}

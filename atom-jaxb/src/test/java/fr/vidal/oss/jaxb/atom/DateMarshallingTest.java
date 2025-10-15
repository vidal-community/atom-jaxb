package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.AtomJaxb;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import jakarta.xml.bind.Marshaller;

import static fr.vidal.oss.jaxb.atom.DateUnmarshallingTest.rollingTimezone;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateMarshallingTest {

   private Marshaller marshaller;
   private TimeZone originalTimeZone;

   @Before
   public void setUp() throws Exception {
      originalTimeZone = TimeZone.getDefault();
      marshaller = AtomJaxb.newContext().createMarshaller();
   }

   @After
   public void tearDown() {
      TimeZone.setDefault(originalTimeZone);
   }

   @Test
   public void should_marshall_date_to_utc() throws Throwable {
      rollingTimezone(() -> {
         Feed feed = Feed.builder()
               .withTitle("Feed Title")
               .withId("feed-id")
               .addLink(Link.builder("www.example.org").build())
               .withUpdateDate(Date.from(Instant.parse("1986-03-04T04:00:00Z")))
               .addEntry(Entry.builder()
                     .withTitle("Entry title")
                     .withId("entry-1")
                     .addLink(Link.builder("www.example.org/entry-1").build())
                     .withUpdateDate(Date.from(Instant.parse("1986-04-01T04:00:00Z")))
                     .withPublishedDate(Date.from(Instant.parse("1977-02-05T04:00:00Z")))
                     .build())
               .build();

         StringWriter result = new StringWriter();
         marshaller.marshal(feed, result);

         assertThat(result.toString()).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                                    "<feed xmlns=\"http://www.w3.org/2005/Atom\">" +
                                                    "    <title>Feed Title</title>" +
                                                    "    <link href=\"www.example.org\"/>" +
                                                    "    <id>feed-id</id>" +
                                                    "    <updated>1986-03-04T04:00:00Z</updated>" +
                                                    "    <entry>" +
                                                    "        <title>Entry title</title>" +
                                                    "        <link href=\"www.example.org/entry-1\"/>" +
                                                    "        <id>entry-1</id>" +
                                                    "        <published>1977-02-05T04:00:00Z</published>" +
                                                    "        <updated>1986-04-01T04:00:00Z</updated>" +
                                                    "        <content/>" +
                                                    "    </entry>" +
                                                    "</feed>");
      });
   }
}

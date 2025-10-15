package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.AtomJaxb;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import static fr.vidal.oss.jaxb.atom.DateUnmarshallingTest.rollingTimezone;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.TimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

public class DateEdgeCasesTest {

   private Marshaller marshaller;
   private Unmarshaller unmarshaller;
   private TimeZone originalTimeZone;

   @Before
   public void setUp() throws Exception {
      originalTimeZone = TimeZone.getDefault();
      JAXBContext jc = AtomJaxb.newContext();
      marshaller = jc.createMarshaller();
      unmarshaller = jc.createUnmarshaller();
   }

   @After
   public void tearDown() {
      TimeZone.setDefault(originalTimeZone);
   }

   @Test
   public void should_unmarshall_date_with_negative_offset() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                   "    <title>Feed Title</title>\n" +
                   "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                   "    <id>feed-id</id>\n" +
                   "    <updated>2023-10-15T14:30:00-05:00</updated>\n" +
                   "    <entry>\n" +
                   "        <title>Entry title</title>\n" +
                   "        <link href=\"http://example.org/entry-1\"/>\n" +
                   "        <id>entry-1</id>\n" +
                   "        <published>2023-10-15T10:00:00-08:00</published>\n" +
                   "        <updated>2023-10-15T14:30:00-05:00</updated>\n" +
                   "    </entry>\n" +
                   "</feed>";

      rollingTimezone(() -> {
         Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

         assertThat(result.getUpdateDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T14:30:00-05:00").toInstant());
         assertThat(result.getEntries()).singleElement()
               .satisfies(entry -> {
                  assertThat(entry.getPublishedDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T10:00:00-08:00").toInstant());
                  assertThat(entry.getUpdateDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T14:30:00-05:00").toInstant());
               });
      });
   }

   @Test
   public void should_unmarshall_date_with_half_hour_offset() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                   "    <title>Feed Title</title>\n" +
                   "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                   "    <id>feed-id</id>\n" +
                   "    <updated>2023-10-15T18:30:00+05:30</updated>\n" +
                   "    <entry>\n" +
                   "        <title>Entry title</title>\n" +
                   "        <link href=\"http://example.org/entry-1\"/>\n" +
                   "        <id>entry-1</id>\n" +
                   "        <published>2023-10-15T10:15:00+05:30</published>\n" +
                   "        <updated>2023-10-15T18:30:00+05:30</updated>\n" +
                   "    </entry>\n" +
                   "</feed>";

      rollingTimezone(() -> {
         Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

         // +05:30 is India Standard Time
         assertThat(result.getUpdateDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T18:30:00+05:30").toInstant());
         assertThat(result.getEntries()).singleElement()
               .satisfies(entry -> {
                  assertThat(entry.getPublishedDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T10:15:00+05:30").toInstant());
                  assertThat(entry.getUpdateDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T18:30:00+05:30").toInstant());
               });
      });
   }

   @Test
   public void should_unmarshall_date_with_45_minute_offset() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                   "    <title>Feed Title</title>\n" +
                   "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                   "    <id>feed-id</id>\n" +
                   "    <updated>2023-10-15T16:45:00+05:45</updated>\n" +
                   "    <entry>\n" +
                   "        <title>Entry title</title>\n" +
                   "        <link href=\"http://example.org/entry-1\"/>\n" +
                   "        <id>entry-1</id>\n" +
                   "        <published>2023-10-15T10:00:00+05:45</published>\n" +
                   "        <updated>2023-10-15T16:45:00+05:45</updated>\n" +
                   "    </entry>\n" +
                   "</feed>";

      rollingTimezone(() -> {
         Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

         // +05:45 is Nepal Time
         assertThat(result.getUpdateDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T16:45:00+05:45").toInstant());
         assertThat(result.getEntries()).singleElement()
               .satisfies(entry -> {
                  assertThat(entry.getPublishedDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T10:00:00+05:45").toInstant());
                  assertThat(entry.getUpdateDate()).isEqualTo(OffsetDateTime.parse("2023-10-15T16:45:00+05:45").toInstant());
               });
      });
   }

   @Test
   public void should_marshall_and_unmarshall_midnight() throws Throwable {
      rollingTimezone(() -> {
         Feed feed = Feed.builder()
               .withTitle("Feed Title")
               .withId("feed-id")
               .addLink(Link.builder("www.example.org").build())
               .withUpdateDate(Date.from(Instant.parse("2023-10-15T00:00:00Z")))
               .addEntry(Entry.builder()
                     .withTitle("Entry title")
                     .withId("entry-1")
                     .addLink(Link.builder("www.example.org/entry-1").build())
                     .withUpdateDate(Date.from(Instant.parse("2023-10-15T00:00:00Z")))
                     .withPublishedDate(Date.from(Instant.parse("2023-10-14T00:00:00Z")))
                     .build())
               .build();

         StringWriter result = new StringWriter();
         marshaller.marshal(feed, result);

         assertThat(result.toString())
               .containsSubsequence("2023-10-15T00:00:00Z", "2023-10-15T00:00:00Z")
               .contains("2023-10-14T00:00:00Z");

         // Round-trip test
         Feed unmarshalled = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(result.toString())));
         assertThat(unmarshalled.getUpdateDate()).isEqualTo(feed.getUpdateDate());
         assertThat(unmarshalled.getEntries().iterator().next().getPublishedDate())
               .isEqualTo(feed.getEntries().iterator().next().getPublishedDate());
      });
   }

   @Test
   public void should_handle_end_of_day() throws Throwable {
      rollingTimezone(() -> {
         Feed feed = Feed.builder()
               .withTitle("Feed Title")
               .withId("feed-id")
               .addLink(Link.builder("www.example.org").build())
               .withUpdateDate(Date.from(Instant.parse("2023-10-15T23:59:59Z")))
               .addEntry(Entry.builder()
                     .withTitle("Entry title")
                     .withId("entry-1")
                     .addLink(Link.builder("www.example.org/entry-1").build())
                     .withUpdateDate(Date.from(Instant.parse("2023-10-15T23:59:59Z")))
                     .withPublishedDate(Date.from(Instant.parse("2023-10-14T23:59:59Z")))
                     .build())
               .build();

         StringWriter result = new StringWriter();
         marshaller.marshal(feed, result);

         assertThat(result.toString())
               .containsSubsequence("2023-10-15T23:59:59Z", "2023-10-15T23:59:59Z")
               .contains("2023-10-14T23:59:59Z");

         // Round-trip test
         Feed unmarshalled = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(result.toString())));
         assertThat(unmarshalled.getUpdateDate()).isEqualTo(feed.getUpdateDate());
         assertThat(unmarshalled.getEntries().iterator().next().getPublishedDate())
               .isEqualTo(feed.getEntries().iterator().next().getPublishedDate());
      });
   }

   @Test
   public void should_handle_leap_year_date() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                   "    <title>Feed Title</title>\n" +
                   "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                   "    <id>feed-id</id>\n" +
                   "    <updated>2020-02-29T12:00:00Z</updated>\n" +
                   "    <entry>\n" +
                   "        <title>Entry title</title>\n" +
                   "        <link href=\"http://example.org/entry-1\"/>\n" +
                   "        <id>entry-1</id>\n" +
                   "        <published>2020-02-29T00:00:00Z</published>\n" +
                   "        <updated>2020-02-29T12:00:00Z</updated>\n" +
                   "    </entry>\n" +
                   "</feed>";

      rollingTimezone(() -> {
         Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

         assertThat(result.getUpdateDate()).isEqualTo(Instant.parse("2020-02-29T12:00:00Z"));
         assertThat(result.getEntries()).singleElement()
               .satisfies(entry -> {
                  assertThat(entry.getPublishedDate()).isEqualTo(Instant.parse("2020-02-29T00:00:00Z"));
                  assertThat(entry.getUpdateDate()).isEqualTo(Instant.parse("2020-02-29T12:00:00Z"));
               });
      });
   }

   @Test
   public void should_preserve_same_instant_across_different_offsets() throws Throwable {
      // The same instant expressed with different offsets should be equal
      String xmlUtc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                      "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                      "    <title>Feed Title</title>\n" +
                      "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                      "    <id>feed-id</id>\n" +
                      "    <updated>2023-10-15T12:00:00Z</updated>\n" +
                      "    <entry>\n" +
                      "        <title>Entry title</title>\n" +
                      "        <link href=\"http://example.org/entry-1\"/>\n" +
                      "        <id>entry-1</id>\n" +
                      "        <updated>2023-10-15T12:00:00Z</updated>\n" +
                      "    </entry>\n" +
                      "</feed>";

      String xmlParis = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                        "    <title>Feed Title</title>\n" +
                        "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                        "    <id>feed-id</id>\n" +
                        "    <updated>2023-10-15T14:00:00+02:00</updated>\n" +
                        "    <entry>\n" +
                        "        <title>Entry title</title>\n" +
                        "        <link href=\"http://example.org/entry-1\"/>\n" +
                        "        <id>entry-1</id>\n" +
                        "        <updated>2023-10-15T14:00:00+02:00</updated>\n" +
                        "    </entry>\n" +
                        "</feed>";

      String xmlNewYork = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                          "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                          "    <title>Feed Title</title>\n" +
                          "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                          "    <id>feed-id</id>\n" +
                          "    <updated>2023-10-15T08:00:00-04:00</updated>\n" +
                          "    <entry>\n" +
                          "        <title>Entry title</title>\n" +
                          "        <link href=\"http://example.org/entry-1\"/>\n" +
                          "        <id>entry-1</id>\n" +
                          "        <updated>2023-10-15T08:00:00-04:00</updated>\n" +
                          "    </entry>\n" +
                          "</feed>";

      rollingTimezone(() -> {
         Feed feedUtc = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xmlUtc)));
         Feed feedParis = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xmlParis)));
         Feed feedNewYork = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xmlNewYork)));

         // All three should represent the same instant in time
         Instant expectedInstant = Instant.parse("2023-10-15T12:00:00Z");
         assertThat(feedUtc.getUpdateDate()).isEqualTo(expectedInstant);
         assertThat(feedParis.getUpdateDate()).isEqualTo(expectedInstant);
         assertThat(feedNewYork.getUpdateDate()).isEqualTo(expectedInstant);

         // Entries too
         assertThat(feedUtc.getEntries().iterator().next().getUpdateDate()).isEqualTo(expectedInstant);
         assertThat(feedParis.getEntries().iterator().next().getUpdateDate()).isEqualTo(expectedInstant);
         assertThat(feedNewYork.getEntries().iterator().next().getUpdateDate()).isEqualTo(expectedInstant);
      });
   }
}


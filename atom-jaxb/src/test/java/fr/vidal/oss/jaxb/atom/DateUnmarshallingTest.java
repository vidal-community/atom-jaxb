package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.AtomJaxb;
import fr.vidal.oss.jaxb.atom.core.Feed;
import jakarta.xml.bind.Unmarshaller;

import static java.util.Arrays.asList;
import static java.util.TimeZone.getTimeZone;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.TimeZone;
import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

public class DateUnmarshallingTest {

   private Unmarshaller unmarshaller;
   private TimeZone originalTimeZone;

   @Before
   public void setUp() throws Exception {
      originalTimeZone = TimeZone.getDefault();
      unmarshaller = AtomJaxb.newContext().createUnmarshaller();
   }

   @After
   public void tearDown() {
      TimeZone.setDefault(originalTimeZone);
   }

   @Test
   public void should_unmarshall_date_from_utc() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                   "    <title>My standard Atom 1.0 feed</title>\n" +
                   "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                   "    <id>urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6</id>\n" +
                   "    <updated>1986-03-04T01:00:00Z</updated>\n" +
                   "    <entry>\n" +
                   "        <title>Atom is not what you think</title>\n" +
                   "        <link href=\"http://example.org/2003/12/13/atom03\"/>\n" +
                   "        <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>\n" +
                   "        <published>1977-02-05T01:00:00Z</published>\n" +
                   "        <updated>1986-04-01T02:00:00Z</updated>\n" +
                   "    </entry>\n" +
                   "</feed>";

      rollingTimezone(() -> {
         Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

         assertThat(result.getUpdateDate()).isEqualTo(Instant.parse("1986-03-04T01:00:00Z"));
         assertThat(result.getEntries()).singleElement()
               .satisfies(entry -> {
                  assertThat(entry.getPublishedDate()).isEqualTo(Instant.parse("1977-02-05T01:00:00Z"));
                  assertThat(entry.getUpdateDate()).isEqualTo(Instant.parse("1986-04-01T02:00:00Z"));
               });
      });
   }

   @Test
   public void should_unmarshall_date_from_europe_paris() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                   "    <title>My standard Atom 1.0 feed</title>\n" +
                   "    <link href=\"http://example.org/\" rel=\"self\"/>\n" +
                   "    <id>urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6</id>\n" +
                   "    <updated>1986-03-04T01:00:00+02:00</updated>\n" +
                   "    <entry>\n" +
                   "        <title>Atom is not what you think</title>\n" +
                   "        <link href=\"http://example.org/2003/12/13/atom03\"/>\n" +
                   "        <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>\n" +
                   "        <published>1977-02-05T01:00:00+02:00</published>\n" +
                   "        <updated>1986-04-01T02:00:00+02:00</updated>\n" +
                   "    </entry>\n" +
                   "</feed>";

      rollingTimezone(() -> {
         Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

         assertThat(result.getUpdateDate()).isEqualTo(OffsetDateTime.parse("1986-03-04T01:00:00+02:00").toInstant());
         assertThat(result.getEntries()).singleElement()
               .satisfies(entry -> {
                  assertThat(entry.getPublishedDate()).isEqualTo(OffsetDateTime.parse("1977-02-05T01:00:00+02:00").toInstant());
                  assertThat(entry.getUpdateDate()).isEqualTo(OffsetDateTime.parse("1986-04-01T02:00:00+02:00").toInstant());
               });
      });
   }

   public static void rollingTimezone(ThrowableAssert.ThrowingCallable code) throws Throwable {
      for (TimeZone tz : asList(
            getTimeZone("UTC"),
            getTimeZone("Europe/Paris"),
            getTimeZone("America/New_York"),
            getTimeZone("Asia/Tokyo"))) {
         System.out.println("Setting timezone to " + tz.getID());
         TimeZone.setDefault(tz);
         code.call();
      }
   }
}

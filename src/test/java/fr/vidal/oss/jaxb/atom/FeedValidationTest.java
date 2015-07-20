package fr.vidal.oss.jaxb.atom;

import fr.vidal.oss.jaxb.atom.core.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static fr.vidal.oss.jaxb.atom.core.LinkRel.alternate;
import static fr.vidal.oss.jaxb.atom.core.LinkRel.self;

public class FeedValidationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void feed_must_contain_exactly_one_id() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("id is mandatory");

        Feed.Builder feedBuilder = Feed.builder()
            .withTitle("My standard Atom 1.0 feed")
            .withSubtitle("Or is it?")
            .withUpdateDate(new Date(510278400000L))
            .withAuthor(Author.builder("VIDAL").build())
            .addLink(Link.builder("http://example.org/").withRel(self).build());

        feedBuilder.build();
    }

    @Test
    public void feed_must_contain_at_least_one_author_if_no_entries_with_author() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("author is mandatory");

        Feed.Builder feedBuilder = Feed.builder()
            .withId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6")
            .withTitle("My standard Atom 1.0 feed")
            .withSubtitle("Or is it?")
            .withUpdateDate(new Date(510278400000L))
            .addLink(Link.builder("http://example.org/").withRel(self).build());

        feedBuilder.build();
    }

    @Test
    public void all_entries_must_contain_at_least_one_author_if_no_author_in_feed() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("author is mandatory");

        Feed.Builder feedBuilder = Feed.builder()
            .withId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6")
            .withTitle("My standard Atom 1.0 feed")
            .withSubtitle("Or is it?")
            .withUpdateDate(new Date(510278400000L))
            .withGenerator(Generator.builder("http://example.org/generator").build())
            .addLink(Link.builder("http://example.org/").withRel(self).build());

        Entry.Builder builder = Entry.builder()
            .addLink(Link.builder("http://example.org/2003/12/13/atom03").build())
            .withTitle("Atom is not what you think")
            .withId("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a")
            .withPublishedDate(new Date(223948800000L))
            .withUpdateDate(new Date(512697600000L))
            .withSummary(Summary.builder().withValue("April's fool!").build());

        feedBuilder.addEntry(builder.build());

        builder = Entry.builder()
            .addLink(Link.builder("http://example.org/2004/12/13/atom03").build())
            .withTitle("And what should I think?")
            .withId("urn:uuid:99999999-9999-9999-8999-999999999999")
            .withPublishedDate(new Date(223948800000L))
            .withUpdateDate(new Date(512697600001L))
            .withSummary(Summary.builder().withValue("No jokes please!").build());

        feedBuilder.addEntry(builder.build());

        feedBuilder.build();
    }

    @Test
    public void feed_must_not_contain_more_than_one_link_with_the_same_rel_alternate_and_same_hreflang() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("links must not contain more than one link with the same rel `alternate` and same hreflang");

        Feed.Builder feedBuilder = Feed.builder()
            .withId("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6")
            .withTitle("My standard Atom 1.0 feed")
            .withSubtitle("Or is it?")
            .withUpdateDate(new Date(510278400000L))
            .withAuthor(Author.builder("VIDAL").build())
            .addLink(Link.builder("http://example.org/").withRel(alternate).withHreflang("fr").build())
            .addLink(Link.builder("http://example2.org/").withRel(alternate).withHreflang("es").build())
            .addLink(Link.builder("http://example3.org/").withRel(alternate).withHreflang("fr").build());

        feedBuilder.build();
    }


}

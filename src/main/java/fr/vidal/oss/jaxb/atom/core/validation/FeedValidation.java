package fr.vidal.oss.jaxb.atom.core.validation;

import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.Link;
import fr.vidal.oss.jaxb.atom.core.LinkRel;

import java.util.Collection;
import java.util.HashMap;

public class FeedValidation {

    public static boolean allEntriesContainAuthor(Collection<Entry> entries) {
        if (entries.isEmpty()) {
            return false;
        }
        for (Entry entry : entries) {
            if(!EntryValidation.entryOrSourceContainsAtLeastAnAuthor(entry)) {
                return false;
            }
        }
        return true;
    }

    /**
     * atom:feed elements MUST NOT contain more than one atom:link
     * element with a rel attribute value of "alternate" that has the
     * same combination of type and hreflang attribute values.
     */
    public static boolean noDuplicatedLinks(Collection<Link> links) {
        HashMap<String, Link> alternateLinks = new HashMap<>();
        for (Link link : links) {
            if (link.getRel().equals(LinkRel.alternate)) {
                if (alternateLinks.containsKey(link.getHreflang() + "_" + link.getType())) {
                    return false;
                }
                alternateLinks.put(link.getHreflang() + "_" + link.getType(), link);
            }
        }

        return true;
    }

}

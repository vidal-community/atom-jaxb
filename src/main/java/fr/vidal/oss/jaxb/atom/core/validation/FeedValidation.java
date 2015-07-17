package fr.vidal.oss.jaxb.atom.core.validation;

import fr.vidal.oss.jaxb.atom.core.Entry;

import java.util.Collection;

public class FeedValidation {

    public static boolean allEntriesContainAuthor(Collection<Entry> entries) {
        if (entries.isEmpty()) {
            return false;
        }
        for (Entry entry : entries) {
            if (entry.getAuthor() == null) {
                return false;
            }
        }
        return true;
    }

}

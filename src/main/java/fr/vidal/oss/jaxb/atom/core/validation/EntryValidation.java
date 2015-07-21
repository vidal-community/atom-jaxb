package fr.vidal.oss.jaxb.atom.core.validation;

import fr.vidal.oss.jaxb.atom.core.Entry;

public class EntryValidation {

    public static boolean entryOrSourceContainsAtLeastAnAuthor(Entry entry) {
        if (entry.getAuthors().isEmpty()) {
            if (entry.getSource() == null || entry.getSource().getAuthors().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}

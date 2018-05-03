package fr.vidal.oss.jaxb.atom.core.validation;

import fr.vidal.oss.jaxb.atom.core.ContentType;
import fr.vidal.oss.jaxb.atom.core.Contents;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.Summary;

public class EntryValidation {

    public static boolean entryOrSourceContainsAtLeastAnAuthor(Entry entry) {
        if (entry.getAuthors().isEmpty()) {
            if (entry.getSource() == null || entry.getSource().getAuthors().isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /**
     * atom:entry elements MUST contain an atom:summary element in either
     * of the following cases:
     *  the atom:entry contains an atom:content that has a "src"
     *  attribute (and is thus empty).
     *
     *  the atom:entry contains content that is encoded in Base64;
     *  i.e., the "type" attribute of atom:content is a MIME media type
     *  [MIMEREG], but is not an XML media type [RFC3023], does not
     *  begin with "text/", and does not end with "/xml" or "+xml".
     */
    public static boolean summaryIsMandatory(Contents contents, Summary summary) {
        return !(contents != null
            && (
            (contents.getSrc() != null && summary == null)
                || (contents.getType() != null
                && contents.getType().equals(ContentType.builder("MIMEREG").build())
                && !contents.getType().getType().startsWith("text/")
                && !contents.getType().getType().endsWith("/xml")
                && !contents.getType().getType().endsWith("+xml"))));
    }
}

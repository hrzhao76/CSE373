package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        for (CharSequence term : terms) {
            this.terms.add(term);
        }
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }

        int i = Collections.binarySearch(this.terms, prefix, CharSequence::compare);
        if (i >= 0) {
            for (int j=i; j< this.terms.size(); j++) {
                if (Autocomplete.isPrefixOf(prefix, this.terms.get(j))) {
                    result.add(this.terms.get(j));
                }else {
                    return result;
                }
            }
        }
        return result;
    }
}

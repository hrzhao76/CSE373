package autocomplete;

import java.util.*;

/**
 * {@link TreeSet} implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TreeSetAutocomplete implements Autocomplete {
    /**
     * {@link NavigableSet} of added autocompletion terms.
     */
    private final NavigableSet<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public TreeSetAutocomplete() {
        this.terms = new TreeSet<>(CharSequence::compare);
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
        /* note: this.terms is a NavigableSet

        The java.util.NavigableSet.addAll(Collection C) method is used to append
        all of the elements from the mentioned collection to the existing NavigableSet.
        The elements are added randomly without following any specific order.

        i.e. the orders of terms are random.
         */
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        CharSequence start = terms.ceiling(prefix); // What is the purpose of this line and L46 using tailSet?
        if (start == null) {
            return result;
        }
        for (CharSequence term : terms.tailSet(start)) {
            if (Autocomplete.isPrefixOf(prefix, term)) {
                result.add(term);
            } else {
                return result;
            }
        }
        return result;
    }
}

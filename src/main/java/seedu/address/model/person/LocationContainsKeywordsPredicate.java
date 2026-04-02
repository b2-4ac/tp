package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Location} matches any of the keywords given.
 */
public class LocationContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public LocationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String location = person.getLocation().value;
        String locationLower = location.toLowerCase();

        // String match does not use StringUtil.containsWordIgnoreCase to allow for partial location matches.
        // An empty keyword is the explicit signal to filter persons with empty locations only.
        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(keyword -> keyword.isEmpty() ? location.isEmpty() : !location.isEmpty()
                        && locationLower.contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LocationContainsKeywordsPredicate)) {
            return false;
        }

        LocationContainsKeywordsPredicate otherLocationContainsKeywordsPredicate =
                (LocationContainsKeywordsPredicate) other;
        return keywords.equals(otherLocationContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

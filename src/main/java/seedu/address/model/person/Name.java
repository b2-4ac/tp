package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in PowerRoster.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * A trailing period is allowed (e.g., "Dr.").
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}](?:[\\p{Alnum}' .-]*[\\p{Alnum}.])?";
    private static final String SPECIAL_CHARACTERS = "'-.";
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces and " + SPECIAL_CHARACTERS
                    + " while not being blank.";
    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String normalisedName = normaliseWhitespace(name);
        checkArgument(isValidName(normalisedName), MESSAGE_CONSTRAINTS);
        fullName = normalisedName;
    }

    /**
     * Normalises runs of spaces to a single space and trims leading/trailing spaces.
     */
    private static String normaliseWhitespace(String value) {
        return value.trim().replaceAll(" +", " ");
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a client's preferred gym location in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidLocation(String)}
 */
public class Location {
    public static final String MESSAGE_CONSTRAINTS =
        "Locations can take any alphanumeric input and the @ symbol. "
        + "Leave it blank to indicate no specified location.";

    public static final String EMPTY_LOCATION = "";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * A valid location includes alphanumeric characters and the
     * @ symbol only.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9@][a-zA-Z0-9@\\s]*$";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        value = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        requireNonNull(test);
        return EMPTY_LOCATION.equals(test) || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return value.equals(otherLocation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

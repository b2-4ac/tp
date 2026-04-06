package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a client's preferred gym location in PowerRoster.
 * Guarantees: immutable;
 */
public class Location {
    public static final String MESSAGE_CONSTRAINTS =
        "Locations can take any value. "
        + "Leave it blank to indicate no specified location.";

    public static final String EMPTY_LOCATION = "";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        value = location;
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

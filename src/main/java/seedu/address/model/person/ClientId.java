package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's ID in PowerRoster.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class ClientId {

    public static final String MESSAGE_CONSTRAINTS = "Client ID must follow the Java UUID format";

    /**
     * Client ID follows the Java UUID format
     */
    public static final String VALIDATION_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public final String value;

    /**
     * Constructs a {@code ClientId}.
     *
     * @param id A valid ID.
     */
    public ClientId(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id));
        this.value = id;
    }

    /**
     * Returns true if the given string is a valid ID.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof ClientId)) {
            return false;
        }

        ClientId otherId = (ClientId) other;
        return value.equals(otherId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

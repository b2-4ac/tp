package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    /**
     * Represents the possible values a person's status can take on.
     */
    public enum StatusEnum {
        ACTIVE("Active"),
        INACTIVE("Inactive");

        private final String displayValue;

        StatusEnum(String displayValue) {
            this.displayValue = displayValue;
        }

        @Override
        public String toString() {
            return displayValue;
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Status can only be 'active' or 'inactive' (case-insensitive)";

    public static final String VALIDATION_REGEX = "^(?i)(active|inactive)$";

    public final StatusEnum value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status string.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        if (status.equalsIgnoreCase("active")) {
            this.value = StatusEnum.ACTIVE;
        } else {
            this.value = StatusEnum.INACTIVE;
        }
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

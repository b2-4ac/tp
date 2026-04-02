package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a Person's session rate in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidRate(String)}
 */
public class Rate {

    public static final String MESSAGE_CONSTRAINTS =
            "Rate must be a non-negative amount with up to 2 decimal places "
                    + "(e.g. 120, 120.5, 120.50). No currency symbols allowed.";

    // Accepts: 120, 120., 120.5, 120.50, .50
    // Rejects: -1, 1,000, $100
    public static final String VALIDATION_REGEX = "(?:\\d+(?:\\.\\d{0,2})?|\\.\\d{1,2})";

    public final String value;

    /**
     * Constructs a {@code Rate}.
     *
     * @param rate A valid rate, or an empty string to indicate no rate set.
     */
    public Rate(String rate) {
        requireNonNull(rate);
        String trimmedRate = rate.trim();
        if (trimmedRate.isEmpty()) {
            value = "";
            return;
        }

        checkArgument(isValidRate(trimmedRate), MESSAGE_CONSTRAINTS);
        value = normaliseRate(trimmedRate);
    }

    /**
     * Returns true if a given string is a valid rate.
     */
    public static boolean isValidRate(String test) {
        requireNonNull(test);
        if (test.trim().isEmpty()) {
            return true; // Allow empty string to indicate no rate set and allow testing
        }
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            BigDecimal amount = new BigDecimal(test);
            return amount.signum() >= 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static String normaliseRate(String rate) {
        return new BigDecimal(rate).setScale(2, RoundingMode.UNNECESSARY).toPlainString();
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
        if (!(other instanceof Rate)) {
            return false;
        }
        Rate otherRate = (Rate) other;
        return value.equals(otherRate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

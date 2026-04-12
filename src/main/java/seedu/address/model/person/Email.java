package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in PowerRoster.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS =
            "Email format must be in `local-part@domain` (e.g., `alex@example.com`). \n"
                    + "- Local-part uses letters, digits, or " + SPECIAL_CHARACTERS
                    + " (not at start/end/not consecutively).\n"
            + "- Domain must be made of labels separated by dots. Labels are alphanumeric, may contain "
            + "hyphens (not at start/end and not consecutively).\n"
                    + "- Last label must be at least 2 characters.";
    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE
        + "([" + SPECIAL_CHARACTERS + "]" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LABEL_REGEX = ALPHANUMERIC_NO_UNDERSCORE
        + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_REGEX = "(" + DOMAIN_LABEL_REGEX + "\\.)+" + DOMAIN_LABEL_REGEX + "$";
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        String domain = test.substring(test.indexOf('@') + 1);
        String[] labels = domain.split("\\.");
        String finalLabel = labels[labels.length - 1];
        return finalLabel.length() >= 2;
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

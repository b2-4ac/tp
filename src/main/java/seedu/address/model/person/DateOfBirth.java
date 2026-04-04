package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Person's date of birth in PowerRoster.
 * Guarantees: immutable; is valid as declared in {@link #isValidDob(String)}
 */
public class DateOfBirth {
    public static final String MESSAGE_CONSTRAINTS =
            "Date of Birth must be a valid date in the format DD/MM/YYYY\n"
            + "Date of Birth cannot be in the future or more than 100 years in the past.";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public final LocalDate value;
    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid date of birth string.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDob(dob), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(dob, DateOfBirth.FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date of birth string.
     */
    public static boolean isValidDob(String test) {
        boolean isValidDate = true;
        try {
            LocalDate testDate = LocalDate.parse(test, DateOfBirth.FORMATTER);
            if (testDate.isAfter(LocalDate.now())) {
                isValidDate = false;
            }
            if (testDate.isBefore(LocalDate.now().minusYears(100))) {
                isValidDate = false;
            }
        } catch (DateTimeParseException e) {
            isValidDate = false;
        }
        return isValidDate;
    }

    @Override
    public String toString() {
        return value.format(DateOfBirth.FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateOfBirth)) {
            return false;
        }

        DateOfBirth otherDob = (DateOfBirth) other;
        return value.equals(otherDob.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

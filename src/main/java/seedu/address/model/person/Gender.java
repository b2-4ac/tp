package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in Power Roster.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_CONSTRAINTS = "Gender can take either M or F, and it should not be blank.";

    /*
     * The gender input must contain a single character,
     * either M/m or F/f.
     */
    public static final String VALIDATION_REGEX = "^[mMfF]$";

    public final GenderEnum value;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender character.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        if (gender.toLowerCase().equals("m")) {
            this.value = GenderEnum.M;
        } else {
            this.value = GenderEnum.F;
        }
    }

    /**
     * Returns true if a given string is a valid gender character.
     */
    public static boolean isValidGender(String test) {
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

        // instanceof handles nulls
        if (!(other instanceof Gender)) {
            return false;
        }

        Gender otherGender = (Gender) other;
        return value.equals(otherGender.value);
    }

    @Override
    public int hashCode() {
        return value.toString().hashCode();
    }
}

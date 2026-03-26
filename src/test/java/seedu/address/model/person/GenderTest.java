package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }


    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "b";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid genders
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("b")); // input other than m or f

        // valid genders
        assertTrue(Gender.isValidGender("m"));
        assertTrue(Gender.isValidGender("M"));
        assertTrue(Gender.isValidGender("f"));
        assertTrue(Gender.isValidGender("F"));
    }

    @Test
    public void equals() {
        Gender gender = new Gender("F");

        // same values -> returns true
        assertTrue(gender.equals(new Gender("F")));

        // same values different case -> returns true
        assertTrue(gender.equals(new Gender("f")));

        // same object -> returns true
        assertTrue(gender.equals(gender));

        // null -> returns false
        assertFalse(gender.equals(null));

        // different types -> returns false
        assertFalse(gender.equals(5.0f));

        // different values -> returns false
        assertFalse(gender.equals(new Gender("M")));
    }

}

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
        // EP: null Input
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // EP: Empty Input
        assertFalse(Gender.isValidGender(""));
        assertFalse(Gender.isValidGender(" "));

        // EP: Alphabet other than M or F
        assertFalse(Gender.isValidGender("b"));
        assertFalse(Gender.isValidGender("c"));

        //EP: Male/Female spelt out
        assertFalse(Gender.isValidGender("male"));
        assertFalse(Gender.isValidGender("Male"));
        assertFalse(Gender.isValidGender("female"));
        assertFalse(Gender.isValidGender("Female"));

        // EP: Valid genders
        assertTrue(Gender.isValidGender("m"));
        assertTrue(Gender.isValidGender("M"));
        assertTrue(Gender.isValidGender("f"));
        assertTrue(Gender.isValidGender("F"));
    }

    @Test
    public void equals() {
        Gender gender = new Gender("F");

        // EP: Same gender (case-insensitive)
        assertTrue(gender.equals(new Gender("F")));
        assertTrue(gender.equals(new Gender("f")));

        // EP: Same object
        assertTrue(gender.equals(gender));

        // EP: Null
        assertFalse(gender.equals(null));

        // EP: Different types
        assertFalse(gender.equals(5.0f));

        // EP: Different gender
        assertFalse(gender.equals(new Gender("M")));
        assertFalse(gender.equals(new Gender("m")));
    }

}

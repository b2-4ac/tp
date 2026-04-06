package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BodyFatPercentageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BodyFatPercentage(null));
    }

    @Test
    public void constructor_invalidBodyFatPercentage_throwsIllegalArgumentException() {
        // EP: invalid numeric value below accepted range.
        // BVA: just below lower bound (1.0).
        String invalidBodyFat = "0.9";
        assertThrows(IllegalArgumentException.class, () -> new BodyFatPercentage(invalidBodyFat));
    }

    @Test
    public void isValidBodyFatPercentage() {
        // EP: null input.
        assertThrows(NullPointerException.class, () -> BodyFatPercentage.isValidBodyFatPercentage(null));

        // EP: invalid textual/format inputs.
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage(" ")); // BVA: blank/whitespace-only input
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("18.55")); // EP: precision > 1 decimal place
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("abc")); // EP: non-numeric input
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("20%")); // EP: numeric value with symbol suffix

        // EP: out-of-range numeric inputs.
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("0.9")); // BVA: just below lower bound
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("75.1")); // BVA: just above upper bound

        // EP: valid inputs, including canonical and normalized forms.
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("")); // EP: explicit clear/empty value
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("1.0")); // BVA: lower bound
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("18")); // EP: whole number within range
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("18.")); // EP: trailing dot normalization
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("18.5")); // EP: one decimal place within range
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("75.0")); // BVA: upper bound
    }

    @Test
    public void constructor_validInput_normalisesToOneDecimalPlace() {
        assertEquals("18.0", new BodyFatPercentage("18").value);
        assertEquals("18.0", new BodyFatPercentage("18.").value);
    }

    @Test
    public void equals() {
        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage("18.0");

        // same values -> returns true
        assertTrue(bodyFatPercentage.equals(new BodyFatPercentage("18.0")));

        // same object -> returns true
        assertTrue(bodyFatPercentage.equals(bodyFatPercentage));

        // null -> returns false
        assertFalse(bodyFatPercentage.equals(null));

        // different types -> returns false
        assertFalse(bodyFatPercentage.equals(5.0f));

        // different values -> returns false
        assertFalse(bodyFatPercentage.equals(new BodyFatPercentage("19.0")));
    }
}


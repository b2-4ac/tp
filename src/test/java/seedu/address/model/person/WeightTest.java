package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        // EP: invalid numeric value below accepted range.
        // BVA: just below lower bound (20.0).
        String invalidWeight = "19.9";
        assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    public void isValidWeight() {
        // EP: null input.
        assertThrows(NullPointerException.class, () -> Weight.isValidWeight(null));

        // EP: invalid textual/format inputs.
        assertFalse(Weight.isValidWeight(" ")); // BVA: blank/whitespace-only input
        assertFalse(Weight.isValidWeight("75.55")); // EP: precision > 1 decimal place
        assertFalse(Weight.isValidWeight("abc")); // EP: non-numeric input
        assertFalse(Weight.isValidWeight("70kg")); // EP: numeric value with unit suffix

        // EP: out-of-range numeric inputs.
        assertFalse(Weight.isValidWeight("19.9")); // BVA: just below lower bound
        assertFalse(Weight.isValidWeight("500.1")); // BVA: just above upper bound

        // EP: valid inputs, including canonical and normalized forms.
        assertTrue(Weight.isValidWeight("")); // EP: explicit clear/empty value
        assertTrue(Weight.isValidWeight("20.0")); // BVA: lower bound
        assertTrue(Weight.isValidWeight("72")); // EP: whole number within range
        assertTrue(Weight.isValidWeight("72.")); // EP: trailing dot normalization
        assertTrue(Weight.isValidWeight("72.5")); // EP: one decimal place within range
        assertTrue(Weight.isValidWeight("500.0")); // BVA: upper bound
    }

    @Test
    public void constructor_validInput_normalisesToOneDecimalPlace() {
        assertEquals("72.0", new Weight("72").value);
        assertEquals("72.0", new Weight("72.").value);
    }

    @Test
    public void equals() {
        Weight weight = new Weight("70.0");

        // same values -> returns true
        assertTrue(weight.equals(new Weight("70.0")));

        // same object -> returns true
        assertTrue(weight.equals(weight));

        // null -> returns false
        assertFalse(weight.equals(null));

        // different types -> returns false
        assertFalse(weight.equals(5.0f));

        // different values -> returns false
        assertFalse(weight.equals(new Weight("71.0")));
    }
}


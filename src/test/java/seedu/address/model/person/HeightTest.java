package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void constructor_invalidHeight_throwsIllegalArgumentException() {
        // EP: invalid numeric value below accepted range.
        // BVA: just below lower bound (50.0).
        String invalidHeight = "49.9";
        assertThrows(IllegalArgumentException.class, () -> new Height(invalidHeight));
    }

    @Test
    public void isValidHeight() {
        // EP: null input.
        assertThrows(NullPointerException.class, () -> Height.isValidHeight(null));

        // EP: invalid textual/format inputs.
        assertFalse(Height.isValidHeight(" ")); // BVA: blank/whitespace-only input
        assertFalse(Height.isValidHeight("170.55")); // EP: precision > 1 decimal place
        assertFalse(Height.isValidHeight("abc")); // EP: non-numeric input
        assertFalse(Height.isValidHeight("170cm")); // EP: numeric value with unit suffix

        // EP: out-of-range numeric inputs.
        assertFalse(Height.isValidHeight("49.9")); // BVA: just below lower bound
        assertFalse(Height.isValidHeight("300.1")); // BVA: just above upper bound

        // EP: valid inputs, including canonical and normalized forms.
        assertTrue(Height.isValidHeight("")); // EP: explicit clear/empty value
        assertTrue(Height.isValidHeight("50.0")); // BVA: lower bound
        assertTrue(Height.isValidHeight("170")); // EP: whole number within range
        assertTrue(Height.isValidHeight("170.")); // EP: trailing dot normalization
        assertTrue(Height.isValidHeight("170.5")); // EP: one decimal place within range
        assertTrue(Height.isValidHeight("300.0")); // BVA: upper bound
    }

    @Test
    public void constructor_validInput_normalisesToOneDecimalPlace() {
        assertEquals("170.0", new Height("170").value);
        assertEquals("170.0", new Height("170.").value);
    }

    @Test
    public void equals() {
        Height height = new Height("170.0");

        // same values -> returns true
        assertTrue(height.equals(new Height("170.0")));

        // same object -> returns true
        assertTrue(height.equals(height));

        // null -> returns false
        assertFalse(height.equals(null));

        // different types -> returns false
        assertFalse(height.equals(5.0f));

        // different values -> returns false
        assertFalse(height.equals(new Height("171.0")));
    }
}


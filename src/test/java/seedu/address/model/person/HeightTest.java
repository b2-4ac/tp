package seedu.address.model.person;

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
        String invalidHeight = "49.9";
        assertThrows(IllegalArgumentException.class, () -> new Height(invalidHeight));
    }

    @Test
    public void isValidHeight() {
        // null height
        assertThrows(NullPointerException.class, () -> Height.isValidHeight(null));

        // invalid height
        assertFalse(Height.isValidHeight(" ")); // spaces only
        assertFalse(Height.isValidHeight("49.9")); // below lower bound
        assertFalse(Height.isValidHeight("300.1")); // above upper bound
        assertFalse(Height.isValidHeight("170.55")); // more than 1 decimal place
        assertFalse(Height.isValidHeight("abc")); // non-numeric
        assertFalse(Height.isValidHeight("170cm")); // contains unit suffix

        // valid height
        assertTrue(Height.isValidHeight("")); // default placeholder
        assertTrue(Height.isValidHeight("50.0")); // lower bound
        assertTrue(Height.isValidHeight("170")); // whole number
        assertTrue(Height.isValidHeight("170.5")); // one decimal place
        assertTrue(Height.isValidHeight("300.0")); // upper bound
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


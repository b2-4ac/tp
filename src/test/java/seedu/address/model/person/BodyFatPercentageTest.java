package seedu.address.model.person;

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
        String invalidBodyFat = "0.9";
        assertThrows(IllegalArgumentException.class, () -> new BodyFatPercentage(invalidBodyFat));
    }

    @Test
    public void isValidBodyFatPercentage() {
        // null body fat percentage
        assertThrows(NullPointerException.class, () -> BodyFatPercentage.isValidBodyFatPercentage(null));

        // invalid body fat percentage
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage(" ")); // spaces only
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("0.9")); // below lower bound
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("75.1")); // above upper bound
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("18.55")); // more than 1 decimal place
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("abc")); // non-numeric
        assertFalse(BodyFatPercentage.isValidBodyFatPercentage("20%")); // contains symbol

        // valid body fat percentage
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("")); // default placeholder
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("1.0")); // lower bound
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("18")); // whole number
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("18.5")); // one decimal place
        assertTrue(BodyFatPercentage.isValidBodyFatPercentage("75.0")); // upper bound
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


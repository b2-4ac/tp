package seedu.address.model.person;

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
        String invalidWeight = "19.9";
        assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    public void isValidWeight() {
        // null weight
        assertThrows(NullPointerException.class, () -> Weight.isValidWeight(null));

        // invalid weight
        assertFalse(Weight.isValidWeight(" ")); // spaces only
        assertFalse(Weight.isValidWeight("19.9")); // below lower bound
        assertFalse(Weight.isValidWeight("500.1")); // above upper bound
        assertFalse(Weight.isValidWeight("75.55")); // more than 1 decimal place
        assertFalse(Weight.isValidWeight("abc")); // non-numeric
        assertFalse(Weight.isValidWeight("70kg")); // contains unit suffix

        // valid weight
        assertTrue(Weight.isValidWeight("")); // default placeholder
        assertTrue(Weight.isValidWeight("20.0")); // lower bound
        assertTrue(Weight.isValidWeight("72")); // whole number
        assertTrue(Weight.isValidWeight("72.5")); // one decimal place
        assertTrue(Weight.isValidWeight("500.0")); // upper bound
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


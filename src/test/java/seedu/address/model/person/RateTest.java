package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RateTest {

    @Test
    public void constructor_invalidRate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Rate("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Rate("1,000"));
        assertThrows(IllegalArgumentException.class, () -> new Rate("$100"));
    }

    @Test
    public void constructor_validRate_normalisesToTwoDecimalPlaces() {
        assertTrue(new Rate("120").value.equals("120.00"));
        assertTrue(new Rate("100.").value.equals("100.00"));
        assertTrue(new Rate("120.5").value.equals("120.50"));
        assertTrue(new Rate(".50").value.equals("0.50"));
        assertTrue(new Rate("0").value.equals("0.00"));
    }

    @Test
    public void equals() {
        Rate rate = new Rate("120.5");

        // same object -> returns true
        assertTrue(rate.equals(rate));

        // same values -> returns true
        Rate rateCopy = new Rate("120.50");
        assertTrue(rate.equals(rateCopy));

        // different types -> returns false
        assertFalse(rate.equals(1));

        // null -> returns false
        assertFalse(rate.equals(null));

        // different rate -> returns false
        Rate differentRate = new Rate("80.00");
        assertFalse(rate.equals(differentRate));
    }
}

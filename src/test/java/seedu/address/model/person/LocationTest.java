package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = ":";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void constructor_emptyLocation_success() {
        assertTrue(new Location("").value.isEmpty());
    }

    @Test
    public void isValidLocation() {
        // null Location
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid Locations
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation(":")); // invalid special character

        // valid Locations
        assertTrue(Location.isValidLocation("")); // empty string
        assertTrue(Location.isValidLocation("Anytime Fitness Marine Parade"));
        assertTrue(Location.isValidLocation("B")); // one character
        assertTrue(Location.isValidLocation("ActiveSG Gym @ Fernvale Square")); // contains @ symbol
    }

    @Test
    public void equals() {
        Location location = new Location("Valid Location");

        // same values -> returns true
        assertTrue(location.equals(new Location("Valid Location")));

        // same object -> returns true
        assertTrue(location.equals(location));

        // null -> returns false
        assertFalse(location.equals(null));

        // different types -> returns false
        assertFalse(location.equals(5.0f));

        // different values -> returns false
        assertFalse(location.equals(new Location("Other Valid Location")));
    }
}

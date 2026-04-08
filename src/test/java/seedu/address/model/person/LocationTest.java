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
    public void constructor_emptyLocation_success() {
        assertTrue(new Location("").value.isEmpty());
    }

    @Test
    public void equals() {
        Location location = new Location("Valid Location");

        // EP: Same location
        assertTrue(location.equals(new Location("Valid Location")));

        // EP: Same object
        assertTrue(location.equals(location));

        // EP: null Input
        assertFalse(location.equals(null));

        // EP: Different types
        assertFalse(location.equals(5.0f));

        // EP: Different Location
        assertFalse(location.equals(new Location("Other Valid Location")));
    }
}

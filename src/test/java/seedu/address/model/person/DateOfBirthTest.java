package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsIllegalArgumentException() {
        String emptyDateOfBirth = "";
        String invalidDateOfBirth = "abc";
        String wrongFormatDateOfBirth = "24-12-2002";

        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(emptyDateOfBirth));
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDateOfBirth));
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(wrongFormatDateOfBirth));
    }

    @Test
    public void isValidDob() {
        // null Date of Birth
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDob(null));

        // invalid Date of Birth
        assertFalse(DateOfBirth.isValidDob("")); // empty string
        assertFalse(DateOfBirth.isValidDob("abc")); // alphabets
        assertFalse(DateOfBirth.isValidDob("24-12-2002")); // wrong format
        assertFalse(DateOfBirth.isValidDob("24/15/2002")); // invalid month
        assertFalse(DateOfBirth.isValidDob("00/12/2002")); // invalid day
        assertFalse(DateOfBirth.isValidDob(LocalDateTime
                .now()
                .plusYears(1)
                .format(DateOfBirth.FORMATTER))); // in the future

        // valid Date of Birth
        assertTrue(DateOfBirth.isValidDob("24/04/1987"));
    }

    @Test
    public void equals() {
        DateOfBirth dateOfBirth = new DateOfBirth("16/07/1999");

        // same values -> returns true
        assertTrue(dateOfBirth.equals(new DateOfBirth("16/07/1999")));

        // same object -> returns true
        assertTrue(dateOfBirth.equals(dateOfBirth));

        // null -> returns false
        assertFalse(dateOfBirth.equals(null));

        // different types -> returns false
        assertFalse(dateOfBirth.equals(5.0f));

        // different values -> returns false
        assertFalse(dateOfBirth.equals(new DateOfBirth("24/04/1987")));
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

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

        // Invalid Month
        assertFalse(DateOfBirth.isValidDob("24/00/2002"));
        assertFalse(DateOfBirth.isValidDob("24/13/2002"));

        // Invalid Day
        assertFalse(DateOfBirth.isValidDob("00/12/2002"));
        assertFalse(DateOfBirth.isValidDob("32/12/2002"));

        // Non-existent Dates
        assertFalse(DateOfBirth.isValidDob("29/02/2002")); // not a leap year
        assertFalse(DateOfBirth.isValidDob("31/04/2002"));

        // Future Dates (Invalid)
        assertFalse(DateOfBirth.isValidDob(LocalDate
                .now()
                .plusYears(1)
                .format(DateOfBirth.FORMATTER)));

        // Dates more than 100 years in the past (Invalid)
        assertFalse(DateOfBirth.isValidDob(LocalDate.now()
                .minusYears(100)
                .minusDays(1)
                .format(DateOfBirth.FORMATTER)));
        assertFalse(DateOfBirth.isValidDob("01/01/1000"));

        // valid Date of Birth
        assertTrue(DateOfBirth.isValidDob(LocalDate.now()
                .minusYears(100)
                .format(DateOfBirth.FORMATTER)));
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

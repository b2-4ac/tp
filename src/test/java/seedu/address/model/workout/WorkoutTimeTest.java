package seedu.address.model.workout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class WorkoutTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WorkoutTime(null));
    }

    @Test
    public void constructor_invalidWorkoutTime_throwsIllegalArgumentException() {
        String emptyWorkoutTime = "";
        String invalidWorkoutTime = "abc";
        String wrongFormatWorkoutTime = "24-12-2002 1400";

        assertThrows(IllegalArgumentException.class, () -> new WorkoutTime(emptyWorkoutTime));
        assertThrows(IllegalArgumentException.class, () -> new WorkoutTime(invalidWorkoutTime));
        assertThrows(IllegalArgumentException.class, () -> new WorkoutTime(wrongFormatWorkoutTime));
    }

    @Test
    public void isValidTime() {
        // null Workout Time
        assertThrows(NullPointerException.class, () -> WorkoutTime.isValidTime(null));

        // invalid Workout Time
        assertFalse(WorkoutTime.isValidTime("")); // empty string
        assertFalse(WorkoutTime.isValidTime("abc")); // alphabets
        assertFalse(WorkoutTime.isValidTime("24-12-2002 1400")); // wrong format

        // Invalid Month
        assertFalse(WorkoutTime.isValidTime("24/00/2002 14:00"));
        assertFalse(WorkoutTime.isValidTime("24/13/2002 14:00"));

        // Invalid Day
        assertFalse(WorkoutTime.isValidTime("00/12/2002 14:00"));

        // Non-existent Date
        assertFalse(WorkoutTime.isValidTime("29/02/2025 14:00")); //not a leap year
        assertFalse(WorkoutTime.isValidTime("31/04/2025 14:00"));

        // Invalid Hour
        assertFalse(WorkoutTime.isValidTime("25/04/2025 25:00"));

        // Invalid Minute
        assertFalse(WorkoutTime.isValidTime("25/04/2025 14:60"));

        // Future Dates (Invalid)
        assertFalse(WorkoutTime.isValidTime(LocalDateTime.now()
                .plusYears(1)
                .format(WorkoutTime.FORMATTER)));

        // Dates more than 50 years older (Invalid)
        assertFalse(WorkoutTime.isValidTime(LocalDateTime.now()
                .minusYears(50)
                .minusSeconds(1)
                .format(WorkoutTime.FORMATTER)));

        // Date less than 50 years old (Valid)
        assertTrue(WorkoutTime.isValidTime(LocalDateTime.now()
                .minusYears(50)
                .plusMinutes(1)
                .format(WorkoutTime.FORMATTER)));

        // Valid Leap Year
        assertTrue(WorkoutTime.isValidTime("29/02/2024 14:00"));

        // valid Workout Time
        assertTrue(WorkoutTime.isValidTime("24/04/1987 14:00"));
    }

    @Test
    public void equals() {
        WorkoutTime workoutTime = new WorkoutTime("01/04/2026 13:00");

        // same values -> returns true
        assertTrue(workoutTime.equals(new WorkoutTime("01/04/2026 13:00")));

        // same object -> returns true
        assertTrue(workoutTime.equals(workoutTime));

        // null -> returns false
        assertFalse(workoutTime.equals(null));

        // different types -> returns false
        assertFalse(workoutTime.equals(5.0f));

        // different values -> returns false
        assertFalse(workoutTime.equals(new WorkoutTime("02/04/2026 13:00")));
    }
}

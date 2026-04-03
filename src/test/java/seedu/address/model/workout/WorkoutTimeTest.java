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
        assertFalse(WorkoutTime.isValidTime("24/15/2002 14:00")); // invalid month
        assertFalse(WorkoutTime.isValidTime("00/12/2002 14:00")); // invalid day
        assertFalse(WorkoutTime.isValidTime(LocalDateTime.now()
                .plusYears(1)
                .format(WorkoutTime.FORMATTER))); // in the future
        assertFalse(WorkoutTime.isValidTime(LocalDateTime.now()
                .minusYears(50)
                .format(WorkoutTime.FORMATTER))); // too far in the past

        // valid Workout Time
        assertTrue(WorkoutTime.isValidTime("24/04/1987 14:00"));
        assertTrue(WorkoutTime.isValidTime(LocalDateTime.now()
                .minusYears(50)
                .plusDays(1)
                .format(WorkoutTime.FORMATTER)));
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

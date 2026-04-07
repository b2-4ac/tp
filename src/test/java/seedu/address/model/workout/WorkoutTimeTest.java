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
        // EP: null Input
        assertThrows(NullPointerException.class, () -> WorkoutTime.isValidTime(null));

        // EP: Invalid Formats
        assertFalse(WorkoutTime.isValidTime("")); // empty string
        assertFalse(WorkoutTime.isValidTime("abc")); // alphabets
        assertFalse(WorkoutTime.isValidTime("24-12-2002 1400")); // wrong format

        // EP: Invalid Month
        assertFalse(WorkoutTime.isValidTime("24/00/2002 14:00"));
        assertFalse(WorkoutTime.isValidTime("24/13/2002 14:00"));

        // EP: Invalid Day
        assertFalse(WorkoutTime.isValidTime("00/12/2002 14:00"));
        assertFalse(WorkoutTime.isValidTime("32/12/2002 14:00"));

        // EP: Non-existent Dates
        assertFalse(WorkoutTime.isValidTime("29/02/2025 14:00")); //not a leap year
        assertFalse(WorkoutTime.isValidTime("31/04/2025 14:00"));

        // EP: Invalid Hour
        assertFalse(WorkoutTime.isValidTime("25/04/2025 25:00"));

        // EP: Invalid Minute
        assertFalse(WorkoutTime.isValidTime("25/04/2025 14:60"));

        // EP: Future Dates
        assertFalse(WorkoutTime.isValidTime(LocalDateTime.now()
                .plusYears(1)
                .format(WorkoutTime.FORMATTER)));

        // EP: Dates more than 50 years in the past
        assertFalse(WorkoutTime.isValidTime(LocalDateTime.now()
                .minusYears(50)
                .minusSeconds(1)
                .format(WorkoutTime.FORMATTER)));

        // EP: Dates less than 50 years old
        assertTrue(WorkoutTime.isValidTime(LocalDateTime.now()
                .minusYears(50)
                .plusMinutes(1)
                .format(WorkoutTime.FORMATTER)));

        // EP: Valid Leap Year
        assertTrue(WorkoutTime.isValidTime("29/02/2024 14:00"));

        // EP: Valid Workout Time
        assertTrue(WorkoutTime.isValidTime("24/04/1987 14:00"));
        assertTrue(WorkoutTime.isValidTime(LocalDateTime.now()
                .format(WorkoutTime.FORMATTER)));
    }

    @Test
    public void equals() {
        // Record current time
        LocalDateTime timeNow = LocalDateTime.now();
        String timeNowString = timeNow.format(WorkoutTime.FORMATTER);
        WorkoutTime workoutTime = new WorkoutTime(timeNowString);

        // EP: Same date and time
        assertTrue(workoutTime.equals(new WorkoutTime(timeNowString)));

        // EP: Same object
        assertTrue(workoutTime.equals(workoutTime));

        // EP: null Input
        assertFalse(workoutTime.equals(null));

        // EP: Different types
        assertFalse(workoutTime.equals(5.0f));

        // EP: Different date
        assertFalse(workoutTime.equals(new WorkoutTime(timeNow
                .minusDays(1)
                .format(WorkoutTime.FORMATTER))));
        assertFalse(workoutTime.equals(new WorkoutTime(timeNow
                .minusMonths(1)
                .format(WorkoutTime.FORMATTER))));

        // EP: Different time
        assertFalse(workoutTime.equals(new WorkoutTime(timeNow
                .minusHours(1)
                .format(WorkoutTime.FORMATTER))));
        assertFalse(workoutTime.equals(new WorkoutTime(timeNow
                .minusMinutes(1)
                .format(WorkoutTime.FORMATTER))));
    }
}

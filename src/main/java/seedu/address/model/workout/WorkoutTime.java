package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents the time when a Workout took place.
 * Guarantees: immutable
 */
public class WorkoutTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Workout Time must be a valid date in the format: dd/MM/yyyy HH:mm\n"
            + "Workout Time cannot be in the future or more than 50 years in the past.";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private final LocalDateTime value;

    /**
     * Constructs a {@code WorkoutTime}
     *
     * @param time A valid datetime String.
     */
    public WorkoutTime(String time) {
        requireNonNull(time);
        if (isValidTime(time)) {
            this.value = LocalDateTime.parse(time, FORMATTER);
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given String is a valid datetime.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalDateTime parsed = LocalDateTime.parse(test, FORMATTER);
            if (parsed.isAfter(LocalDateTime.now())) {
                return false;
            }
            LocalDateTime threshold = LocalDateTime.now().minusYears(50);
            if (parsed.isBefore(threshold)) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalDateTime getTime() {
        return value;
    }

    /**
     * Returns true if this {@code WorkoutTime} occurs after another
     * given {@code WorkoutTime}
     *
     * @param otherTime Other time to compare to
     * @return True if this time is after given time, False otherwise
     */
    public boolean isAfter(WorkoutTime otherTime) {
        return value.isAfter(otherTime.value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof WorkoutTime)) {
            return false;
        }

        WorkoutTime otherTime = (WorkoutTime) other;
        return value.equals(otherTime.value);
    }

    @Override
    public String toString() {
        return value.format(FORMATTER);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

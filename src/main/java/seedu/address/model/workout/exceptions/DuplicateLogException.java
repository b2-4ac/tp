package seedu.address.model.workout.exceptions;

/**
 * Signals that the operation will result in duplicate Logs
 * Workout Logs are duplicates if they are linked to the same client
 * and occur at the same time.
 */
public class DuplicateLogException extends RuntimeException {
    public DuplicateLogException() {
        super("Operation would result in duplicate workout logs");
    }
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.workout.WorkoutLog;

/**
 * Wraps all data at the Workout Log Book level
 * Duplicates are not allowed
 */
public class WorkoutLogBook {

    private List<WorkoutLog> logs;

    public WorkoutLogBook() {
        this.logs = new ArrayList<>();
    }

    /**
     * Copies the logs from an existing WorkoutLogBook.
     */
    public WorkoutLogBook(WorkoutLogBook toBeCopied) {
        this.logs = toBeCopied.logs;
    }

    public List<WorkoutLog> getLogList() {
        return logs;
    }

    /**
     * Returns true if the current Log is already present in memory.
     *
     * @param log WorkoutLog object to check
     * @return True if the object exists, false otherwise
     */
    public boolean hasLog(WorkoutLog log) {
        requireNonNull(log);

        return logs.contains(log);
    }

    /**
     * Adds the specified WorkoutLog object into the current List.
     *
     * @param log WorkoutLog object to add
     */
    public void addLog(WorkoutLog log) {
        requireNonNull(log);

        logs.add(log);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof WorkoutLogBook)) {
            return false;
        }

        WorkoutLogBook otherLogBook = (WorkoutLogBook) other;
        return this.logs.equals(otherLogBook.logs);
    }

}

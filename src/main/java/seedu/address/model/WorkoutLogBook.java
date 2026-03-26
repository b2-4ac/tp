package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;
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
        this.logs = new ArrayList<>(toBeCopied.logs);
    }

    public List<WorkoutLog> getLogList() {
        return new ArrayList<>(logs);
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

    /**
     * Finds and returns all {@code WorkoutLog} objects belonging to the
     * specified person.
     *
     * @param person Person whose logs to search for
     * @return List of {@code WorkoutLog} that belongs to that person
     */
    public List<WorkoutLog> fetchLogs(Person person) {
        return logs.stream()
                .filter(log -> log.getTrainee().equals(person.getId()))
                .toList();
    }

    /**
     * Returns the most recent {@code WorkoutLog} for the specified
     * {@code Person}.
     *
     * @param person Person whose logs to search for
     * @return Most recent {@code WorkoutLog} object, null if no logs found
     */
    public WorkoutLog lastLog(Person person) {
        List<WorkoutLog> personLogs = fetchLogs(person);
        if (personLogs.size() == 0) {
            return null;
        }
        WorkoutLog latest = personLogs.get(0);
        for (WorkoutLog log : personLogs) {
            if (log.getTime().isAfter(latest.getTime())) {
                latest = log;
            }
        }
        return latest;
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

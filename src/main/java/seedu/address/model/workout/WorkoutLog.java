package seedu.address.model.workout;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.Location;

/**
 * Represents a singular Workout Session.
 * Guarantees: immutable
 */
public class WorkoutLog {

    private final ClientId traineeId;
    private final WorkoutTime time;
    private final Location location;

    /**
     * Constructs a WorkoutLog object. All fields must be non-null.
     *
     * @param trainee Person that performed the workout
     * @param time Time during which the workout took place
     * @param location Location of the Gym at which the workout took place
     */
    public WorkoutLog(ClientId traineeId, WorkoutTime time, Location location) {
        requireAllNonNull(traineeId, time, location);
        this.traineeId = traineeId;
        this.time = time;
        this.location = location;
    }

    /**
     * Returns the Person that did the workout
     */
    public ClientId getTrainee() {
        return traineeId;
    }

    /**
     * Returns the gym location where the workout took place
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the time during which the workout took place
     */
    public WorkoutTime getTime() {
        return time;
    }

    /**
     * Returns true if both logs are linked to the same client and occur at
     * the same time.
     * This defines a weaker notion of equality between two workout logs.
     */
    public boolean isSameLog(WorkoutLog otherLog) {
        if (otherLog == this) {
            return true;
        }

        return otherLog != null
                && traineeId.equals(otherLog.traineeId)
                && time.equals(otherLog.time);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WorkoutLog)) {
            return false;
        }

        WorkoutLog otherLog = (WorkoutLog) other;
        return traineeId.equals(otherLog.traineeId)
                && location.equals(otherLog.location)
                && time.equals(otherLog.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traineeId, location, time);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("traineeId", traineeId)
                .add("location", location)
                .add("time", time)
                .toString();
    }

}

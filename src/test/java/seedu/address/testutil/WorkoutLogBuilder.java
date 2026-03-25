package seedu.address.testutil;

import seedu.address.model.person.ClientId;
import seedu.address.model.person.Location;
import seedu.address.model.workout.WorkoutLog;
import seedu.address.model.workout.WorkoutTime;

/**
 * A utility class to help with building WorkoutLog objects.
 */
public class WorkoutLogBuilder {

    public static final String DEFAULT_ID = "f0962b9a-41d3-4627-897b-944a195b2173";
    public static final String DEFAULT_TIME = "19/12/2025 15:00";
    public static final String DEFAULT_LOCATION = "ActiveSG Gym @ Fernvale Square";

    private ClientId traineeId;
    private WorkoutTime time;
    private Location location;

    /**
     * Creates a {@code WorkoutLogBuilder} with the default details.
     */
    public WorkoutLogBuilder() {
        this.traineeId = new ClientId(DEFAULT_ID);
        this.time = new WorkoutTime(DEFAULT_TIME);
        this.location = new Location(DEFAULT_LOCATION);
    }

    /**
     * Initializes the WorkoutLogBuilder with the data of {@code logToCopy}.
     */
    public WorkoutLogBuilder(WorkoutLog logToCopy) {
        this.traineeId = logToCopy.getTrainee();
        this.time = logToCopy.getTime();
        this.location = logToCopy.getLocation();
    }

    /**
     * Sets the {@code ClientId} of the {@code WorkoutLog} that we are building.
     */
    public WorkoutLogBuilder withId(String traineeId) {
        this.traineeId = new ClientId(traineeId);
        return this;
    }

    /**
     * Sets the {@code WorkoutTime} of the {@code WorkoutLog} that we are building.
     */
    public WorkoutLogBuilder withTime(String time) {
        this.time = new WorkoutTime(time);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code WorkoutLog} that we are building.
     */
    public WorkoutLogBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    public WorkoutLog build() {
        return new WorkoutLog(traineeId, time, location);
    }

}

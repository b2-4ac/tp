package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.Location;
import seedu.address.model.workout.WorkoutLog;
import seedu.address.model.workout.WorkoutTime;

/**
 * Jackson-friendly version of {@link WorkoutLog}.
 */
public class JsonAdaptedWorkoutLog {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Log %s field is missing!";

    private final String traineeId;
    private final String time;
    private final String location;

    /**
     * Constructs a {@code JsonAdaptedWorkoutLog} with the given log details.
     */
    @JsonCreator
    public JsonAdaptedWorkoutLog(@JsonProperty("traineeId") String traineeId,
            @JsonProperty("time") String time,
            @JsonProperty("location") String location) {
        this.traineeId = traineeId;
        this.time = time;
        this.location = location;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedWorkoutLog(WorkoutLog source) {
        traineeId = source.getTrainee().value;
        time = source.getTime().toString();
        location = source.getLocation().value;
    }

    /**
     * Converts this Jackson-friendly adapted workout log object into the model's
     * {@code WorkoutLog} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log.
     */
    public WorkoutLog toModelType() throws IllegalValueException {
        if (traineeId == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT,
                    ClientId.class.getSimpleName()));
        }
        if (!ClientId.isValidId(traineeId)) {
            throw new IllegalValueException(ClientId.MESSAGE_CONSTRAINTS);
        }
        final ClientId modelTraineeId = new ClientId(traineeId);

        if (time == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT,
                    WorkoutTime.class.getSimpleName()));
        }
        if (!WorkoutTime.isValidTime(time)) {
            throw new IllegalValueException(WorkoutTime.MESSAGE_CONSTRAINTS);
        }
        final WorkoutTime modelTime = new WorkoutTime(time);

        if (location == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        final Location modelLocation = new Location(location);

        return new WorkoutLog(modelTraineeId, modelTime, modelLocation);
    }

}

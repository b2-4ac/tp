package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WorkoutLogBook;
import seedu.address.model.workout.WorkoutLog;

/**
 * An Immutable WorkoutLogBook that is serializable to JSON format.
 */
@JsonRootName(value = "workoutlogbook")
public class JsonSerializableWorkoutLogBook {

    public static final String MESSAGE_DUPLICATE_LOG = "Log Book contains duplicate log(s).";

    private final List<JsonAdaptedWorkoutLog> logs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWorkoutLogBook} with the given logs.
     */
    @JsonCreator
    public JsonSerializableWorkoutLogBook(@JsonProperty("logs") List<JsonAdaptedWorkoutLog> logs) {
        this.logs.addAll(logs);
    }

    /**
     * Converts a given {@code ReadOnlyWorkoutLogBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWorkoutLogBook}.
     */
    public JsonSerializableWorkoutLogBook(WorkoutLogBook source) {
        logs.addAll(source.getLogList().stream().map(JsonAdaptedWorkoutLog::new).collect(Collectors.toList()));
    }

    /**
     * Converts this workout log book into the model's {@code WorkoutLogBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WorkoutLogBook toModelType() throws IllegalValueException {
        WorkoutLogBook workoutLogBook = new WorkoutLogBook();
        for (JsonAdaptedWorkoutLog jsonAdaptedWorkoutLog : logs) {
            WorkoutLog log = jsonAdaptedWorkoutLog.toModelType();
            if (workoutLogBook.hasLog(log)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LOG);
            }
            workoutLogBook.addLog(log);
        }
        return workoutLogBook;
    }

}

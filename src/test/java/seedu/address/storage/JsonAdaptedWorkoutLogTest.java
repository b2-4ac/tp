package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedWorkoutLog.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWorkoutLogs.BENSON_LOG_1;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.Location;
import seedu.address.model.workout.WorkoutTime;

public class JsonAdaptedWorkoutLogTest {

    private static final String INVALID_ID = "345hjj5-345-GFNJ";
    private static final String INVALID_TIME = "23-01-25 0900";

    private static final String VALID_ID = BENSON_LOG_1.getTrainee().toString();
    private static final String VALID_TIME = BENSON_LOG_1.getTime().toString();
    private static final String VALID_LOCATION = BENSON_LOG_1.getLocation().toString();

    @Test
    public void toModelType_validLogDetails_returnsWorkoutLog() throws Exception {
        JsonAdaptedWorkoutLog log = new JsonAdaptedWorkoutLog(BENSON_LOG_1);
        assertEquals(BENSON_LOG_1, log.toModelType());
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedWorkoutLog log = new JsonAdaptedWorkoutLog(
                INVALID_ID,
                VALID_TIME,
                VALID_LOCATION);
        String expectedMessage = ClientId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedWorkoutLog log = new JsonAdaptedWorkoutLog(
                null,
                VALID_TIME,
                VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClientId.class.getSimpleName());;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }
    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedWorkoutLog log = new JsonAdaptedWorkoutLog(
                VALID_ID,
                INVALID_TIME,
                VALID_LOCATION);
        String expectedMessage = WorkoutTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedWorkoutLog log = new JsonAdaptedWorkoutLog(
                VALID_ID,
                null,
                VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WorkoutTime.class.getSimpleName());;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedWorkoutLog log = new JsonAdaptedWorkoutLog(
                VALID_ID,
                VALID_TIME,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }
}

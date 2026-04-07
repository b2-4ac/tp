package seedu.address.model.workout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalWorkoutLogs.ALICE_LOG_1;
import static seedu.address.testutil.TypicalWorkoutLogs.BENSON_LOG_1;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.ClientId;
import seedu.address.model.person.Location;
import seedu.address.testutil.WorkoutLogBuilder;

public class WorkoutLogTest {
    @Test
    public void isSameLog() {
        // EP: Same object
        assertTrue(ALICE_LOG_1.isSameLog(ALICE_LOG_1));

        // EP: null Input
        assertFalse(ALICE_LOG_1.isSameLog(null));

        // EP: Same values
        ClientId aliceId = ALICE_LOG_1.getTrainee();
        WorkoutTime aliceTime = ALICE_LOG_1.getTime();
        Location aliceLocation = ALICE_LOG_1.getLocation();
        WorkoutLog sameValuesLog = new WorkoutLogBuilder()
                .withId(aliceId.toString())
                .withTime(aliceTime.toString())
                .withLocation(aliceLocation.toString())
                .build();
        assertTrue(ALICE_LOG_1.isSameLog(sameValuesLog));

        // EP: Different ClientID only
        ClientId bensonId = BENSON_LOG_1.getTrainee();
        WorkoutLog differentIdLog = new WorkoutLogBuilder(ALICE_LOG_1)
                .withId(bensonId.toString())
                .build();
        assertFalse(ALICE_LOG_1.isSameLog(differentIdLog));

        // EP: Different Time only
        WorkoutTime bensonTime = BENSON_LOG_1.getTime();
        WorkoutLog differentTimeLog = new WorkoutLogBuilder(ALICE_LOG_1)
                .withTime(bensonTime.toString())
                .build();
        assertFalse(ALICE_LOG_1.isSameLog(differentTimeLog));

        // EP: Different Location only
        Location bensonLocation = BENSON_LOG_1.getLocation();
        WorkoutLog differentLocationLog = new WorkoutLogBuilder(ALICE_LOG_1)
                .withLocation(bensonLocation.toString())
                .build();
        assertTrue(ALICE_LOG_1.isSameLog(differentLocationLog));
    }
}

package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.WorkoutLogBook;
import seedu.address.model.workout.WorkoutLog;

/**
 * A utility class containing a list of {@code WorkoutLog} objects to be used in
 * tests.
 */
public class TypicalWorkoutLogs {

    public static final WorkoutLog ALICE_LOG_1 = new WorkoutLogBuilder()
            .withId("4f63c6d5-a3d1-4e78-bc4a-bf60a92f8087")
            .withTime("24/03/2026 14:26")
            .withLocation("Jurong West ActiveSG Gym")
            .build();

    public static final WorkoutLog ALICE_LOG_2 = new WorkoutLogBuilder()
            .withId("4f63c6d5-a3d1-4e78-bc4a-bf60a92f8087")
            .withTime("25/03/2026 09:45")
            .withLocation("Clementi ActiveSG Gym")
            .build();

    public static final WorkoutLog BENSON_LOG_1 = new WorkoutLogBuilder()
            .withId("524f0c90-efc4-4c6e-8270-e5550a26d701")
            .withTime("19/03/2026 18:01")
            .withLocation("Clementi ActiveSG Gym")
            .build();

    private TypicalWorkoutLogs() {} //prevents instantiation

    public static WorkoutLogBook getTypicalWorkoutLogBook() {
        WorkoutLogBook wlb = new WorkoutLogBook();
        for (WorkoutLog wl : getTypicalLogs()) {
            wlb.addLog(wl);
        }
        return wlb;
    }

    public static List<WorkoutLog> getTypicalLogs() {
        return new ArrayList<>(Arrays.asList(ALICE_LOG_1, ALICE_LOG_2, BENSON_LOG_1));
    }
}

package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalWorkoutLogs.ALICE_LOG_1;
import static seedu.address.testutil.TypicalWorkoutLogs.BENSON_LOG_1;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class WorkoutLogBookTest {

    private final WorkoutLogBook workoutLogBook = new WorkoutLogBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), workoutLogBook.getLogList());
    }

    @Test
    public void hasLog_nullLog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> workoutLogBook.hasLog(null));
    }

    @Test
    public void hasLog_logNotInLogBook_returnsFalse() {
        assertFalse(workoutLogBook.hasLog(ALICE_LOG_1));
    }

    @Test
    public void hasLog_logInLogBook_returnsTrue() {
        workoutLogBook.addLog(ALICE_LOG_1);
        assertTrue(workoutLogBook.hasLog(ALICE_LOG_1));
    }

    @Test
    public void resetLogs_logsInLogBook_success() {
        workoutLogBook.addLog(BENSON_LOG_1);
        workoutLogBook.resetLogs();
        assertTrue(workoutLogBook.getLogList().isEmpty());
    }
}

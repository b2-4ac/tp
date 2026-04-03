package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWorkoutLogs.ALICE_LOG_1;
import static seedu.address.testutil.TypicalWorkoutLogs.ALICE_LOG_2;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutLogBook;

public class LogCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, ALICE_LOG_1.getTime(), ALICE_LOG_1.getLocation());

        String expectedMessage = String.format(LogCommand.MESSAGE_LOG_WORKOUT_SUCCESS,
                ALICE.getName(),
                ALICE_LOG_1.getTime(),
                ALICE_LOG_1.getLocation());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new WorkoutLogBook());
        expectedModel.addLog(ALICE_LOG_1);

        assertCommandSuccess(logCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LogCommand logCommand = new LogCommand(outOfBoundIndex, ALICE_LOG_1.getTime(), ALICE_LOG_1.getLocation());

        assertCommandFailure(logCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateLogs_throwsCommandException() {
        ModelManager testModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new WorkoutLogBook());
        testModel.addLog(ALICE_LOG_1);

        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, ALICE_LOG_1.getTime(), ALICE_LOG_1.getLocation());

        assertThrows(CommandException.class, LogCommand.MESSAGE_DUPLICATE_LOG, () -> logCommand.execute(testModel));
    }

    @Test
    public void equals() {
        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, ALICE_LOG_1.getTime(),
                ALICE_LOG_1.getLocation());

        // same object -> returns true
        assertTrue(logCommand.equals(logCommand));

        // same values -> returns true
        LogCommand logCommandCopy = new LogCommand(INDEX_FIRST_PERSON, ALICE_LOG_1.getTime(),
                ALICE_LOG_1.getLocation());
        assertTrue(logCommand.equals(logCommandCopy));

        // different types -> returns false
        assertFalse(logCommand.equals(1));

        // null -> returns false
        assertFalse(logCommand.equals(null));

        // different index -> returns false
        LogCommand diffIndexLogCommand = new LogCommand(INDEX_SECOND_PERSON, ALICE_LOG_1.getTime(),
                ALICE_LOG_1.getLocation());
        assertFalse(logCommand.equals(diffIndexLogCommand));

        //different time -> returns false
        LogCommand diffTimeLogCommand = new LogCommand(INDEX_FIRST_PERSON, ALICE_LOG_2.getTime(),
                ALICE_LOG_1.getLocation());
        assertFalse(logCommand.equals(diffTimeLogCommand));

        // different location -> returns false
        LogCommand diffLocationLogCommand = new LogCommand(INDEX_FIRST_PERSON, ALICE_LOG_1.getTime(),
                ALICE_LOG_2.getLocation());
        assertFalse(logCommand.equals(diffLocationLogCommand));
    }
}

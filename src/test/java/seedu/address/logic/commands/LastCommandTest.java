package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWorkoutLogs.ALICE_LOG_2;
import static seedu.address.testutil.TypicalWorkoutLogs.BENSON_LOG_1;
import static seedu.address.testutil.TypicalWorkoutLogs.getTypicalWorkoutLogBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class LastCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWorkoutLogBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToCheck = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LastCommand lastCommand = new LastCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(LastCommand.MESSAGE_RETRIEVE_LOG_SUCCESS,
                personToCheck.getName(),
                ALICE_LOG_2.getTime(),
                ALICE_LOG_2.getLocation());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                model.getWorkoutLogBook());

        assertCommandSuccess(lastCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexNoLogs_noLogsMessage() {
        Person personToCheck = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        LastCommand lastCommand = new LastCommand(INDEX_THIRD_PERSON);

        String expectedMessage = String.format(LastCommand.MESSAGE_NO_LOGS_FOUND_FAILURE,
                personToCheck.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                model.getWorkoutLogBook());

        assertCommandSuccess(lastCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LastCommand lastCommand = new LastCommand(outOfBoundIndex);

        assertCommandFailure(lastCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person personToCheck = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LastCommand lastCommand = new LastCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(LastCommand.MESSAGE_RETRIEVE_LOG_SUCCESS,
                personToCheck.getName(),
                BENSON_LOG_1.getTime(),
                BENSON_LOG_1.getLocation());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                model.getWorkoutLogBook());
        showPersonAtIndex(expectedModel, INDEX_SECOND_PERSON);

        assertCommandSuccess(lastCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LastCommand lastCommand = new LastCommand(outOfBoundIndex);

        assertCommandFailure(lastCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LastCommand lastFirstCommand = new LastCommand(INDEX_FIRST_PERSON);
        LastCommand lastSecondCommand = new LastCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(lastFirstCommand.equals(lastFirstCommand));

        // same values -> returns true
        LastCommand lastFirstCommandCopy = new LastCommand(INDEX_FIRST_PERSON);
        assertTrue(lastFirstCommand.equals(lastFirstCommandCopy));

        // different types -> returns false
        assertFalse(lastFirstCommand.equals(1));

        // null -> returns false
        assertFalse(lastFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(lastFirstCommand.equals(lastSecondCommand));
    }
}

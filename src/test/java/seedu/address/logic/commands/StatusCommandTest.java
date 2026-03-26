package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutLogBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StatusCommand.
 */
public class StatusCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());

    @Test
    public void execute_validIndexAndStatus_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Status newStatus = new Status("inactive");
        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_PERSON, newStatus);

        Person editedPerson = new PersonBuilder(personToEdit).withStatus("inactive").build();
        String expectedMessage = String.format(StatusCommand.MESSAGE_STATUS_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(statusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Status status = new Status("inactive");
        StatusCommand statusCommand = new StatusCommand(outOfBoundIndex, status);

        assertCommandFailure(statusCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_sameStatus_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Status currentStatus = personToEdit.getStatus();
        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_PERSON, currentStatus);

        String expectedMessage = String.format(StatusCommand.MESSAGE_NOT_CHANGED,
                currentStatus, Messages.format(personToEdit));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new WorkoutLogBook());

        assertCommandSuccess(statusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Status activeStatus = new Status("active");
        Status inactiveStatus = new Status("inactive");
        StatusCommand statusFirstCommand = new StatusCommand(INDEX_FIRST_PERSON, activeStatus);
        StatusCommand statusSecondCommand = new StatusCommand(INDEX_SECOND_PERSON, activeStatus);
        StatusCommand statusFirstInactiveCommand = new StatusCommand(INDEX_FIRST_PERSON, inactiveStatus);

        // same object -> returns true
        assertTrue(statusFirstCommand.equals(statusFirstCommand));

        // same values -> returns true
        StatusCommand statusFirstCommandCopy = new StatusCommand(INDEX_FIRST_PERSON, activeStatus);
        assertTrue(statusFirstCommand.equals(statusFirstCommandCopy));

        // different types -> returns false
        assertFalse(statusFirstCommand.equals(1));

        // null -> returns false
        assertFalse(statusFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(statusFirstCommand.equals(statusSecondCommand));

        // different status -> returns false
        assertFalse(statusFirstCommand.equals(statusFirstInactiveCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Status status = new Status("active");
        StatusCommand statusCommand = new StatusCommand(targetIndex, status);
        String expected = StatusCommand.class.getCanonicalName()
                + "{index=" + targetIndex + ", status=" + status + "}";
        assertEquals(expected, statusCommand.toString());
    }
}

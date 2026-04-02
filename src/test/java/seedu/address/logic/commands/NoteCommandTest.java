package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutLogBook;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for NoteCommand.
 */
public class NoteCommandTest {

    private static final String NOTE_STUB = "Some note";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());

    @Test
    public void execute_addNoteUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNote(NOTE_STUB).build();

        NoteCommand noteCommand =
                new NoteCommand(INDEX_FIRST_PERSON, new Note(editedPerson.getNote().value), false);

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_SUCCESS, Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNoteFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                        .withNote(NOTE_STUB).build();

        NoteCommand noteCommand =
                new NoteCommand(INDEX_FIRST_PERSON, new Note(editedPerson.getNote().value), false);

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_SUCCESS, Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appendNoteToExistingNote_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithNote = new PersonBuilder(firstPerson).withNote(NOTE_STUB).build();
        model.setPerson(firstPerson, personWithNote);

        String appendText = "Additional text";
        String expectedNoteContent = NOTE_STUB + " " + appendText;
        Person expectedPerson = new PersonBuilder(personWithNote).withNote(expectedNoteContent).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(appendText), true);

        String expectedMessage = String.format(NoteCommand.MESSAGE_APPEND_SUCCESS, Messages.format(expectedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(personWithNote, expectedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appendNoteToEmptyNote_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithEmptyNote = new PersonBuilder(firstPerson).withNote("").build();
        model.setPerson(firstPerson, personWithEmptyNote);

        String appendText = "New note";
        Person editedPerson = new PersonBuilder(personWithEmptyNote).withNote(appendText).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(appendText), true);

        String expectedMessage = String.format(NoteCommand.MESSAGE_APPEND_SUCCESS, Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(personWithEmptyNote, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNote_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithNote = new PersonBuilder(firstPerson).withNote(NOTE_STUB).build();
        model.setPerson(firstPerson, personWithNote);

        Person editedPerson = new PersonBuilder(personWithNote).withNote("").build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(""), false);

        String expectedMessage = String.format(NoteCommand.MESSAGE_DELETE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(personWithNote, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appendNoteWithNoChange_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithNote = new PersonBuilder(firstPerson).withNote(NOTE_STUB).build();
        model.setPerson(firstPerson, personWithNote);

        Person editedPerson = new PersonBuilder(personWithNote).withNote(NOTE_STUB).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(""), true);

        String expectedMessage = String.format(NoteCommand.MESSAGE_NOCHANGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(personWithNote, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex, new Note(VALID_NOTE_BOB), false);

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex, new Note(VALID_NOTE_BOB), false);

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NoteCommand standardCommand =
                new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY), false);

        // same values -> returns true
        NoteCommand commandWithSameValues =
                new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY), false);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand
                .equals(new NoteCommand(INDEX_SECOND_PERSON, new Note(VALID_NOTE_AMY), false)));

        // different note -> returns false
        assertFalse(standardCommand
                .equals(new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_BOB), false)));

        // different isAppend -> returns false
        assertFalse(standardCommand
                .equals(new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY), true)));
    }
}

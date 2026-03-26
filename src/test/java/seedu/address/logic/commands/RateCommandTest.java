package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWorkoutLogs.getTypicalWorkoutLogBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rate;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWorkoutLogBook());

    @Test
    public void execute_setRateUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRate(VALID_RATE_AMY).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_PERSON, new Rate(VALID_RATE_AMY));

        String expectedMessage =
                String.format(RateCommand.MESSAGE_SET_SUCCESS, Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), getTypicalWorkoutLogBook());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearRate_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithRate = new PersonBuilder(firstPerson).withRate(VALID_RATE_BOB).build();
        model.setPerson(firstPerson, personWithRate);

        Person editedPerson = new PersonBuilder(personWithRate).withRate("").build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_PERSON, new Rate(""));

        String expectedMessage =
                String.format(RateCommand.MESSAGE_CLEAR_SUCCESS, Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), getTypicalWorkoutLogBook());
        expectedModel.setPerson(personWithRate, editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RateCommand rateCommand = new RateCommand(outOfBoundIndex, new Rate(VALID_RATE_BOB));

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RateCommand rateCommand = new RateCommand(outOfBoundIndex, new Rate(VALID_RATE_BOB));

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RateCommand standardCommand =
                new RateCommand(INDEX_FIRST_PERSON, new Rate(VALID_RATE_AMY));

        RateCommand commandWithSameValues =
                new RateCommand(INDEX_FIRST_PERSON, new Rate(VALID_RATE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        assertTrue(standardCommand.equals(standardCommand));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));

        assertFalse(standardCommand
                .equals(new RateCommand(INDEX_SECOND_PERSON, new Rate(VALID_RATE_AMY))));

        assertFalse(standardCommand
                .equals(new RateCommand(INDEX_FIRST_PERSON, new Rate(VALID_RATE_BOB))));
    }
}

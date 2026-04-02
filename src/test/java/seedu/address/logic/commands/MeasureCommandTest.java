package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BODY_FAT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEIGHT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_AMY;
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
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Height;
import seedu.address.model.person.Person;
import seedu.address.model.person.Weight;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for MeasureCommand.
 */
public class MeasureCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());

    /**
     * Executes measure on an unfiltered list with all measurement fields and verifies success.
     */
    @Test
    public void execute_updateMeasurementsUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withHeight(VALID_HEIGHT_AMY)
                .withWeight(VALID_WEIGHT_AMY)
                .withBodyFatPercentage(VALID_BODY_FAT_AMY)
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(VALID_HEIGHT_AMY), new Weight(VALID_WEIGHT_AMY),
                new BodyFatPercentage(VALID_BODY_FAT_AMY));

        String expectedMessage = String.format(MeasureCommand.MESSAGE_SET_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure on a filtered list with partial measurement updates and verifies success.
     */
    @Test
    public void execute_updateMeasurementsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withHeight(VALID_HEIGHT_AMY)
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(VALID_HEIGHT_AMY), null, null);

        String expectedMessage = String.format(MeasureCommand.MESSAGE_SET_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new WorkoutLogBook());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with blank values to clear measurements and verifies success.
     */
    @Test
    public void execute_clearMeasurements_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withHeight("")
                .withWeight("")
                .withBodyFatPercentage("")
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(""), new Weight(""), new BodyFatPercentage(""));

        String expectedMessage = String.format(MeasureCommand.MESSAGE_CLEAR_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with an out-of-bounds index on an unfiltered list and verifies failure.
     */
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MeasureCommand measureCommand = new MeasureCommand(outOfBoundIndex,
                new Height(VALID_HEIGHT_AMY), null, null);

        assertCommandFailure(measureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes measure with an out-of-bounds index on a filtered list and verifies failure.
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MeasureCommand measureCommand = new MeasureCommand(outOfBoundIndex,
                new Height(VALID_HEIGHT_AMY), null, null);

        assertCommandFailure(measureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Verifies equality behavior for {@code MeasureCommand}.
     */
    @Test
    public void equals() {
        final MeasureCommand standardCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(VALID_HEIGHT_AMY), new Weight(VALID_WEIGHT_AMY),
                new BodyFatPercentage(VALID_BODY_FAT_AMY));

        MeasureCommand sameValuesCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(VALID_HEIGHT_AMY), new Weight(VALID_WEIGHT_AMY),
                new BodyFatPercentage(VALID_BODY_FAT_AMY));
        // Same index and same optional measurements indicates commands are equal.
        assertTrue(standardCommand.equals(sameValuesCommand));

        // Same object reference should always be equal.
        assertTrue(standardCommand.equals(standardCommand));
        // Command should not be equal to null.
        assertFalse(standardCommand.equals(null));
        // Command should not be equal to objects of a different type.
        assertFalse(standardCommand.equals(new ClearCommand()));

        MeasureCommand differentIndexCommand = new MeasureCommand(INDEX_SECOND_PERSON,
                new Height(VALID_HEIGHT_AMY), new Weight(VALID_WEIGHT_AMY),
                new BodyFatPercentage(VALID_BODY_FAT_AMY));
        // Different target index indicates commands are not equal.
        assertFalse(standardCommand.equals(differentIndexCommand));

        MeasureCommand differentMeasurementCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height("170.0"), new Weight(VALID_WEIGHT_AMY),
                new BodyFatPercentage(VALID_BODY_FAT_AMY));
        // Different measurement payload indicates commands are not equal.
        assertFalse(standardCommand.equals(differentMeasurementCommand));
    }
}


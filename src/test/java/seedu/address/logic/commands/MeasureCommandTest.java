package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Person getFirstFilteredPerson(Model targetModel) {
        return targetModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    }

    private Model copyModel(Model sourceModel) {
        return new ModelManager(new AddressBook(sourceModel.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
    }

    /**
     * Executes measure on an unfiltered list with all measurement fields and verifies success.
     */
    @Test
    public void execute_updateMeasurementsUnfilteredList_success() {
        // EP: unfiltered list + all measurement fields set.
        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withHeight(VALID_HEIGHT_AMY)
                .withWeight(VALID_WEIGHT_AMY)
                .withBodyFatPercentage(VALID_BODY_FAT_AMY)
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(VALID_HEIGHT_AMY), new Weight(VALID_WEIGHT_AMY),
                new BodyFatPercentage(VALID_BODY_FAT_AMY));

        String expectedMessage = String.format(MeasureCommand.MESSAGE_HEIGHT_SET_SUCCESS,
                editedPerson.getName(), "165.5")
                + "\n" + String.format(MeasureCommand.MESSAGE_WEIGHT_SET_SUCCESS,
                editedPerson.getName(), "58.0")
                + "\n" + String.format(MeasureCommand.MESSAGE_BODY_FAT_SET_SUCCESS,
                editedPerson.getName(), "22.5");

        Model expectedModel = copyModel(model);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure on a filtered list with partial measurement updates and verifies success.
     */
    @Test
    public void execute_updateMeasurementsFilteredList_success() {
        // EP: filtered list + partial field update.
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withHeight(VALID_HEIGHT_AMY)
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(VALID_HEIGHT_AMY), null, null);

        String expectedMessage = String.format(MeasureCommand.MESSAGE_HEIGHT_SET_SUCCESS,
                editedPerson.getName(), "165.5");

        Model expectedModel = copyModel(model);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with blank values to clear measurements and verifies success.
     */
    @Test
    public void execute_clearMeasurements_success() {
        // EP: clear semantics for all provided fields.
        // BVA: empty-string measurement values.
        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withHeight("")
                .withWeight("")
                .withBodyFatPercentage("")
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(""), new Weight(""), new BodyFatPercentage(""));

        String expectedMessage = String.format(MeasureCommand.MESSAGE_HEIGHT_CLEAR_SUCCESS, editedPerson.getName())
                + "\n" + String.format(MeasureCommand.MESSAGE_WEIGHT_CLEAR_SUCCESS, editedPerson.getName())
                + "\n" + String.format(MeasureCommand.MESSAGE_BODY_FAT_CLEAR_SUCCESS, editedPerson.getName());

        Model expectedModel = copyModel(model);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with blank values on already-cleared fields and verifies success message.
     */
    @Test
    public void execute_clearMeasurementsAlreadyCleared_success() {
        // EP: idempotent clear on already-empty fields.
        Person firstPerson = getFirstFilteredPerson(model);
        Person personWithClearedMeasurements = new PersonBuilder(firstPerson)
                .withHeight("")
                .withWeight("")
                .withBodyFatPercentage("")
                .build();
        model.setPerson(firstPerson, personWithClearedMeasurements);

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(""), new Weight(""), new BodyFatPercentage(""));

        String expectedMessage = String.format(MeasureCommand.MESSAGE_HEIGHT_ALREADY_CLEARED,
                personWithClearedMeasurements.getName())
                + "\n" + String.format(MeasureCommand.MESSAGE_WEIGHT_ALREADY_CLEARED,
                personWithClearedMeasurements.getName())
                + "\n" + String.format(MeasureCommand.MESSAGE_BODY_FAT_ALREADY_CLEARED,
                personWithClearedMeasurements.getName());

        Model expectedModel = copyModel(model);
        firstPerson = getFirstFilteredPerson(expectedModel);
        expectedModel.setPerson(firstPerson, personWithClearedMeasurements);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with mixed update and clear inputs and verifies set-summary feedback.
     */
    @Test
    public void execute_updateAndClearMeasurements_success() {
        // EP: mixed set/clear behavior in one execution.
        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withHeight("180.0")
                .withWeight("")
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height("180"), new Weight(""), null);

        String expectedMessage = String.format(MeasureCommand.MESSAGE_HEIGHT_SET_SUCCESS,
                editedPerson.getName(), "180.0")
                + "\n" + String.format(MeasureCommand.MESSAGE_WEIGHT_CLEAR_SUCCESS, editedPerson.getName());

        Model expectedModel = copyModel(model);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with mixed inputs where the cleared field is already empty.
     */
    @Test
    public void execute_updateAndAlreadyClearedMeasurements_success() {
        // EP: mixed set + already-cleared feedback path.
        Person firstPerson = getFirstFilteredPerson(model);
        Person personWithClearedHeight = new PersonBuilder(firstPerson)
                .withHeight("")
                .build();
        model.setPerson(firstPerson, personWithClearedHeight);

        Person editedPerson = new PersonBuilder(personWithClearedHeight)
                .withHeight("")
                .withWeight("120.0")
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(""), new Weight("120"), null);

        String expectedMessage = String.format(MeasureCommand.MESSAGE_HEIGHT_ALREADY_CLEARED,
                editedPerson.getName())
                + "\n" + String.format(MeasureCommand.MESSAGE_WEIGHT_SET_SUCCESS,
                editedPerson.getName(), "120.0");

        Model expectedModel = copyModel(model);
        firstPerson = getFirstFilteredPerson(expectedModel);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with only body fat provided and verifies success.
     */
    @Test
    public void execute_updateBodyFatOnly_success() {
        // EP: single-field update (body fat only).
        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withBodyFatPercentage(VALID_BODY_FAT_AMY)
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                null, null, new BodyFatPercentage(VALID_BODY_FAT_AMY));

        String expectedMessage = String.format(MeasureCommand.MESSAGE_BODY_FAT_SET_SUCCESS,
                editedPerson.getName(), "22.5");

        Model expectedModel = copyModel(model);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure clearing only body fat when already empty and verifies success message.
     */
    @Test
    public void execute_clearBodyFatAlreadyCleared_success() {
        // EP: single-field clear when field already empty.
        Person firstPerson = getFirstFilteredPerson(model);
        Person personWithClearedBodyFat = new PersonBuilder(firstPerson)
                .withBodyFatPercentage("")
                .build();
        model.setPerson(firstPerson, personWithClearedBodyFat);

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                null, null, new BodyFatPercentage(""));

        String expectedMessage = String.format(MeasureCommand.MESSAGE_BODY_FAT_ALREADY_CLEARED,
                personWithClearedBodyFat.getName());

        Model expectedModel = copyModel(model);
        firstPerson = getFirstFilteredPerson(expectedModel);
        expectedModel.setPerson(firstPerson, personWithClearedBodyFat);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure clearing only weight and verifies other measurement fields remain unchanged.
     */
    @Test
    public void execute_clearWeightOnly_success() {
        // EP: single-field clear while others remain unchanged.
        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withWeight("")
                .build();

        MeasureCommand measureCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                null, new Weight(""), null);

        String expectedMessage = String.format(MeasureCommand.MESSAGE_WEIGHT_CLEAR_SUCCESS,
                editedPerson.getName());

        Model expectedModel = copyModel(model);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(measureCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes measure with an out-of-bounds index on an unfiltered list and verifies failure.
     */
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        // EP: invalid index on unfiltered list.
        // BVA: first out-of-bounds index = size + 1.
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
        // EP: index valid in backing list but invalid in filtered view.
        // BVA: filtered-list upper boundary breach.
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

    /**
     * Verifies constructor enforces that at least one measurement is provided.
     */
    @Test
    public void constructor_noMeasurementsProvided_throwsIllegalArgumentException() {
        // EP: no measurement payload provided at all.
        assertThrows(IllegalArgumentException.class, () -> new MeasureCommand(INDEX_FIRST_PERSON, null, null, null));
    }

}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLAN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLAN_BOB;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.Plan;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for PlanCommand.
 */
public class PlanCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());

    @Test
    public void execute_addPlanUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withPlan(VALID_PLAN_AMY).build();

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));

        String expectedMessage = String.format(PlanCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addPlanFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withPlan(VALID_PLAN_AMY).build();

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));

        String expectedMessage = String.format(PlanCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new WorkoutLogBook());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearPlan_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withPlan(Plan.DEFAULT_PLAN_TEXT).build();

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, Plan.getDefaultPlan());

        String expectedMessage = String.format(PlanCommand.MESSAGE_CLEAR_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PlanCommand planCommand = new PlanCommand(outOfBoundIndex, new Plan(VALID_PLAN_BOB));

        assertCommandFailure(planCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PlanCommand planCommand = new PlanCommand(outOfBoundIndex, new Plan(VALID_PLAN_BOB));

        assertCommandFailure(planCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final PlanCommand standardCommand =
                new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));

        // same values -> returns true
        PlanCommand commandWithSameValues =
                new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand
                .equals(new PlanCommand(INDEX_SECOND_PERSON, new Plan(VALID_PLAN_AMY))));

        // different plan -> returns false
        assertFalse(standardCommand
                .equals(new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_BOB))));
    }
}


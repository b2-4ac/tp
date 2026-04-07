package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Person getFirstFilteredPerson(Model targetModel) {
        return targetModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    }

    private Model copyModel(Model sourceModel) {
        return new ModelManager(new AddressBook(sourceModel.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
    }

    /**
     * Executes {@code plan} on an unfiltered list and updates the target client's plan.
     */
    @Test
    public void execute_addPlanUnfilteredList_success() {
        // EP: unfiltered list update with a non-default valid plan.
        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson).withPlan(VALID_PLAN_AMY).build();

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));

        String expectedMessage = String.format(PlanCommand.MESSAGE_SUCCESS,
                editedPerson.getName(), editedPerson.getPlan());

        Model expectedModel = copyModel(model);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes {@code plan} on a filtered list and updates the shown client at the given index.
     */
    @Test
    public void execute_addPlanFilteredList_success() {
        // EP: filtered list update with a non-default valid plan.
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = getFirstFilteredPerson(model);
        Person editedPerson = new PersonBuilder(firstPerson).withPlan(VALID_PLAN_AMY).build();

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));

        String expectedMessage = String.format(PlanCommand.MESSAGE_SUCCESS,
                editedPerson.getName(), editedPerson.getPlan());

        Model expectedModel = copyModel(model);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes {@code plan} with the FULL BODY category and verifies the command succeeds.
     */
    @Test
    public void execute_addFullBodyPlanUnfilteredList_success() {
        // EP: valid multi-word category partition ("FULL BODY").
        Person firstPerson = getFirstFilteredPerson(model);
        Plan fullBodyPlan = new Plan("FULL BODY");
        Person editedPerson = new PersonBuilder(firstPerson).withPlan("FULL BODY").build();

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, fullBodyPlan);

        String expectedMessage = String.format(PlanCommand.MESSAGE_SUCCESS,
                editedPerson.getName(), editedPerson.getPlan());

        Model expectedModel = copyModel(model);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Clears an assigned workout plan and reports clear success.
     */
    @Test
    public void execute_clearPlan_success() {
        // EP: clear semantics from assigned plan to default plan.
        // BVA: transition to default/empty-equivalent plan state.
        Person firstPerson = getFirstFilteredPerson(model);
        Person personWithAssignedPlan = new PersonBuilder(firstPerson).withPlan(VALID_PLAN_AMY).build();
        model.setPerson(firstPerson, personWithAssignedPlan);

        Person editedPerson = new PersonBuilder(personWithAssignedPlan).withPlan(Plan.DEFAULT_PLAN_TEXT).build();

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, Plan.getDefaultPlan());

        String expectedMessage = String.format(PlanCommand.MESSAGE_CLEAR_SUCCESS, editedPerson.getName());

        Model expectedModel = copyModel(model);
        firstPerson = getFirstFilteredPerson(expectedModel);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Clears an already unassigned workout plan and reports already-cleared feedback.
     */
    @Test
    public void execute_clearPlanAlreadyCleared_success() {
        // EP: idempotent clear when plan is already default.
        Person firstPerson = getFirstFilteredPerson(model);
        Person personWithClearedPlan = new PersonBuilder(firstPerson).withPlan(Plan.DEFAULT_PLAN_TEXT).build();
        model.setPerson(firstPerson, personWithClearedPlan);

        PlanCommand planCommand = new PlanCommand(INDEX_FIRST_PERSON, Plan.getDefaultPlan());

        String expectedMessage = String.format(PlanCommand.MESSAGE_ALREADY_CLEARED,
                personWithClearedPlan.getName());

        Model expectedModel = copyModel(model);
        firstPerson = getFirstFilteredPerson(expectedModel);
        expectedModel.setPerson(firstPerson, personWithClearedPlan);

        assertCommandSuccess(planCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Rejects an out-of-bounds index when operating on an unfiltered list.
     */
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        // EP: invalid index on unfiltered list.
        // BVA: first out-of-bounds index = size + 1.
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PlanCommand planCommand = new PlanCommand(outOfBoundIndex, new Plan(VALID_PLAN_BOB));

        assertCommandFailure(planCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Rejects an out-of-bounds index when operating on a filtered list.
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        // EP: index valid in backing list but invalid in filtered view.
        // BVA: filtered-list boundary breach.
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PlanCommand planCommand = new PlanCommand(outOfBoundIndex, new Plan(VALID_PLAN_BOB));

        assertCommandFailure(planCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Verifies {@link PlanCommand#equals(Object)} for matching and non-matching cases.
     */
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

        // equal objects must have the same hash code
        assertTrue(standardCommand.hashCode() == commandWithSameValues.hashCode());
    }

    /**
     * Verifies constructor defensive checks for null index/plan.
     */
    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, new Plan(VALID_PLAN_AMY)));
        assertThrows(NullPointerException.class, () -> new PlanCommand(INDEX_FIRST_PERSON, null));
    }
}

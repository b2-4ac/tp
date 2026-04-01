package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutLogBook;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());
    }

    @Test
    public void equals() {
        SortCommand sortByNameCommand = new SortCommand("name", "asc");
        SortCommand sortByLocationCommand = new SortCommand("location", "asc");

        // same object -> returns true
        assertTrue(sortByNameCommand.equals(sortByNameCommand));

        // same values -> returns true
        SortCommand sortByNameCommandCopy = new SortCommand("name", "asc");
        assertTrue(sortByNameCommand.equals(sortByNameCommandCopy));

        // different types -> returns false
        assertFalse(sortByNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortByNameCommand.equals(null));

        // different attribute -> returns false
        assertFalse(sortByNameCommand.equals(sortByLocationCommand));
    }

    @Test
    public void execute_sortByNameAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "asc");
        Comparator<Person> nameComparator = Comparator.comparing(p -> p.getName().fullName.toLowerCase());
        SortCommand command = new SortCommand("name", "asc");
        expectedModel.updatePersonListComparator(nameComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE (alphabetical order)
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByNameDescending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "desc");
        Comparator<Person> nameComparator = Comparator.comparing((Person p) ->
                p.getName().fullName.toLowerCase()).reversed();
        SortCommand command = new SortCommand("name", "desc");
        expectedModel.updatePersonListComparator(nameComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE (reverse alphabetical)
        assertEquals(Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByLocationAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "location", "asc");
        Comparator<Person> locationComparator = Comparator.comparing(p -> p.getLocation().value.toLowerCase());
        SortCommand command = new SortCommand("location", "asc");
        expectedModel.updatePersonListComparator(locationComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(7, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_sortByDateOfBirthAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "date of birth", "asc");
        Comparator<Person> dobComparator = Comparator.comparing(p -> p.getDateOfBirth().value);
        SortCommand command = new SortCommand("date of birth", "asc");
        expectedModel.updatePersonListComparator(dobComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // DANIEL (1978), BENSON (1985), ALICE (1992), ELLE (1996), GEORGE (1999), CARL (2001), FIONA (2010)
        assertEquals(Arrays.asList(DANIEL, BENSON, ALICE, ELLE, GEORGE, CARL, FIONA),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByDateOfBirthDescending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "date of birth", "desc");
        Comparator<Person> dobComparator = Comparator.comparing((Person p) ->
                p.getDateOfBirth().value).reversed();
        SortCommand command = new SortCommand("date of birth", "desc");
        expectedModel.updatePersonListComparator(dobComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // FIONA (2010), CARL (2001), GEORGE (1999), ELLE (1996), ALICE (1992), BENSON (1985), DANIEL (1978)
        assertEquals(Arrays.asList(FIONA, CARL, GEORGE, ELLE, ALICE, BENSON, DANIEL),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByPhoneAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone", "asc");
        Comparator<Person> phoneComparator = Comparator.comparing(p -> p.getPhone().value);
        SortCommand command = new SortCommand("phone", "asc");
        expectedModel.updatePersonListComparator(phoneComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // DANIEL (87652533), ALICE (94351253), ELLE (9482224), FIONA (9482427), GEORGE (9482442),
        // CARL (95352563), BENSON (98765432)
        assertEquals(Arrays.asList(DANIEL, ALICE, ELLE, FIONA, GEORGE, CARL, BENSON),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByEmailAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "email", "asc");
        Comparator<Person> emailComparator = Comparator.comparing(p -> p.getEmail().value.toLowerCase());
        SortCommand command = new SortCommand("email", "asc");
        expectedModel.updatePersonListComparator(emailComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(7, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_sortByAddressAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "address", "asc");
        Comparator<Person> addressComparator = Comparator.comparing(p -> p.getAddress().value.toLowerCase());
        SortCommand command = new SortCommand("address", "asc");
        expectedModel.updatePersonListComparator(addressComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(7, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_sortByGenderAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "gender", "asc");
        Comparator<Person> genderComparator = Comparator.comparing(p -> p.getGender().value);
        SortCommand command = new SortCommand("gender", "asc");
        expectedModel.updatePersonListComparator(genderComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Just verify the command executed successfully and list has items
        assertTrue(model.getFilteredPersonList().size() > 0);
    }

    @Test
    public void execute_sortByStatusAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "status", "asc");
        Comparator<Person> statusComparator = Comparator.comparing(p -> p.getStatus().value.name());
        SortCommand command = new SortCommand("status", "asc");
        expectedModel.updatePersonListComparator(statusComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // All typical persons are ACTIVE by default, so all 7 should be present
        assertEquals(7, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_sortByPlanAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "plan", "asc");
        Comparator<Person> planComparator = Comparator.comparing(p -> p.getPlan().value.name());
        SortCommand command = new SortCommand("plan", "asc");
        expectedModel.updatePersonListComparator(planComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Alphabetical by enum name: CARDIO (CARL), CONDITIONING (GEORGE), CORE (FIONA),
        // FULL_BODY (ELLE), LEGS (DANIEL), PULL (BENSON), PUSH (ALICE)
        assertEquals(Arrays.asList(CARL, GEORGE, FIONA, ELLE, DANIEL, BENSON, ALICE),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByRateAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "rate", "asc");
        Comparator<Person> rateComparator = Comparator.comparingDouble((Person p) ->
                p.getRate().value.isEmpty() ? -1.0 : Double.parseDouble(p.getRate().value));
        SortCommand command = new SortCommand("rate", "asc");
        expectedModel.updatePersonListComparator(rateComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // DANIEL (no rate = -1), GEORGE (75), FIONA (80), ELLE (85), BENSON (90), CARL (90), ALICE (100)
        assertEquals(Arrays.asList(DANIEL, GEORGE, FIONA, ELLE, BENSON, CARL, ALICE),
                model.getFilteredPersonList());
    }

    @Test
    public void equals_differentOrder_returnsFalse() {
        SortCommand sortByNameAsc = new SortCommand("name", "asc");
        SortCommand sortByNameDesc = new SortCommand("name", "desc");
        assertFalse(sortByNameAsc.equals(sortByNameDesc));
    }

    @Test
    public void equals_differentNewAttributes_returnsFalse() {
        SortCommand sortByStatus = new SortCommand("status", "asc");
        SortCommand sortByPlan = new SortCommand("plan", "asc");
        SortCommand sortByRate = new SortCommand("rate", "asc");

        // status vs plan -> returns false
        assertFalse(sortByStatus.equals(sortByPlan));

        // status vs rate -> returns false
        assertFalse(sortByStatus.equals(sortByRate));

        // plan vs rate -> returns false
        assertFalse(sortByPlan.equals(sortByRate));
    }

    @Test
    public void equals_newAttributeSameValues_returnsTrue() {
        SortCommand sortByStatusAsc = new SortCommand("status", "asc");
        SortCommand sortByStatusAscCopy = new SortCommand("status", "asc");
        assertTrue(sortByStatusAsc.equals(sortByStatusAscCopy));

        SortCommand sortByPlanDesc = new SortCommand("plan", "desc");
        SortCommand sortByPlanDescCopy = new SortCommand("plan", "desc");
        assertTrue(sortByPlanDesc.equals(sortByPlanDescCopy));

        SortCommand sortByRateAsc = new SortCommand("rate", "asc");
        SortCommand sortByRateAscCopy = new SortCommand("rate", "asc");
        assertTrue(sortByRateAsc.equals(sortByRateAscCopy));
    }

    @Test
    public void execute_sortByStatusDescending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "status", "desc");
        Comparator<Person> statusComparator = Comparator.comparing((
                Person p) -> p.getStatus().value.name()).reversed();
        SortCommand command = new SortCommand("status", "desc");
        expectedModel.updatePersonListComparator(statusComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(7, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_sortByPlanDescending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "plan", "desc");
        Comparator<Person> planComparator = Comparator.comparing((
                Person p) -> p.getPlan().value.name()).reversed();
        SortCommand command = new SortCommand("plan", "desc");
        expectedModel.updatePersonListComparator(planComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Reverse alphabetical by enum name: PUSH (ALICE), PULL (BENSON), LEGS (DANIEL),
        // FULL_BODY (ELLE), CORE (FIONA), CONDITIONING (GEORGE), CARDIO (CARL)
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ELLE, FIONA, GEORGE, CARL),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByRateDescending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "rate", "desc");
        Comparator<Person> rateComparator = Comparator.comparingDouble((Person p) ->
                p.getRate().value.isEmpty() ? -1.0 : Double.parseDouble(p.getRate().value)).reversed();
        SortCommand command = new SortCommand("rate", "desc");
        expectedModel.updatePersonListComparator(rateComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // ALICE (100), BENSON (90), CARL (90), ELLE (85), FIONA (80), GEORGE (75), DANIEL (no rate = -1)
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, ELLE, FIONA, GEORGE, DANIEL),
                model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand("name", "asc");
        String expected = SortCommand.class.getCanonicalName()
                + "{attribute=name, order=asc}";
        assertEquals(expected, sortCommand.toString());
    }
}

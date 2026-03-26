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
        Comparator<Person> nameComparator = Comparator.comparing(p -> p.getName().fullName.toLowerCase());
        Comparator<Person> locationComparator = Comparator.comparing(p -> p.getLocation().value.toLowerCase());

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
    public void toStringMethod() {
        Comparator<Person> nameComparator = Comparator.comparing(p -> p.getName().fullName.toLowerCase());
        SortCommand sortCommand = new SortCommand("name", "asc");
        String expected = SortCommand.class.getCanonicalName()
                + "{attribute=name, order=asc}";
        assertEquals(expected, sortCommand.toString());
    }
}

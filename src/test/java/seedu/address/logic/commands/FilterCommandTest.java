package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutLogBook;
import seedu.address.model.person.LocationContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String SINGLE_SPACE = " ";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
            new WorkoutLogBook());

    @Test
    public void equals() {
        LocationContainsKeywordsPredicate firstPredicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("first"));
        LocationContainsKeywordsPredicate secondPredicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertEquals(filterFirstCommand, filterFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, filterFirstCommand);

        // null -> returns false
        assertNotEquals(null, filterFirstCommand);

        // different person -> returns false
        assertNotEquals(filterFirstCommand, filterSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        LocationContainsKeywordsPredicate predicate = new LocationContainsKeywordsPredicate(Collections.emptyList());
        assertFilterCommandSuccess(predicate, 0);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeywords_multiplePersonsFound() {
        LocationContainsKeywordsPredicate predicate = preparePredicate("Anytime");
        assertFilterCommandSuccess(predicate, 5);
        assertEquals(Arrays.asList(CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        LocationContainsKeywordsPredicate predicate = preparePredicate("Anytime Fitness Buona", "Clementi");
        assertFilterCommandSuccess(predicate, 3);
        assertEquals(Arrays.asList(BENSON, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_blankPhrase_filtersPersonsWithEmptyLocation() {
        Person noLocationPerson = new PersonBuilder()
                .withId(UUID.randomUUID().toString())
                .withName("No Location Client")
                .withLocation("")
                .build();
        model.addPerson(noLocationPerson);
        expectedModel.addPerson(noLocationPerson);

        LocationContainsKeywordsPredicate predicate = preparePredicate("");
        assertFilterCommandSuccess(predicate, 1);
        assertEquals(Collections.singletonList(noLocationPerson), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("keyword"));
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code LocationContainsKeywordsPredicate}.
     */
    private LocationContainsKeywordsPredicate preparePredicate(String... phrases) {
        return new LocationContainsKeywordsPredicate(Arrays.stream(phrases)
                .map(String::trim)
                .map(this::normalizeWhitespace)
                .collect(Collectors.toList()));
    }

    private String normalizeWhitespace(String text) {
        return text.replaceAll(WHITESPACE_REGEX, SINGLE_SPACE);
    }

    private void assertFilterCommandSuccess(LocationContainsKeywordsPredicate predicate, int expectedCount) {
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedCount);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


}

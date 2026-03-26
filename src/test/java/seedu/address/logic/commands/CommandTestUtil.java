package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_APPEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ID_AMY = "f0962b9a-41d3-4627-897b-944a195b2173";
    public static final String VALID_ID_BOB = "bc530490-67c8-4753-8515-86641217e296";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_DOB_AMY = "24/02/2000";
    public static final String VALID_DOB_BOB = "18/07/1997";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_LOCATION_AMY = "Yishun ActiveSG Gym";
    public static final String VALID_LOCATION_BOB = "Woodlands ActiveSG Gym";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_NOTE_AMY = "Likes to play sports.";
    public static final String VALID_NOTE_BOB = "Focuses on strength training.";
    public static final String VALID_NOTE_APPEND = "Likes to work out on weekends.";
    public static final String VALID_RATE_AMY = "80.00";
    public static final String VALID_RATE_BOB = "120.50";
    public static final String VALID_HEIGHT_AMY = "165.5";
    public static final String VALID_HEIGHT_BOB = "180.0";
    public static final String VALID_WEIGHT_AMY = "58.0";
    public static final String VALID_WEIGHT_BOB = "80.5";
    public static final String VALID_BODY_FAT_AMY = "22.5";
    public static final String VALID_BODY_FAT_BOB = "15.0";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String DOB_DESC_AMY = " " + PREFIX_DOB + VALID_DOB_AMY;
    public static final String DOB_DESC_BOB = " " + PREFIX_DOB + VALID_DOB_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String LOCATION_DESC_AMY = " " + PREFIX_LOCATION + VALID_LOCATION_AMY;
    public static final String LOCATION_DESC_BOB = " " + PREFIX_LOCATION + VALID_LOCATION_BOB;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + VALID_NOTE_BOB;
    public static final String NOTE_APPEND_DESC = " " + PREFIX_NOTE_APPEND + VALID_NOTE_APPEND;
    public static final String RATE_DESC_AMY = " " + PREFIX_RATE + VALID_RATE_AMY;
    public static final String RATE_DESC_BOB = " " + PREFIX_RATE + VALID_RATE_BOB;
    public static final String HEIGHT_DESC_AMY = " " + PREFIX_HEIGHT + VALID_HEIGHT_AMY;
    public static final String HEIGHT_DESC_BOB = " " + PREFIX_HEIGHT + VALID_HEIGHT_BOB;
    public static final String WEIGHT_DESC_AMY = " " + PREFIX_WEIGHT + VALID_WEIGHT_AMY;
    public static final String WEIGHT_DESC_BOB = " " + PREFIX_WEIGHT + VALID_WEIGHT_BOB;
    public static final String BODY_FAT_DESC_AMY = " " + PREFIX_BODY_FAT + VALID_BODY_FAT_AMY;
    public static final String BODY_FAT_DESC_BOB = " " + PREFIX_BODY_FAT + VALID_BODY_FAT_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    // '&' not allowed in names
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&";
    // only 'm' and 'f' allowed in gender
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "asdf";
    // date should be valid and in dd/MM/yyyy format
    public static final String INVALID_DOB_DESC = " " + PREFIX_DOB + "30-02-2002";
    // 'a' not allowed in phones
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a";
    // missing '@' symbol
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo";
    // empty string not allowed for addresses
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS;
    // empty string not allowed for locations
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION;
    // height out of valid range
    public static final String INVALID_HEIGHT_DESC = " " + PREFIX_HEIGHT + "49.9";
    // weight out of valid range
    public static final String INVALID_WEIGHT_DESC = " " + PREFIX_WEIGHT + "10.0";
    // body fat percentage out of valid range
    public static final String INVALID_BODY_FAT_DESC = " " + PREFIX_BODY_FAT + "90.0";
    // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withGender(VALID_GENDER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withLocation(VALID_LOCATION_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withLocation(VALID_LOCATION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain
     * unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given
     * {@code targetIndex} in the {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}

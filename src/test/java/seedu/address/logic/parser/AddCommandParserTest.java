package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser,
            PREAMBLE_WHITESPACE
            + NAME_DESC_BOB
            + GENDER_DESC_BOB
            + DOB_DESC_BOB
            + PHONE_DESC_BOB
            + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB
            + LOCATION_DESC_BOB
            + TAG_DESC_FRIEND,
            new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple genders
        assertParseFailure(parser, GENDER_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // multiple dob
        assertParseFailure(parser, DOB_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DOB));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple locations
        assertParseFailure(parser, LOCATION_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + NAME_DESC_AMY
                + ADDRESS_DESC_AMY
                + LOCATION_DESC_AMY
                + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_NAME,
                        PREFIX_GENDER,
                        PREFIX_DOB,
                        PREFIX_ADDRESS,
                        PREFIX_LOCATION,
                        PREFIX_EMAIL,
                        PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid gender
        assertParseFailure(parser, INVALID_GENDER_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid dob
        assertParseFailure(parser, INVALID_DOB_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DOB));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid location
        assertParseFailure(parser, INVALID_LOCATION_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid gender
        assertParseFailure(parser, validExpectedPersonString + INVALID_GENDER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid gender
        assertParseFailure(parser, validExpectedPersonString + INVALID_DOB_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DOB));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid location
        assertParseFailure(parser, validExpectedPersonString + INVALID_LOCATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY)
                .withTags()
                .build();
        assertParseSuccess(parser,
                NAME_DESC_AMY
                + GENDER_DESC_AMY
                + DOB_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY
                + LOCATION_DESC_AMY,
                new AddCommand(expectedPerson));

        //no location
        Person expectedNoLocationPerson = new PersonBuilder(AMY)
                .withLocation("")
                .build();
        assertParseSuccess(parser,
                NAME_DESC_AMY
                + GENDER_DESC_AMY
                + DOB_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND,
                new AddCommand(expectedNoLocationPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedInvalidFormatMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String expectedMissingName = String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS, PREFIX_NAME),
                AddCommand.MESSAGE_USAGE);
        String expectedMissingGender = String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS, PREFIX_GENDER),
                AddCommand.MESSAGE_USAGE);
        String expectedMissingDob = String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS, PREFIX_DOB),
                AddCommand.MESSAGE_USAGE);
        String expectedMissingPhone = String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS, PREFIX_PHONE),
                AddCommand.MESSAGE_USAGE);
        String expectedMissingEmail = String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS, PREFIX_EMAIL),
                AddCommand.MESSAGE_USAGE);
        String expectedMissingAddress = String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS, PREFIX_ADDRESS),
                AddCommand.MESSAGE_USAGE);
        String expectedMissingAll = String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                String.format(AddCommandParser.MESSAGE_MISSING_REQUIRED_FIELDS,
                        String.join(", ",
                                PREFIX_NAME.toString(),
                                PREFIX_GENDER.toString(),
                                PREFIX_DOB.toString(),
                                PREFIX_PHONE.toString(),
                                PREFIX_EMAIL.toString(),
                                PREFIX_ADDRESS.toString())),
                AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
                GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB,
                expectedMissingName);

        // missing gender prefix
        assertParseFailure(parser,
                NAME_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB,
                expectedMissingGender);

        // missing dob prefix
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB,
                expectedMissingDob);

        // missing phone prefix
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB,
                expectedMissingPhone);

        // missing email prefix
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB,
                expectedMissingEmail);

        // missing address prefix
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + LOCATION_DESC_BOB,
                expectedMissingAddress);

        // all required prefixes missing
        assertParseFailure(parser,
                TAG_DESC_FRIEND,
                expectedMissingAll);

        // all required prefixes missing and provided as preamble values
        assertParseFailure(parser,
                VALID_NAME_BOB
                + VALID_GENDER_BOB
                + VALID_DOB_BOB
                + VALID_PHONE_BOB
                + VALID_EMAIL_BOB
                + VALID_ADDRESS_BOB
                + VALID_LOCATION_BOB,
                expectedInvalidFormatMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser,
                NAME_DESC_BOB
                + INVALID_GENDER_DESC
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                Gender.MESSAGE_CONSTRAINTS);

        // invalid dob
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + INVALID_DOB_DESC
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                DateOfBirth.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + INVALID_LOCATION_DESC
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                Location.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + INVALID_TAG_DESC
                + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values are reported together
        assertParseFailure(parser,
                INVALID_NAME_DESC
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC
                + LOCATION_DESC_BOB,
                String.join("\n", Name.MESSAGE_CONSTRAINTS, Address.MESSAGE_CONSTRAINTS));

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY
                + NAME_DESC_BOB
                + GENDER_DESC_BOB
                + DOB_DESC_BOB
                + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + LOCATION_DESC_BOB
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

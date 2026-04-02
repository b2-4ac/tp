package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noAttribute_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_ORDER + "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidOrder_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_sortByNameAscending_success() {
        SortCommand expectedCommand = new SortCommand("name", "asc");

        // with explicit ascending order
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "asc", expectedCommand);

        // without order (defaults to ascending)
        assertParseSuccess(parser, " " + PREFIX_NAME, expectedCommand);
    }

    @Test
    public void parse_sortByNameDescending_success() {
        SortCommand expectedCommand = new SortCommand("name", "desc");
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "desc", expectedCommand);
    }

    @Test
    public void parse_sortByLocationAscending_success() {
        SortCommand expectedCommand = new SortCommand("location", "asc");
        assertParseSuccess(parser, " " + PREFIX_LOCATION, expectedCommand);
    }

    @Test
    public void parse_sortByDateOfBirthDescending_success() {
        SortCommand expectedCommand = new SortCommand("date of birth", "desc");
        assertParseSuccess(parser, " " + PREFIX_DOB + " " + PREFIX_ORDER + "desc", expectedCommand);
    }

    @Test
    public void parse_sortByPhoneAscending_success() {
        SortCommand expectedCommand = new SortCommand("phone", "asc");
        assertParseSuccess(parser, " " + PREFIX_PHONE, expectedCommand);
    }

    @Test
    public void parse_sortByEmailAscending_success() {
        SortCommand expectedCommand = new SortCommand("email", "asc");
        assertParseSuccess(parser, " " + PREFIX_EMAIL, expectedCommand);
    }

    @Test
    public void parse_sortByAddressDescending_success() {
        SortCommand expectedCommand = new SortCommand("address", "desc");
        assertParseSuccess(parser, " " + PREFIX_ADDRESS + " " + PREFIX_ORDER + "desc", expectedCommand);
    }

    @Test
    public void parse_sortByGenderAscending_success() {
        SortCommand expectedCommand = new SortCommand("gender", "asc");
        assertParseSuccess(parser, " " + PREFIX_GENDER, expectedCommand);
    }

    @Test
    public void parse_sortByStatusAscending_success() {
        SortCommand expectedCommand = new SortCommand("status", "asc");
        assertParseSuccess(parser, " " + PREFIX_STATUS, expectedCommand);
    }

    @Test
    public void parse_sortByPlanDescending_success() {
        SortCommand expectedCommand = new SortCommand("plan", "desc");
        assertParseSuccess(parser, " " + PREFIX_PLAN + " " + PREFIX_ORDER + "desc", expectedCommand);
    }

    @Test
    public void parse_sortByRateAscending_success() {
        SortCommand expectedCommand = new SortCommand("rate", "asc");
        assertParseSuccess(parser, " " + PREFIX_RATE, expectedCommand);
    }

    @Test
    public void parse_duplicateOrderPrefix_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "asc " + PREFIX_ORDER + "desc",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_ORDER);
    }

    @Test
    public void parse_joinedAttributeAndOrderPrefix_throwsParseException() {
        // n/o/desc has no space before o/ so it is not recognised as the order prefix;
        // instead n/ receives the trailing value "o/desc", which is invalid
        assertParseFailure(parser, " " + PREFIX_NAME + PREFIX_ORDER + "desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_attributePrefixWithTrailingGarbage_throwsParseException() {
        // a/b/c/ — b/ and c/ are not recognised prefixes, so a/ receives value "b/c/", which is invalid
        assertParseFailure(parser, " " + PREFIX_ADDRESS + "b/c/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_attributePrefixWithTrailingText_throwsParseException() {
        // dob/test o/desc — dob/ receives value "test", which is invalid even though o/desc is valid
        assertParseFailure(parser, " " + PREFIX_DOB + "test " + PREFIX_ORDER + "desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_caseInsensitiveOrder_success() {
        SortCommand expectedCommand = new SortCommand("name", "asc");

        // uppercase order
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "ASC", expectedCommand);

        // mixed case order
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "AsC", expectedCommand);
    }
}

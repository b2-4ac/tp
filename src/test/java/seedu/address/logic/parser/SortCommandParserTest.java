package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.Person;

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
        Comparator<Person> expectedComparator = Comparator.comparing(p -> p.getName().fullName.toLowerCase());
        SortCommand expectedCommand = new SortCommand("name", "asc");

        // with explicit ascending order
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "asc", expectedCommand);

        // without order (defaults to ascending)
        assertParseSuccess(parser, " " + PREFIX_NAME, expectedCommand);
    }

    @Test
    public void parse_sortByNameDescending_success() {
        Comparator<Person> expectedComparator =
                Comparator.comparing((Person p) -> p.getName().fullName.toLowerCase()).reversed();
        SortCommand expectedCommand = new SortCommand("name", "desc");

        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "desc", expectedCommand);
    }

    @Test
    public void parse_sortByLocationAscending_success() {
        Comparator<Person> expectedComparator = Comparator.comparing(p -> p.getLocation().value.toLowerCase());
        SortCommand expectedCommand = new SortCommand("location", "asc");

        assertParseSuccess(parser, " " + PREFIX_LOCATION, expectedCommand);
    }

    @Test
    public void parse_sortByDateOfBirthDescending_success() {
        Comparator<Person> expectedComparator =
                Comparator.comparing((Person p) -> p.getDateOfBirth().value).reversed();
        SortCommand expectedCommand = new SortCommand("date of birth", "desc");

        assertParseSuccess(parser, " " + PREFIX_DOB + " " + PREFIX_ORDER + "desc", expectedCommand);
    }

    @Test
    public void parse_sortByPhoneAscending_success() {
        Comparator<Person> expectedComparator = Comparator.comparing(p -> p.getPhone().value);
        SortCommand expectedCommand = new SortCommand("phone", "asc");

        assertParseSuccess(parser, " " + PREFIX_PHONE, expectedCommand);
    }

    @Test
    public void parse_sortByEmailAscending_success() {
        Comparator<Person> expectedComparator = Comparator.comparing(p -> p.getEmail().value.toLowerCase());
        SortCommand expectedCommand = new SortCommand("email", "asc");

        assertParseSuccess(parser, " " + PREFIX_EMAIL, expectedCommand);
    }

    @Test
    public void parse_sortByAddressDescending_success() {
        Comparator<Person> expectedComparator =
                Comparator.comparing((Person p) -> p.getAddress().value.toLowerCase()).reversed();
        SortCommand expectedCommand = new SortCommand("address", "desc");

        assertParseSuccess(parser, " " + PREFIX_ADDRESS + " " + PREFIX_ORDER + "desc", expectedCommand);
    }

    @Test
    public void parse_sortByGenderAscending_success() {
        Comparator<Person> expectedComparator = Comparator.comparing(p -> p.getGender().value);
        SortCommand expectedCommand = new SortCommand("gender", "asc");

        assertParseSuccess(parser, " " + PREFIX_GENDER, expectedCommand);
    }

    @Test
    public void parse_caseInsensitiveOrder_success() {
        Comparator<Person> expectedComparator = Comparator.comparing(p -> p.getName().fullName.toLowerCase());
        SortCommand expectedCommand = new SortCommand("name", "asc");

        // uppercase order
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "ASC", expectedCommand);

        // mixed case order
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + PREFIX_ORDER + "AsC", expectedCommand);
    }
}

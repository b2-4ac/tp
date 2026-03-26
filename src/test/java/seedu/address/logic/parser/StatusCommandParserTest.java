package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.StatusCommand;
import seedu.address.model.person.Status;

public class StatusCommandParserTest {

    private StatusCommandParser parser = new StatusCommandParser();

    @Test
    public void parse_validArgs_returnsStatusCommand() {
        Status expectedStatus = new Status("active");
        assertParseSuccess(parser, "1 " + PREFIX_STATUS + "active",
                new StatusCommand(INDEX_FIRST_PERSON, expectedStatus));
    }

    @Test
    public void parse_caseInsensitiveStatus_returnsStatusCommand() {
        Status expectedStatusUpperCase = new Status("ACTIVE");
        assertParseSuccess(parser, "1 " + PREFIX_STATUS + "ACTIVE",
                new StatusCommand(INDEX_FIRST_PERSON, expectedStatusUpperCase));

        Status expectedStatusMixedCase = new Status("Inactive");
        assertParseSuccess(parser, "1 " + PREFIX_STATUS + "Inactive",
                new StatusCommand(INDEX_FIRST_PERSON, expectedStatusMixedCase));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a " + PREFIX_STATUS + "active",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStatus_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_STATUS + "unknown",
                Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateStatus_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_STATUS + "active " + PREFIX_STATUS + "inactive",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));
    }
}

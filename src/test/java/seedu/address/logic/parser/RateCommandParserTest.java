package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RateCommand;
import seedu.address.model.person.Rate;

public class RateCommandParserTest {
    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_validValue_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + "120.5";
        RateCommand expectedCommand = new RateCommand(INDEX_FIRST_PERSON, new Rate("120.5"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_clearValue_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE;
        RateCommand expectedCommand = new RateCommand(INDEX_FIRST_PERSON, new Rate(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndexOrPrefix_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, PREFIX_RATE + "120.5", expectedMessage);
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " 120.5", expectedMessage);
    }

    @Test
    public void parse_repeatedRatePrefix_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_RATE + "120.00 " + PREFIX_RATE + "100.00";
        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(PREFIX_RATE));
    }
}

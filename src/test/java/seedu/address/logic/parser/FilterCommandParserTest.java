package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.LocationContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "Anytime Fitness Jurong",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankPrefixedValues_throwsParseException() {
        assertParseFailure(parser, " l/   l/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        String[] testSingleLocationArray = {"Anytime Fitness Jurong"};
        String[] testMultiLocationArray = {"Anytime Fitness Jurong", "Clementi"};
        FilterCommand expectedSingleFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList(testSingleLocationArray)));
        assertParseSuccess(parser, " l/Anytime Fitness Jurong", expectedSingleFilterCommand);

        // multiple whitespaces between words in l/ command reduced to single spaces
        FilterCommand expectedNormalizedSingleFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList(testSingleLocationArray)));
        assertParseSuccess(parser, " l/Anytime   Fitness    Jurong", expectedNormalizedSingleFilterCommand);

        // multiple l/ phrases
        FilterCommand expectedMultiplePhrasesFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(
                        Arrays.asList(testMultiLocationArray)));
        assertParseSuccess(parser, " l/Anytime Fitness Jurong l/Clementi",
                expectedMultiplePhrasesFilterCommand);

        // mixed-case phrases should be accepted as it is by parser
        FilterCommand expectedMixedCaseFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(
                        Arrays.asList("aNyTiMe FiTnEsS jUrOnG", "cLeMeNtI")));
        assertParseSuccess(parser, " l/aNyTiMe FiTnEsS jUrOnG l/cLeMeNtI", expectedMixedCaseFilterCommand);
    }
}

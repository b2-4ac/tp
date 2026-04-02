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
    public void parse_multiplePrefixesWithBlankValue_throwsParseException() {
        assertParseFailure(parser, " l/Anytime Fitness Jurong l/   ",
                FilterCommandParser.MESSAGE_MULTIPLE_PREFIXES_CANNOT_BE_BLANK);

        assertParseFailure(parser, " l/   l/Clementi",
                FilterCommandParser.MESSAGE_MULTIPLE_PREFIXES_CANNOT_BE_BLANK);
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        String[] testSingleLocationArray = {"Anytime Fitness Jurong"};
        FilterCommand expectedSingleFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList(testSingleLocationArray)));
        assertParseSuccess(parser, " l/Anytime Fitness Jurong", expectedSingleFilterCommand);

        // multiple whitespaces between words in l/ command reduced to single spaces
        FilterCommand expectedNormalizedSingleFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList(testSingleLocationArray)));
        assertParseSuccess(parser, " l/Anytime   Fitness    Jurong", expectedNormalizedSingleFilterCommand);

        // blank value filters clients with no specified location
        FilterCommand expectedBlankFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList("")));
        assertParseSuccess(parser, " l/", expectedBlankFilterCommand);

        // multiple l/ phrases are supported
        FilterCommand expectedMultiplePhrasesFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(
                        Arrays.asList("Anytime Fitness Jurong", "Clementi")));
        assertParseSuccess(parser, " l/Anytime Fitness Jurong l/Clementi",
                expectedMultiplePhrasesFilterCommand);

        // mixed-case phrases should be accepted as-is by parser
        FilterCommand expectedMixedCaseFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(
                        Arrays.asList("aNyTiMe FiTnEsS jUrOnG", "cLeMeNtI")));
        assertParseSuccess(parser, " l/aNyTiMe FiTnEsS jUrOnG l/cLeMeNtI", expectedMixedCaseFilterCommand);
    }
}

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
        // EP: no usable argument content.
        // BVA: whitespace-only input.
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // EP: location phrase provided without required l/ prefix.
        assertParseFailure(parser, "Anytime Fitness Jurong",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixesWithBlankValue_throwsParseException() {
        // EP: multiple prefixed phrases where at least one phrase is blank.
        // BVA: blank value at end and at start of repeated-prefix sequence.
        assertParseFailure(parser, " l/Anytime Fitness Jurong l/   ",
                FilterCommandParser.MESSAGE_MULTIPLE_PREFIXES_CANNOT_BE_BLANK);

        assertParseFailure(parser, " l/   l/Clementi",
                FilterCommandParser.MESSAGE_MULTIPLE_PREFIXES_CANNOT_BE_BLANK);
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // EP: single valid phrase.
        String[] testSingleLocationArray = {"Anytime Fitness Jurong"};
        FilterCommand expectedSingleFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList(testSingleLocationArray)));
        assertParseSuccess(parser, " l/Anytime Fitness Jurong", expectedSingleFilterCommand);

        // EP: valid phrase requiring whitespace normalization.
        FilterCommand expectedNormalizedSingleFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList(testSingleLocationArray)));
        assertParseSuccess(parser, " l/Anytime   Fitness    Jurong", expectedNormalizedSingleFilterCommand);

        // EP: explicit empty phrase (clear/missing-location filter semantics).
        // BVA: empty string value right after prefix.
        FilterCommand expectedBlankFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(Arrays.asList("")));
        assertParseSuccess(parser, " l/", expectedBlankFilterCommand);

        // EP: multiple valid l/ phrases.
        FilterCommand expectedMultiplePhrasesFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(
                        Arrays.asList("Anytime Fitness Jurong", "Clementi")));
        assertParseSuccess(parser, " l/Anytime Fitness Jurong l/Clementi",
                expectedMultiplePhrasesFilterCommand);

        // EP: case-variant phrases should still parse successfully.
        FilterCommand expectedMixedCaseFilterCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(
                        Arrays.asList("aNyTiMe FiTnEsS jUrOnG", "cLeMeNtI")));
        assertParseSuccess(parser, " l/aNyTiMe FiTnEsS jUrOnG l/cLeMeNtI", expectedMixedCaseFilterCommand);
    }
}

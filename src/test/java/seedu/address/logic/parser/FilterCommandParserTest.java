package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.LocationContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

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
        assertParsesToCommand(" l/Anytime Fitness Jurong", "Anytime Fitness Jurong");

        // EP: valid phrase requiring whitespace normalization.
        assertParsesToCommand(" l/Anytime   Fitness    Jurong", "Anytime Fitness Jurong");

        // EP: explicit empty phrase (clear/missing-location filter semantics).
        // BVA: empty string value right after prefix.
        assertParsesToCommand(" l/", "");

        // EP: multiple valid l/ phrases.
        assertParsesToCommand(" l/Anytime Fitness Jurong l/Clementi", "Anytime Fitness Jurong", "Clementi");

        // EP: case-variant phrases should still parse successfully.
        assertParsesToCommand(" l/aNyTiMe FiTnEsS jUrOnG l/cLeMeNtI", "aNyTiMe FiTnEsS jUrOnG", "cLeMeNtI");
    }

    private void assertParsesToCommand(String userInput, String... expectedPhrases) {
        List<String> expectedPhraseList = Arrays.asList(expectedPhrases);
        FilterCommand expectedCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(expectedPhraseList));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

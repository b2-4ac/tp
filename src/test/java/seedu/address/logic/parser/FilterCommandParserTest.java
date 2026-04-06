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
        // no leading or trailing whitespace in phrase
        assertParsesToCommand(" l/Anytime Fitness Jurong", "Anytime Fitness Jurong");

        // multiple internal spaces are normalized to single spaces
        assertParsesToCommand(" l/Anytime   Fitness    Jurong", "Anytime Fitness Jurong");

        // blank phrase is supported to filter clients with empty location
        assertParsesToCommand(" l/", "");

        // multiple location phrases are supported
        assertParsesToCommand(" l/Anytime Fitness Jurong l/Clementi", "Anytime Fitness Jurong", "Clementi");

        // mixed-case phrases are accepted as-is by parser
        assertParsesToCommand(" l/aNyTiMe FiTnEsS jUrOnG l/cLeMeNtI", "aNyTiMe FiTnEsS jUrOnG", "cLeMeNtI");
    }

    private void assertParsesToCommand(String userInput, String... expectedPhrases) {
        List<String> expectedPhraseList = Arrays.asList(expectedPhrases);
        FilterCommand expectedCommand =
                new FilterCommand(new LocationContainsKeywordsPredicate(expectedPhraseList));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

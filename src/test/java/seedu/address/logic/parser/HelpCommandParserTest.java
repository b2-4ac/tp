package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;

public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_emptyArg_returnsHelpCommand() {
        // empty string
        HelpCommand expectedHelpCommand = new HelpCommand("");
        assertParseSuccess(parser, "", expectedHelpCommand);

        // whitespace only
        assertParseSuccess(parser, "     ", expectedHelpCommand);
    }

    @Test
    public void parse_validArgs_returnsHelpCommand() {
        // single command word
        HelpCommand expectedHelpCommand = new HelpCommand("add");
        assertParseSuccess(parser, "add", expectedHelpCommand);

        // command word with leading and trailing whitespaces
        assertParseSuccess(parser, "  add  ", expectedHelpCommand);

        // different command word
        expectedHelpCommand = new HelpCommand("delete");
        assertParseSuccess(parser, "delete", expectedHelpCommand);
    }
}

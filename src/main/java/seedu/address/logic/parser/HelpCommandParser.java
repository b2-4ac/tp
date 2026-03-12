package seedu.address.logic.parser;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {
    @Override
    public HelpCommand parse(String args) throws ParseException {
        return new HelpCommand(args.trim());
    }
}

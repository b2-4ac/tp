package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Rate;

/**
 * Parses input arguments and creates a new RateCommand object.
 */
public class RateCommandParser implements Parser<RateCommand> {

    /**
     * Parses the given {@code String} of arguments for the RateCommand and returns a
     * RateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_RATE);

        if (!argMultimap.getValue(PREFIX_RATE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        String rateContent = argMultimap.getValue(PREFIX_RATE).get();
        Rate rate;
        try {
            rate = ParserUtil.parseRate(rateContent);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe);
        }

        return new RateCommand(index, rate);
    }
}

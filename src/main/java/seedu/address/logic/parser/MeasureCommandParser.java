package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeasureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Height;
import seedu.address.model.person.Weight;

/**
 * Parses input arguments and creates a new MeasureCommand object.
 */
public class MeasureCommandParser implements Parser<MeasureCommand> {

    @Override
    public MeasureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_BODY_FAT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_BODY_FAT);

        if (!argMultimap.getValue(PREFIX_HEIGHT).isPresent()
                && !argMultimap.getValue(PREFIX_WEIGHT).isPresent()
                && !argMultimap.getValue(PREFIX_BODY_FAT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
        }

        Height height = null;
        Weight weight = null;
        BodyFatPercentage bodyFatPercentage = null;

        if (argMultimap.getValue(PREFIX_HEIGHT).isPresent()) {
            try {
                height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT).get());
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe);
            }
        }
        if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
            try {
                weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get());
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe);
            }
        }
        if (argMultimap.getValue(PREFIX_BODY_FAT).isPresent()) {
            try {
                bodyFatPercentage = ParserUtil.parseBodyFatPercentage(argMultimap.getValue(PREFIX_BODY_FAT).get());
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe);
            }
        }

        return new MeasureCommand(index, height, weight, bodyFatPercentage);
    }
}


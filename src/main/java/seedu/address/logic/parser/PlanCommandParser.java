package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAN;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PlanCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Plan;

/**
 * Parses input arguments and creates a new PlanCommand object.
 */
public class PlanCommandParser implements Parser<PlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PlanCommand and returns a
     * PlanCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PlanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAN);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAN);

        if (argMultimap.getValue(PREFIX_PLAN).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_USAGE));
        }

        Plan plan;
        if (argMultimap.getValue(PREFIX_PLAN).get().trim().isEmpty()) {
            plan = Plan.getDefaultPlan();
        } else {
            plan = ParserUtil.parsePlanCategory(argMultimap.getValue(PREFIX_PLAN).get());
        }
        return new PlanCommand(index, plan);
    }
}


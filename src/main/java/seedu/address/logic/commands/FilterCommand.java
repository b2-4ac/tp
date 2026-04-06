package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.LocationContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose location contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters clients whose location contains the specified phrase (case-insensitive), "
            + "or filters clients with no specified location when the phrase is blank.\n"
            + "Use one or more l/ prefixes.\n"
            + "If multiple l/ prefixes are provided, each must have a non-blank value.\n"
            + "Parameters: " + PREFIX_LOCATION + "LOCATION_PHRASE [" + PREFIX_LOCATION + "MORE_LOCATION_PHRASES]...\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " " + PREFIX_LOCATION + "Anytime Fitness Buona\n"
            + "  " + COMMAND_WORD + " " + PREFIX_LOCATION + "Anytime Fitness " + PREFIX_LOCATION + "Clementi\n"
            + "  " + COMMAND_WORD + " " + PREFIX_LOCATION;

    private static final Logger logger = LogsCenter.getLogger(FilterCommand.class);

    private final LocationContainsKeywordsPredicate predicate;

    /**
     * Creates a FilterCommand to filter the client list by location phrases.
     *
     * @param predicate Predicate that defines which clients match.
     */
    public FilterCommand(LocationContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing filter command with predicate: " + predicate);

        model.updateFilteredPersonList(predicate);
        assert model.getFilteredPersonList() != null : "Filtered person list should never be null";

        int numberOfClientsFound = model.getFilteredPersonList().size();
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, numberOfClientsFound));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand otherFilterCommand)) {
            return false;
        }

        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

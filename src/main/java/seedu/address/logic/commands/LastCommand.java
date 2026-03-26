package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.workout.WorkoutLog;

/**
 * Finds the most recent recorded workout of the specified client.
 */
public class LastCommand extends Command {

    public static final String COMMAND_WORD = "last";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds the previous training session of the person identified by the index number "
            + "used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RETRIEVE_LOG_SUCCESS = "Previous Session for: %s\n"
            + "Date: %s\n"
            + "Location: %s";

    public static final String MESSAGE_NO_LOGS_FOUND_FAILURE = "No Logs found for: %s";

    private final Index targetIndex;

    public LastCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSearch = lastShownList.get(targetIndex.getZeroBased());
        WorkoutLog latest = model.lastLog(personToSearch);
        if (latest == null) {
            return new CommandResult(String.format(MESSAGE_NO_LOGS_FOUND_FAILURE, personToSearch.getName()));
        }

        return new CommandResult(String.format(MESSAGE_RETRIEVE_LOG_SUCCESS,
                personToSearch.getName(),
                latest.getTime(),
                latest.getLocation()));
    }
}

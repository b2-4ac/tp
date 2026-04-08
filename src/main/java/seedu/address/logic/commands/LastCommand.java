package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
            + ": Displays the most recent training session of the client "
            + "identified by the index number used in the displayed client list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RETRIEVE_LOG_SUCCESS = "Previous Session for: %s\n"
            + "Date: %s\n"
            + "Location: %s";

    public static final String MESSAGE_NO_LOGS_FOUND_FAILURE = "No workouts have been logged for: %s";

    private static final String UNSET_LOCATION_DISPLAY = "N/A";

    private static final Logger logger = LogsCenter.getLogger(LastCommand.class);

    private final Index targetIndex;

    public LastCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing last command for client at index " + targetIndex.getOneBased());

        Person personToSearch = getTargetPerson(model);

        WorkoutLog latest = model.lastLog(personToSearch);
        if (latest == null) {
            return handleNoLogFound(personToSearch);
        }

        logger.fine("Last command executed successfully for client at index: " + targetIndex.getOneBased());

        return new CommandResult(formatSuccessMessage(personToSearch, latest));
    }

    private Person getTargetPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        // Check for valid index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Last command failed due to invalid index: " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSearch = lastShownList.get(targetIndex.getZeroBased());
        return personToSearch;
    }

    private CommandResult handleNoLogFound(Person person) {
        logger.info("No logs found for specified client");
        return new CommandResult(String.format(MESSAGE_NO_LOGS_FOUND_FAILURE, person.getName()));
    }

    private String formatSuccessMessage(Person person, WorkoutLog log) {
        return String.format(MESSAGE_RETRIEVE_LOG_SUCCESS,
                person.getName(),
                log.getTime(),
                resolveLocation(log));
    }

    private String resolveLocation(WorkoutLog log) {
        String locationString = log.getLocation().toString();
        return locationString.isEmpty() ? UNSET_LOCATION_DISPLAY : locationString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LastCommand)) {
            return false;
        }

        LastCommand otherLastCommand = (LastCommand) other;
        return targetIndex.equals(otherLastCommand.targetIndex);
    }
}

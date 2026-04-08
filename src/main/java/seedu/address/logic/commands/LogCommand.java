package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.Location;
import seedu.address.model.person.Person;
import seedu.address.model.workout.WorkoutLog;
import seedu.address.model.workout.WorkoutTime;

/**
 * Adds a WorkoutLog, linking a client to a workout session.
 */
public class LogCommand extends Command {

    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Logs a workout for the person identified by the index number used in the displayed client list\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_LOCATION + "LOCATION]";

    public static final String MESSAGE_LOG_WORKOUT_SUCCESS = "Workout Logged for: %s\n"
            + "Date: %s\n"
            + "Location: %s";

    public static final String MESSAGE_DUPLICATE_LOG = "This workout log already exists.";

    private static final Logger logger = LogsCenter.getLogger(LogCommand.class);

    private final Index targetIndex;
    private final WorkoutTime time;
    private final Location location;

    /**
     * Constructs a new {@code LogCommand}.
     *
     * @param targetIndex of the person in the filtered person list to log
     * @param time during which the workout took place
     * @param location at which the workout took place
     */
    public LogCommand(Index targetIndex, WorkoutTime time, Location location) {
        requireNonNull(targetIndex);
        requireNonNull(time);
        requireNonNull(location);

        this.targetIndex = targetIndex;
        this.time = time;
        this.location = location;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing log command for client at index " + targetIndex.getOneBased());

        assert time != null : "Time should always be initialised";
        assert location != null : "Location should always be initialised, use empty string for unspecified location";

        Person personToLog = getTargetPerson(model);
        WorkoutLog newLog = createWorkoutLog(personToLog);

        ensureNoDuplicateLog(model, newLog);

        model.addLog(newLog);
        logger.fine("Successfully logged workout for client at index " + targetIndex.getOneBased());

        return new CommandResult(formatSuccessMessage(personToLog, newLog));
    }

    private Person getTargetPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        // Check for valid index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Log command failed due to invalid index: " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(targetIndex.getZeroBased());
    }

    private WorkoutLog createWorkoutLog(Person person) {
        ClientId traineeId = person.getId();
        Location workoutLocation = resolveLocation(person);
        WorkoutLog newLog = new WorkoutLog(traineeId, time, workoutLocation);
        return newLog;
    }

    private Location resolveLocation(Person person) {
        if (location.toString().isEmpty()) {
            logger.info("Empty location detected, client's preset location will be used");
            return new Location(person.getLocation().toString());
        } else {
            return location;
        }
    }

    private void ensureNoDuplicateLog(Model model, WorkoutLog log) throws CommandException {
        if (model.hasLog(log)) {
            logger.warning("Log command failed due to duplicate logs present");
            throw new CommandException(MESSAGE_DUPLICATE_LOG);
        }
    }

    private String formatSuccessMessage(Person person, WorkoutLog log) {
        return String.format(MESSAGE_LOG_WORKOUT_SUCCESS,
                person.getName(),
                log.getTime(),
                log.getLocation());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LogCommand)) {
            return false;
        }

        LogCommand otherLogCommand = (LogCommand) other;
        return targetIndex.equals(otherLogCommand.targetIndex)
                && time.equals(otherLogCommand.time)
                && location.equals(otherLogCommand.location);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("time", time)
                .add("location", location)
                .toString();
    }
}

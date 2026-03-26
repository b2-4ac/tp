package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;

import seedu.address.commons.core.index.Index;
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
            + ": Logs a workout for the person identified by the index number used in the displayed person list"
            + "Parameters: "
            + "INDEX (must be a positive integer)"
            + "[" + PREFIX_TIME + "TIME]"
            + "[" + PREFIX_LOCATION + "LOCATION]";

    public static final String MESSAGE_LOG_WORKOUT_SUCCESS = "Workout Logged for: %1$s";

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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToLog = lastShownList.get(targetIndex.getZeroBased());
        ClientId traineeId = new ClientId(personToLog.getId().toString());
        Location workoutLocation;
        if (location.toString().equals(Location.UNSPECIFIED_LOCATION)) {
            workoutLocation = new Location(personToLog.getLocation().toString());
        } else {
            workoutLocation = location;
        }
        WorkoutLog newLog = new WorkoutLog(traineeId, time, workoutLocation);

        model.addLog(newLog);
        return new CommandResult(String.format(MESSAGE_LOG_WORKOUT_SUCCESS, Messages.format(personToLog)));
    }
}

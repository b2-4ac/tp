package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Plan;

/**
 * Assigns or updates a workout plan for a client identified by index.
 */
public class PlanCommand extends Command {

    public static final String COMMAND_WORD = "plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns or replaces the workout plan of the specified client by index number used "
            + "in the displayed client list.\n"
            + "Use 'wp/' with a value to set the plan, or 'wp/' with no value to clear it.\n"
            + "Parameters: INDEX (must be a positive integer) wp/[CATEGORY]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 1 wp/PUSH\n"
            + "  " + COMMAND_WORD + " 2 wp/LEGS\n"
            + "  " + COMMAND_WORD + " 3 wp/";

    public static final String MESSAGE_SUCCESS = "Updated workout plan to %2$s for client: %1$s";
    public static final String MESSAGE_CLEAR_SUCCESS = "Workout plan unassigned for client: %1$s";
    public static final String MESSAGE_ALREADY_CLEARED = "Workout plan is already unassigned for client: %1$s";

    private static final Logger logger = LogsCenter.getLogger(PlanCommand.class);

    private final Index index;
    private final Plan plan;

    /**
     * Creates a PlanCommand to assign/update the specified {@code plan} of the client at the
     * specified {@code index}.
     *
     * @param index of the client in the last displayed list to edit the workout plan
     * @param plan workout plan to update the client to
     */
    public PlanCommand(Index index, Plan plan) {
        requireNonNull(index);
        requireNonNull(plan);
        this.index = index;
        this.plan = plan;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing plan command for index: " + index.getOneBased());

        List<Person> lastShownList = model.getFilteredPersonList();

        if (isTargetIndexInvalid(lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit);

        assert editedPerson.getPlan().equals(plan) : "Edited client plan should match requested plan";

        model.setPerson(personToEdit, editedPerson);
        String message = buildResultMessage(personToEdit, editedPerson);
        return new CommandResult(message);
    }

    private boolean isTargetIndexInvalid(List<Person> lastShownList) {
        int zeroBasedIndex = index.getZeroBased();
        return zeroBasedIndex < 0 || zeroBasedIndex >= lastShownList.size();
    }

    private Person createEditedPerson(Person personToEdit) {
        return personToEdit.withPlan(plan);
    }

    private String buildResultMessage(Person personToEdit, Person editedPerson) {
        if (plan.isUnassigned()) {
            String message = personToEdit.getPlan().isUnassigned() ? MESSAGE_ALREADY_CLEARED : MESSAGE_CLEAR_SUCCESS;
            return String.format(message, editedPerson.getName());
        }

        return String.format(MESSAGE_SUCCESS, editedPerson.getName(), editedPerson.getPlan());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PlanCommand)) {
            return false;
        }

        PlanCommand otherCommand = (PlanCommand) other;
        return index.equals(otherCommand.index) && plan.equals(otherCommand.plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, plan);
    }

    /**
     * Returns a debug-friendly string representation of this command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("plan", plan)
                .toString();
    }
}

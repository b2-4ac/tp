package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Plan;

/**
 * Assigns or updates a workout plan for a person identified by index.
 */
public class PlanCommand extends Command {

    public static final String COMMAND_WORD = "plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a workout plan to the specified person by index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PLAN + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PLAN
            + "PUSH";

    public static final String MESSAGE_SUCCESS = "Updated workout plan for person: %1$s";

    private final Index index;
    private final Plan plan;

    /**
     * Creates a PlanCommand to assign/update the specified {@code plan} of the person at the
     * specified {@code index}.
     *
     * @param index of the person in the last person list to edit the workout plan
     * @param plan of the person to be updated to
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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getId(),
                personToEdit.getName(),
                personToEdit.getGender(),
                personToEdit.getDateOfBirth(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getLocation(),
                personToEdit.getNote(),
                plan,
                personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
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


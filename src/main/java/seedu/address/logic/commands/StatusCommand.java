package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;

/**
 * Changes the status of an existing person in the address book.
 */
public class StatusCommand extends Command {

    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the status of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_STATUS + "STATUS (must be 'active' or 'inactive')\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_STATUS + "inactive";

    public static final String MESSAGE_STATUS_PERSON_SUCCESS = "Updated status of Person: %1$s";
    public static final String MESSAGE_NOT_CHANGED = "Status is already set to %1$s for person: %2$s";

    private final Index index;
    private final Status status;

    /**
     * @param index of the person in the filtered person list to update status
     * @param status new status to set for the person
     */
    public StatusCommand(Index index, Status status) {
        requireNonNull(index);
        requireNonNull(status);

        this.index = index;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Check if status is already the same
        if (personToEdit.getStatus().equals(status)) {
            return new CommandResult(
                    String.format(MESSAGE_NOT_CHANGED, status, Messages.format(personToEdit)));
        }

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
                personToEdit.getRate(),
                status, // NEW STATUS
                personToEdit.getHeight(),
                personToEdit.getWeight(),
                personToEdit.getBodyFatPercentage(),
                personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_STATUS_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusCommand)) {
            return false;
        }

        StatusCommand otherStatusCommand = (StatusCommand) other;
        return index.equals(otherStatusCommand.index)
                && status.equals(otherStatusCommand.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("status", status)
                .toString();
    }
}

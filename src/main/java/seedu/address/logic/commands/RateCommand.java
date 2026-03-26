package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rate;

/**
 * Adds or replaces a person's session rate.
 */
public class RateCommand extends Command {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or replaces the session rate of the specified person by index number used "
            + "in the displayed person list.\n"
            + "Use 'r/' with a value to set the rate, or 'r/' with no value to clear it.\n"
            + "Parameters: INDEX (must be a positive integer) r/[RATE]\n" + "Examples:\n" + "  "
            + COMMAND_WORD + " 1 r/120.50\n" + "  " + COMMAND_WORD + " 2 r/199\n" + "  "
            + COMMAND_WORD + " 3 r/";

    public static final String MESSAGE_SET_SUCCESS = "Rate added/updated for person: %1$s";
    public static final String MESSAGE_CLEAR_SUCCESS = "Rate cleared for person: %1$s";

    private final Index index;
    private final Rate rate;

    /**
     * Creates a RateCommand to add/replace the rate of the person at the specified {@code index}.
     */
    public RateCommand(Index index, Rate rate) {
        requireNonNull(index);
        requireNonNull(rate);

        this.index = index;
        this.rate = rate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getId(), personToEdit.getName(),
                personToEdit.getGender(), personToEdit.getDateOfBirth(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getLocation(),
                personToEdit.getNote(), rate, personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // Change this when we want to change the message to "-" to indicate cleared rate instead of
        // empty string
        String message = rate.value.isEmpty() ? MESSAGE_CLEAR_SUCCESS : MESSAGE_SET_SUCCESS;
        return new CommandResult(String.format(message, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RateCommand)) {
            return false;
        }

        RateCommand otherRateCommand = (RateCommand) other;
        return index.equals(otherRateCommand.index) && rate.equals(otherRateCommand.rate);
    }
}

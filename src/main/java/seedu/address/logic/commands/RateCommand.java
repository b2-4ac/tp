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
import seedu.address.model.person.Rate;

/**
 * Adds or replaces a person's session rate.
 */
public class RateCommand extends Command {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or replaces the session rate of the specified client by index number used "
            + "in the displayed client list.\n"
            + "Use 'r/' with a value to set the rate, or 'r/' with no value to clear it.\n"
            + "Parameters: INDEX (must be a positive integer) r/[RATE]\n" + "Examples:\n" + "  "
            + COMMAND_WORD + " 1 r/120.50\n" + "  " + COMMAND_WORD + " 2 r/199\n" + "  "
            + COMMAND_WORD + " 3 r/";

    public static final String MESSAGE_SET_SUCCESS = "Rate added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_NO_CHANGE_SUCCESS = "Rate unchanged for client: %1$s";
    public static final String MESSAGE_CLEAR_SUCCESS = "Rate cleared for client: %1$s";
    public static final String MESSAGE_RATE_ALREADY_CLEARED =
            "Rate is already cleared for client: %1$s";

    private static final Logger logger = LogsCenter.getLogger(RateCommand.class);

    private final Index index;
    private final Rate rate;

    /**
     * Creates a RateCommand to add/replace the rate of the client at the specified {@code index}.
     */
    public RateCommand(Index index, Rate rate) {
        requireNonNull(index);
        requireNonNull(rate);

        this.index = index;
        this.rate = rate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing rate command for index: " + index.getOneBased());
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createPersonWithUpdatedRate(personToEdit);

        model.setPerson(personToEdit, editedPerson);

        String message = determineSuccessMessage(personToEdit);

        return new CommandResult(String.format(message, editedPerson.getName(), rate.value));
    }

    private Person createPersonWithUpdatedRate(Person personToEdit) {
        return new Person(personToEdit.getId(), personToEdit.getName(), personToEdit.getGender(),
                personToEdit.getDateOfBirth(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getLocation(), personToEdit.getNote(),
                personToEdit.getPlan(), rate, personToEdit.getStatus(), personToEdit.getHeight(),
                personToEdit.getWeight(), personToEdit.getBodyFatPercentage(),
                personToEdit.getTags());
    }

    private String determineSuccessMessage(Person personToEdit) {
        boolean wasRateCleared = personToEdit.getRate().value.isEmpty();
        boolean isRateCleared = rate.value.isEmpty();

        if (isRateCleared && wasRateCleared) {
            return MESSAGE_RATE_ALREADY_CLEARED;
        }
        if (isRateCleared) {
            return MESSAGE_CLEAR_SUCCESS;
        }
        if (personToEdit.getRate().equals(rate)) {
            return MESSAGE_NO_CHANGE_SUCCESS;
        }
        return MESSAGE_SET_SUCCESS;
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

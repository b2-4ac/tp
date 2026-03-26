package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Height;
import seedu.address.model.person.Person;
import seedu.address.model.person.Weight;

/**
 * Updates body measurements for a person identified by index.
 */
public class MeasureCommand extends Command {

    public static final String COMMAND_WORD = "measure";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates body measurements for the person identified by the index number used in the displayed"
            + " person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HEIGHT + "HEIGHT_CM] "
            + "[" + PREFIX_WEIGHT + "WEIGHT_KG] "
            + "[" + PREFIX_BODY_FAT + "BODY_FAT_PERCENTAGE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HEIGHT + "175.5 "
            + PREFIX_WEIGHT + "72.0 "
            + PREFIX_BODY_FAT + "14.8";

    public static final String MESSAGE_SET_SUCCESS = "Measurements added/updated for person: %1$s";
    public static final String MESSAGE_CLEAR_SUCCESS = "Measurements cleared for person: %1$s";

    private final Index index;
    private final Height height;
    private final Weight weight;
    private final BodyFatPercentage bodyFatPercentage;

    /**
     * Creates a MeasureCommand.
     */
    public MeasureCommand(Index index, Height height, Weight weight, BodyFatPercentage bodyFatPercentage) {
        requireNonNull(index);

        this.index = index;
        this.height = height;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
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
                personToEdit.getRate(),
                personToEdit.getStatus(),
                height == null ? personToEdit.getHeight() : height,
                weight == null ? personToEdit.getWeight() : weight,
                bodyFatPercentage == null ? personToEdit.getBodyFatPercentage() : bodyFatPercentage,
                personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        boolean hasAnyProvidedField = height != null || weight != null || bodyFatPercentage != null;
        boolean isClearOperation = hasAnyProvidedField
                && (height == null || height.value.isEmpty())
                && (weight == null || weight.value.isEmpty())
                && (bodyFatPercentage == null || bodyFatPercentage.value.isEmpty());
        String message = isClearOperation ? MESSAGE_CLEAR_SUCCESS : MESSAGE_SET_SUCCESS;
        return new CommandResult(String.format(message, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MeasureCommand)) {
            return false;
        }

        MeasureCommand otherCommand = (MeasureCommand) other;
        return index.equals(otherCommand.index)
                && Objects.equals(height, otherCommand.height)
                && Objects.equals(weight, otherCommand.weight)
                && Objects.equals(bodyFatPercentage, otherCommand.bodyFatPercentage);
    }
}


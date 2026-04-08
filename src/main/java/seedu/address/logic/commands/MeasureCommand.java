package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Height;
import seedu.address.model.person.Person;
import seedu.address.model.person.Weight;

/**
 * Updates body measurements for a client identified by index.
 */
public class MeasureCommand extends Command {

    public static final String COMMAND_WORD = "measure";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates body measurements for the client identified by the index number used in the displayed"
            + " client list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HEIGHT + "HEIGHT_CM] "
            + "[" + PREFIX_WEIGHT + "WEIGHT_KG] "
            + "[" + PREFIX_BODY_FAT + "BODY_FAT_PERCENTAGE]\n"
            + "At least one of h/, w/, or bf/ must be provided (e.g., 'measure 1' is invalid).\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HEIGHT + "175.5 "
            + PREFIX_WEIGHT + "72.0 "
            + PREFIX_BODY_FAT + "14.8";

    public static final String MESSAGE_HEIGHT_SET_SUCCESS = "Height added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_HEIGHT_CLEAR_SUCCESS = "Height cleared for client: %1$s";
    public static final String MESSAGE_HEIGHT_ALREADY_CLEARED = "Height is already cleared for client: %1$s";

    public static final String MESSAGE_WEIGHT_SET_SUCCESS = "Weight added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_WEIGHT_CLEAR_SUCCESS = "Weight cleared for client: %1$s";
    public static final String MESSAGE_WEIGHT_ALREADY_CLEARED = "Weight is already cleared for client: %1$s";

    public static final String MESSAGE_BODY_FAT_SET_SUCCESS = "Body Fat added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_BODY_FAT_CLEAR_SUCCESS = "Body Fat cleared for client: %1$s";
    public static final String MESSAGE_BODY_FAT_ALREADY_CLEARED =
            "Body Fat is already cleared for client: %1$s";

    private static final String MESSAGE_NO_MEASUREMENTS_PROVIDED =
            "At least one measurement must be provided.";

    private static final Logger logger = LogsCenter.getLogger(MeasureCommand.class);

    private final Index index;
    private final Height height;
    private final Weight weight;
    private final BodyFatPercentage bodyFatPercentage;

    /**
     * Creates a MeasureCommand.
     */
    public MeasureCommand(Index index, Height height, Weight weight, BodyFatPercentage bodyFatPercentage) {
        requireNonNull(index);
        if (!hasAnyMeasurementProvided(height, weight, bodyFatPercentage)) {
            throw new IllegalArgumentException(MESSAGE_NO_MEASUREMENTS_PROVIDED);
        }

        this.index = index;
        this.height = height;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing measure command for index: " + index.getOneBased());

        List<Person> lastShownList = model.getFilteredPersonList();
        if (isTargetIndexInvalid(lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        assert hasAnyMeasurementProvided(height, weight, bodyFatPercentage)
                : "Invariant broken: MeasureCommand must carry at least one measurement.";

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit);
        model.setPerson(personToEdit, editedPerson);

        String message = formatOutcomeMessages(personToEdit, editedPerson.getName().toString());
        return new CommandResult(message);
    }

    private Person createEditedPerson(Person personToEdit) {
        Height updatedHeight = height != null ? height : personToEdit.getHeight();
        Weight updatedWeight = weight != null ? weight : personToEdit.getWeight();
        BodyFatPercentage updatedBodyFatPercentage = bodyFatPercentage != null
                ? bodyFatPercentage : personToEdit.getBodyFatPercentage();
        return personToEdit.withMeasurements(updatedHeight, updatedWeight, updatedBodyFatPercentage);
    }

    private boolean isTargetIndexInvalid(List<Person> lastShownList) {
        int zeroBasedIndex = index.getZeroBased();
        return zeroBasedIndex < 0 || zeroBasedIndex >= lastShownList.size();
    }

    private static boolean hasAnyMeasurementProvided(Height height, Weight weight,
                                                     BodyFatPercentage bodyFatPercentage) {
        return height != null || weight != null || bodyFatPercentage != null;
    }

    /** Returns field-specific outcomes in deterministic order (h/, w/, bf/). */
    private String formatOutcomeMessages(Person personBeforeEdit, String clientName) {
        StringJoiner joiner = new StringJoiner("\n");
        if (height != null) {
            joiner.add(formatHeightOutcome(clientName, personBeforeEdit.getHeight().value));
        }
        if (weight != null) {
            joiner.add(formatWeightOutcome(clientName, personBeforeEdit.getWeight().value));
        }
        if (bodyFatPercentage != null) {
            joiner.add(formatBodyFatOutcome(clientName, personBeforeEdit.getBodyFatPercentage().value));
        }
        return joiner.toString();
    }

    private String formatHeightOutcome(String clientName, String oldValue) {
        assert height != null : "Invariant broken: formatHeightOutcome should only be called when height is present.";
        if (height.value.isEmpty()) {
            String message = oldValue.isEmpty() ? MESSAGE_HEIGHT_ALREADY_CLEARED : MESSAGE_HEIGHT_CLEAR_SUCCESS;
            return String.format(message, clientName);
        }
        return String.format(MESSAGE_HEIGHT_SET_SUCCESS, clientName, height.value);
    }

    private String formatWeightOutcome(String clientName, String oldValue) {
        assert weight != null : "Invariant broken: formatWeightOutcome should only be called when weight is present.";
        if (weight.value.isEmpty()) {
            String message = oldValue.isEmpty() ? MESSAGE_WEIGHT_ALREADY_CLEARED : MESSAGE_WEIGHT_CLEAR_SUCCESS;
            return String.format(message, clientName);
        }
        return String.format(MESSAGE_WEIGHT_SET_SUCCESS, clientName, weight.value);
    }

    private String formatBodyFatOutcome(String clientName, String oldValue) {
        assert bodyFatPercentage != null
                : "Invariant broken: formatBodyFatOutcome should only be called when body fat is present.";
        if (bodyFatPercentage.value.isEmpty()) {
            String message = oldValue.isEmpty() ? MESSAGE_BODY_FAT_ALREADY_CLEARED : MESSAGE_BODY_FAT_CLEAR_SUCCESS;
            return String.format(message, clientName);
        }
        return String.format(MESSAGE_BODY_FAT_SET_SUCCESS, clientName, bodyFatPercentage.value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MeasureCommand otherCommand)) {
            return false;
        }
        return index.equals(otherCommand.index)
                && Objects.equals(height, otherCommand.height)
                && Objects.equals(weight, otherCommand.weight)
                && Objects.equals(bodyFatPercentage, otherCommand.bodyFatPercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, height, weight, bodyFatPercentage);
    }

    /**
     * Returns a debug-friendly string representation of this command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("height", height)
                .add("weight", weight)
                .add("bodyFatPercentage", bodyFatPercentage)
                .toString();
    }
}

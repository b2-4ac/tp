package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.PersonComparators.ORDER_DESC;

import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonComparators;

/**
 * Sorts the client list by a specified attribute in ascending or descending order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list of clients by the specified attribute.\n"
            + "Parameters: ATTRIBUTE/ [o/ORDER]\n"
            + "Supported attributes: n/ (name), l/ (location), dob/ (date of birth), "
            + "p/ (phone), e/ (email), a/ (address), g/ (gender), "
            + "s/ (status), wp/ (plan), r/ (rate)\n"
            + "Order: asc (ascending, default) or desc (descending)\n"
            + "Example: " + COMMAND_WORD + " n/ o/asc";

    public static final String MESSAGE_SUCCESS = "Sorted clients by %s in %s order";

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

    private final String attribute;
    private final String order;

    /**
     * Creates a SortCommand to sort the client list.
     * The comparator is reconstructed during execution based on attribute and order,
     * ensuring that equals() properly reflects object equality.
     *
     * @param attribute The attribute being sorted by.
     * @param order The order of sorting (asc/desc).
     */
    public SortCommand(String attribute, String order) {
        requireNonNull(attribute);
        requireNonNull(order);
        this.attribute = attribute;
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert PersonComparators.isValidAttribute(attribute) : "Attribute must be valid";

        logger.info(String.format("Executing sort command for attribute: %s, order: %s", attribute, order));

        // Reconstruct comparator from attribute and order
        boolean isDescending = order.equals(ORDER_DESC);
        Comparator<Person> comparator = PersonComparators.getComparator(attribute, isDescending);

        assert comparator != null : "Comparator should not be null for valid attribute";

        model.updatePersonListComparator(comparator);
        logger.fine(String.format("Successfully sorted client list by %s in %s order", attribute, order));
        return new CommandResult(String.format(MESSAGE_SUCCESS, attribute, order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        // Equality is based on attribute and order only, since comparators are deterministic
        return attribute.equals(otherCommand.attribute)
                && order.equals(otherCommand.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, order);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("attribute", attribute)
                .add("order", order)
                .toString();
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.person.PersonComparators.ATTRIBUTE_ADDRESS;
import static seedu.address.model.person.PersonComparators.ATTRIBUTE_DOB;
import static seedu.address.model.person.PersonComparators.ATTRIBUTE_EMAIL;
import static seedu.address.model.person.PersonComparators.ATTRIBUTE_GENDER;
import static seedu.address.model.person.PersonComparators.ATTRIBUTE_LOCATION;
import static seedu.address.model.person.PersonComparators.ATTRIBUTE_NAME;
import static seedu.address.model.person.PersonComparators.ATTRIBUTE_PHONE;
import static seedu.address.model.person.PersonComparators.DEFAULT_ORDER;
import static seedu.address.model.person.PersonComparators.ORDER_ASC;
import static seedu.address.model.person.PersonComparators.ORDER_DESC;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonComparators;

/**
 * Parses input arguments and creates a new SortCommand object.
 * Uses a map-based approach for extensibility and maintainability.
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Logger logger = LogsCenter.getLogger(SortCommandParser.class);

    /** Maps prefixes to their corresponding attribute names. */
    private static final Map<Prefix, String> PREFIX_TO_ATTRIBUTE = new HashMap<>();

    static {
        PREFIX_TO_ATTRIBUTE.put(PREFIX_NAME, ATTRIBUTE_NAME);
        PREFIX_TO_ATTRIBUTE.put(PREFIX_LOCATION, ATTRIBUTE_LOCATION);
        PREFIX_TO_ATTRIBUTE.put(PREFIX_DOB, ATTRIBUTE_DOB);
        PREFIX_TO_ATTRIBUTE.put(PREFIX_PHONE, ATTRIBUTE_PHONE);
        PREFIX_TO_ATTRIBUTE.put(PREFIX_EMAIL, ATTRIBUTE_EMAIL);
        PREFIX_TO_ATTRIBUTE.put(PREFIX_ADDRESS, ATTRIBUTE_ADDRESS);
        PREFIX_TO_ATTRIBUTE.put(PREFIX_GENDER, ATTRIBUTE_GENDER);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        logger.fine("Parsing sort command with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_LOCATION, PREFIX_ORDER);

        // Find which attribute to sort by
        String attribute = null;
        int attributeCount = 0;

        for (Map.Entry<Prefix, String> entry : PREFIX_TO_ATTRIBUTE.entrySet()) {
            if (argMultimap.getValue(entry.getKey()).isPresent()) {
                attribute = entry.getValue();
                attributeCount++;
            }
        }

        // Validate that exactly one attribute is specified
        if (attributeCount == 0) {
            logger.finer("No attribute specified in sort command");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (attributeCount > 1) {
            logger.finer("Multiple attributes specified in sort command");
            throw new ParseException(
                    "Please specify only one attribute to sort by.\n"
                    + SortCommand.MESSAGE_USAGE);
        }

        // Determine sort order
        String order = argMultimap.getValue(PREFIX_ORDER).orElse(DEFAULT_ORDER).toLowerCase();
        if (!order.equals(ORDER_ASC) && !order.equals(ORDER_DESC)) {
            logger.finer("Invalid order specified: " + order);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Validate that the attribute is supported
        assert attribute != null : "Attribute should not be null after validation";
        if (!PersonComparators.isValidAttribute(attribute)) {
            logger.finer("Invalid attribute specified: " + attribute);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        logger.fine("Sort command parsed successfully - attribute: " + attribute + ", order: " + order);
        return new SortCommand(attribute, order);
    }
}

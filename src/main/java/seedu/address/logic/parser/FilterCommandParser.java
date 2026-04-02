package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.LocationContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_MULTIPLE_PREFIXES_CANNOT_BE_BLANK =
            "For multiple l/ prefixes, none of them can be blank.";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand and returns a
     * FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LOCATION);

        List<String> locationKeywords = argMultimap.getAllValues(PREFIX_LOCATION).stream()
                .map(value -> value.replaceAll("\\s+", " ").trim())
                .collect(Collectors.toList());

        if (locationKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (locationKeywords.size() > 1 && locationKeywords.stream().anyMatch(String::isEmpty)) {
            throw new ParseException(MESSAGE_MULTIPLE_PREFIXES_CANNOT_BE_BLANK);
        }

        return new FilterCommand(new LocationContainsKeywordsPredicate(locationKeywords));
    }


}

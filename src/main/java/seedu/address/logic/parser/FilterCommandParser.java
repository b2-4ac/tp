package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String SINGLE_SPACE = " ";
    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand and returns a
     * FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LOCATION);
        List<String> locationKeywords = parseLocationKeywords(argMultimap);

        validateLocationKeywords(locationKeywords);

        return new FilterCommand(new LocationContainsKeywordsPredicate(locationKeywords));
    }

    private List<String> parseLocationKeywords(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_LOCATION).stream()
                .map(this::normalizeLocationPhrase)
                .collect(Collectors.toList());
    }

    private String normalizeLocationPhrase(String rawPhrase) {
        return rawPhrase.replaceAll(WHITESPACE_REGEX, SINGLE_SPACE).trim();
    }

    private void validateLocationKeywords(List<String> locationKeywords) throws ParseException {
        if (locationKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (hasMultiplePhrasesWithAnyBlankPhrase(locationKeywords)) {
            throw new ParseException(MESSAGE_MULTIPLE_PREFIXES_CANNOT_BE_BLANK);
        }
    }

    private boolean hasMultiplePhrasesWithAnyBlankPhrase(List<String> locationKeywords) {
        boolean hasMultiplePhrases = locationKeywords.size() > 1;
        boolean hasAnyBlankPhrase = locationKeywords.stream().anyMatch(String::isEmpty);
        return hasMultiplePhrases && hasAnyBlankPhrase;
    }
}

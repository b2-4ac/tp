package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Height;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Plan;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Status;
import seedu.address.model.person.Weight;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final String EMPTY_NOTE = "";
    public static final String EMPTY_RATE = "";
    // Command specific messages for add command
    public static final String MESSAGE_MISSING_REQUIRED_FIELDS = "Missing required field(s): %1$s";
    public static final String MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE = "%1$s\n%2$s";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                    args,
                    PREFIX_NAME,
                    PREFIX_GENDER,
                    PREFIX_DOB,
                    PREFIX_PHONE,
                    PREFIX_EMAIL,
                    PREFIX_ADDRESS,
                    PREFIX_LOCATION,
                    PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Prefix[] requiredPrefixes = {
            PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS
        };
        String missingRequiredPrefixes = getMissingRequiredPrefixes(argMultimap, requiredPrefixes);
        if (!missingRequiredPrefixes.isEmpty()) {
            String missingFieldsMessage = String.format(MESSAGE_MISSING_REQUIRED_FIELDS, missingRequiredPrefixes);
            throw new ParseException(String.format(MESSAGE_MISSING_REQUIRED_FIELDS_WITH_USAGE,
                    missingFieldsMessage, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_LOCATION, PREFIX_ADDRESS);

        StringJoiner validationErrors = new StringJoiner("\n");

        Name name = null;
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
        }

        Gender gender = null;
        try {
            gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
        }

        DateOfBirth dob = null;
        try {
            dob = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB).get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
        }

        Phone phone = null;
        try {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
        }

        Email email = null;
        try {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
        }

        Address address = null;
        try {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
        }

        Location location = null;
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            try {
                location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
            } catch (ParseException pe) {
                validationErrors.add(pe.getMessage());
            }
        } else {
            location = new Location(Location.EMPTY_LOCATION);
        }

        Set<Tag> tagList = null;
        try {
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
        }

        if (validationErrors.length() > 0) {
            throw new ParseException(validationErrors.toString());
        }

        ClientId id = new ClientId(UUID.randomUUID().toString());
        Plan plan = Plan.getDefaultPlan();

        Person person = new Person(
                id,
                name,
                gender,
                dob,
                phone,
                email,
                address,
                location,
                new Note(EMPTY_NOTE),
                plan,
                new Rate(EMPTY_RATE),
                new Status("active"),
                new Height(Height.DEFAULT_HEIGHT_TEXT),
                new Weight(Weight.DEFAULT_WEIGHT_TEXT),
                new BodyFatPercentage(BodyFatPercentage.DEFAULT_BODY_FAT_TEXT),

                tagList);

        return new AddCommand(person);
    }

    private static String getMissingRequiredPrefixes(ArgumentMultimap argumentMultimap, Prefix... requiredPrefixes) {
        return Stream.of(requiredPrefixes)
                .filter(prefix -> !argumentMultimap.getValue(prefix).isPresent())
                .map(Prefix::toString)
                .collect(Collectors.joining(", "));
    }

}

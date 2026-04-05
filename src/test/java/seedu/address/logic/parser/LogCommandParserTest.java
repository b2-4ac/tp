package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKOUT_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.WORKOUT_TIME_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.WORKOUT_TIME_DESC_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.workout.WorkoutTime.FORMATTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LogCommand;
import seedu.address.model.person.Location;
import seedu.address.model.workout.WorkoutTime;

public class LogCommandParserTest {

    private LogCommandParser parser = new LogCommandParser();

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // Multiple Location Prefixes
        assertParseFailure(parser,
                "1" + LOCATION_DESC_AMY + LOCATION_DESC_BOB,
                getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // Multiple Time Prefixes
        assertParseFailure(parser,
                "1" + WORKOUT_TIME_DESC_1 + WORKOUT_TIME_DESC_2,
                getErrorMessageForDuplicatePrefixes(PREFIX_TIME));
    }

    @Test
    public void parse_missingOptionalValues_success() {
        // Unspecfied Time
        assertParseSuccess(parser, "1" + LOCATION_DESC_AMY,
                new LogCommand(
                        INDEX_FIRST_PERSON,
                        new WorkoutTime(LocalDateTime.now().format(FORMATTER)),
                        new Location(VALID_LOCATION_AMY)));

        // Unspecified Location
        assertParseSuccess(parser, "1" + WORKOUT_TIME_DESC_1,
                new LogCommand(
                        INDEX_FIRST_PERSON,
                        new WorkoutTime(VALID_WORKOUT_TIME_1),
                        new Location(Location.EMPTY_LOCATION)));

        // Unspecified Time and Location
        assertParseSuccess(parser, "1",
                new LogCommand(
                        INDEX_FIRST_PERSON,
                        new WorkoutTime(LocalDateTime.now().format(FORMATTER)),
                        new Location(Location.EMPTY_LOCATION)));
    }

}

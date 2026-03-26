package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.CommandTestUtil.BODY_FAT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeasureCommand;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Height;
import seedu.address.model.person.Weight;

public class MeasureCommandParserTest {

    private final MeasureCommandParser parser = new MeasureCommandParser();

    /**
     * Parses input missing the required index and verifies failure.
     */
    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, HEIGHT_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with index but without any measurement prefix and verifies failure.
     */
    @Test
    public void parse_noMeasurementPrefix_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with an invalid height value and verifies failure.
     */
    @Test
    public void parse_invalidHeight_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_HEIGHT + "49.9";
        assertParseFailure(parser, userInput, Height.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with an invalid weight value and verifies failure.
     */
    @Test
    public void parse_invalidWeight_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_WEIGHT + "10.0";
        assertParseFailure(parser, userInput, Weight.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with an invalid body fat percentage value and verifies failure.
     */
    @Test
    public void parse_invalidBodyFat_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_BODY_FAT + "90.0";
        assertParseFailure(parser, userInput, BodyFatPercentage.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with duplicate measurement prefixes and verifies failure.
     */
    @Test
    public void parse_duplicatePrefix_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + HEIGHT_DESC_AMY + " "
                + PREFIX_HEIGHT + "170.0";
        assertParseFailure(parser, userInput,
                getErrorMessageForDuplicatePrefixes(PREFIX_HEIGHT));
    }

    /**
     * Parses input with a single valid measurement prefix and verifies success.
     */
    @Test
    public void parse_validSinglePrefix_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + HEIGHT_DESC_AMY;
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height("165.5"), null, null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Parses input with an empty measurement value and verifies clear semantics.
     */
    @Test
    public void parse_clearMeasurement_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_HEIGHT;
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(""), null, null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Parses input with multiple valid measurement prefixes and verifies success.
     */
    @Test
    public void parse_validMultiplePrefixes_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + HEIGHT_DESC_AMY + WEIGHT_DESC_AMY + BODY_FAT_DESC_AMY;
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height("165.5"), new Weight("58.0"), new BodyFatPercentage("22.5"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


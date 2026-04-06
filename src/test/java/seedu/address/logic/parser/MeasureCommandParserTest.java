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
        // EP: missing mandatory target index.
        assertParseFailure(parser, HEIGHT_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with index but without any measurement prefix and verifies failure.
     */
    @Test
    public void parse_noMeasurementPrefix_failure() {
        // EP: index provided but no measurement prefix/value pair.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with an invalid height value and verifies failure.
     */
    @Test
    public void parse_invalidHeight_failure() {
        // EP: invalid height value.
        // BVA: numeric value just below accepted lower bound.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_HEIGHT + "49.9";
        assertParseFailure(parser, userInput, Height.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with an invalid weight value and verifies failure.
     */
    @Test
    public void parse_invalidWeight_failure() {
        // EP: invalid weight value.
        // BVA: numeric value below accepted lower bound.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_WEIGHT + "10.0";
        assertParseFailure(parser, userInput, Weight.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with an invalid body fat percentage value and verifies failure.
     */
    @Test
    public void parse_invalidBodyFat_failure() {
        // EP: invalid body fat value.
        // BVA: numeric value above accepted upper bound.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_BODY_FAT + "90.0";
        assertParseFailure(parser, userInput, BodyFatPercentage.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with multiple invalid measurement values and verifies aggregated failure.
     */
    @Test
    public void parse_multipleInvalidMeasurements_failure() {
        // EP: multiple invalid prefixed values in one command.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_HEIGHT + "a " + PREFIX_WEIGHT + "a";
        String expectedMessage = Height.MESSAGE_CONSTRAINTS + "\n" + Weight.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Parses input with duplicate measurement prefixes and verifies failure.
     */
    @Test
    public void parse_duplicatePrefix_failure() {
        // EP: duplicate height prefix.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + HEIGHT_DESC_AMY + " "
                + PREFIX_HEIGHT + "170.0";
        assertParseFailure(parser, userInput,
                getErrorMessageForDuplicatePrefixes(PREFIX_HEIGHT));
    }

    /**
     * Parses input with duplicate weight prefixes and verifies failure.
     */
    @Test
    public void parse_duplicateWeightPrefix_failure() {
        // EP: duplicate weight prefix.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + WEIGHT_DESC_AMY + " "
                + PREFIX_WEIGHT + "70.0";
        assertParseFailure(parser, userInput,
                getErrorMessageForDuplicatePrefixes(PREFIX_WEIGHT));
    }

    /**
     * Parses input with duplicate body fat prefixes and verifies failure.
     */
    @Test
    public void parse_duplicateBodyFatPrefix_failure() {
        // EP: duplicate body fat prefix.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + BODY_FAT_DESC_AMY + " "
                + PREFIX_BODY_FAT + "20.0";
        assertParseFailure(parser, userInput,
                getErrorMessageForDuplicatePrefixes(PREFIX_BODY_FAT));
    }

    /**
     * Parses input with a single valid measurement prefix and verifies success.
     */
    @Test
    public void parse_validSinglePrefix_success() {
        // EP: one valid measurement prefix with value.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + HEIGHT_DESC_AMY;
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height("165.5"), null, null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    /**
     * Parses input with a trailing-dot weight value and verifies normalization.
     */
    @Test
    public void parse_validTrailingDotPrefix_success() {
        // EP: valid trailing-dot decimal format for weight.
        // BVA: decimal representation normalization boundary ("72." -> "72.0").
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_WEIGHT + "72.";
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                null, new Weight("72.0"), null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Parses input with a trailing-dot height value and verifies normalization.
     */
    @Test
    public void parse_validTrailingDotHeight_success() {
        // EP: valid trailing-dot decimal format for height.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_HEIGHT + "170.";
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height("170.0"), null, null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Parses input with a trailing-dot body fat value and verifies normalization.
     */
    @Test
    public void parse_validTrailingDotBodyFat_success() {
        // EP: valid trailing-dot decimal format for body fat.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_BODY_FAT + "18.";
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                null, null, new BodyFatPercentage("18.0"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Parses input with an empty measurement value and verifies clear semantics.
     */
    @Test
    public void parse_clearMeasurement_success() {
        // EP: explicit clear semantics via empty prefixed value.
        // BVA: empty-string boundary after prefix.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_HEIGHT;
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(""), null, null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Parses input with multiple valid measurement prefixes and verifies success.
     */
    @Test
    public void parse_validMultiplePrefixes_success() {
        // EP: multiple valid measurement prefixes in a single command.
        String userInput = INDEX_FIRST_PERSON.getOneBased() + HEIGHT_DESC_AMY + WEIGHT_DESC_AMY + BODY_FAT_DESC_AMY;
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height("165.5"), new Weight("58.0"), new BodyFatPercentage("22.5"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Parses input with zero index and verifies failure.
     */
    @Test
    public void parse_zeroIndex_failure() {
        // EP: non-positive index.
        // BVA: zero index boundary.
        String userInput = "0 " + HEIGHT_DESC_AMY;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with negative index and verifies failure.
     */
    @Test
    public void parse_negativeIndex_failure() {
        // EP: negative index.
        // BVA: just below zero boundary.
        String userInput = "-1 " + HEIGHT_DESC_AMY;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with non-numeric index and verifies failure.
     */
    @Test
    public void parse_nonNumericIndex_failure() {
        // EP: non-numeric preamble/index token.
        String userInput = "a " + HEIGHT_DESC_AMY;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with a mix of clear and set semantics and verifies success.
     */
    @Test
    public void parse_clearAndSetMeasurements_success() {
        // EP: mixed partitions in one command (clear one field, set another).
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_HEIGHT + " " + PREFIX_WEIGHT + "70.0";
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON,
                new Height(""), new Weight("70.0"), null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


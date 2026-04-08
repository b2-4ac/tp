package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.CommandTestUtil.PLAN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PLAN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLAN_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PlanCommand;
import seedu.address.model.person.Plan;

public class PlanCommandParserTest {

    private final PlanCommandParser parser = new PlanCommandParser();

    /**
     * Parses valid wp/ inputs, including mixed-case values and normalized internal whitespace.
     */
    @Test
    public void parse_planPrefix_success() {
        // EP: valid wp/ values across case and whitespace-normalization variants.
        Index targetIndex = INDEX_FIRST_PERSON;

        // Standard valid plan value
        String userInput = targetIndex.getOneBased() + " " + PREFIX_PLAN + VALID_PLAN_AMY;
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));
        assertParseSuccess(parser, userInput, expectedCommand);

        // Mixed-case value should still be parsed (case-insensitive)
        userInput = targetIndex.getOneBased() + " " + PREFIX_PLAN + "pUsH";
        expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("PUSH"));
        assertParseSuccess(parser, userInput, expectedCommand);

        // Extra internal and surrounding whitespace should be normalized
        userInput = targetIndex.getOneBased() + " " + PREFIX_PLAN + "   FULL    BODY   ";
        expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("FULL BODY"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Fails when required index/arguments are missing.
     */
    @Test
    public void parse_missingIndexOrParams_failure() {
        // EP: missing mandatory command parts (index and/or wp/ argument).
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_USAGE);

        // No parameters
        assertParseFailure(parser, "", expectedMessage);

        // Only prefix, no index
        assertParseFailure(parser, PREFIX_PLAN + VALID_PLAN_AMY, expectedMessage);
    }

    /**
     * Fails when the plan prefix is missing.
     */
    @Test
    public void parse_missingPrefix_failure() {
        // EP: plan value provided without required wp/ prefix.
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + VALID_PLAN_AMY, expectedMessage);
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + "", expectedMessage);
    }

    /**
     * Parses blank wp/ value as the default unassigned plan.
     */
    @Test
    public void parse_blankPrefixedValue_success() {
        // EP: explicit clear/reset semantics with blank wp/ value.
        // BVA: empty-string-equivalent prefixed value.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, Plan.getDefaultPlan());
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "   ", expectedCommand);
    }

    /**
     * Fails when plan value is not one of the supported categories.
     */
    @Test
    public void parse_invalidValue_failure() {
        // EP: syntactically present wp/ with unsupported category value.
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "Bench Press",
                Plan.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses valid arguments into a {@link PlanCommand}.
     */
    @Test
    public void parse_validArgs_returnsPlanCommand() {
        // EP: canonical valid index + wp/ argument.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));

        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PLAN_DESC_AMY, expectedCommand);
    }

    /**
     * Parses plan values in a case-insensitive manner.
     */
    @Test
    public void parse_caseInsensitiveValue_success() {
        // EP: case-insensitive value normalization.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("PUSH"));

        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "pUsH", expectedCommand);
    }

    /**
     * Parses multi-word plan values with surrounding and internal whitespace.
     */
    @Test
    public void parse_whitespaceInValue_success() {
        // EP: multi-word value with surrounding/internal whitespace normalization.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("FULL BODY"));

        assertParseSuccess(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "   FULL    BODY   ",
                expectedCommand);
    }

    /**
     * Parses underscore-separated multi-word category names.
     */
    @Test
    public void parse_underscoreInValue_success() {
        // EP: separator normalization for underscore-delimited categories.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("FULL BODY"));

        assertParseSuccess(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "FULL_BODY",
                expectedCommand);
    }

    /**
     * Parses hyphen-separated multi-word category names.
     */
    @Test
    public void parse_hyphenInValue_success() {
        // EP: separator normalization for hyphen-delimited categories.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("FULL BODY"));

        assertParseSuccess(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "FULL-BODY",
                expectedCommand);
    }

    /**
     * Parses multi-word category names with repeated underscores.
     */
    @Test
    public void parse_multipleUnderscoresInValue_success() {
        // EP: repeated separator normalization.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("FULL BODY"));

        assertParseSuccess(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "FULL__BODY",
                expectedCommand);
    }

    /**
     * Parses multi-word category names with mixed repeated separators.
     */
    @Test
    public void parse_mixedRepeatedSeparatorsInValue_success() {
        // EP: mixed repeated separator normalization.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("FULL BODY"));

        assertParseSuccess(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "FULL__---___BODY",
                expectedCommand);
    }

    /**
     * Parses single-word category names with surrounding whitespace.
     */
    @Test
    public void parse_surroundingWhitespaceInSingleWordValue_success() {
        // EP: single-word value with surrounding whitespace.
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("PUSH"));

        assertParseSuccess(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "   PUSH   ",
                expectedCommand);
    }

    /**
     * Fails when duplicate wp/ prefixes are provided.
     */
    @Test
    public void parse_repeatedPlanValue_failure() {
        // EP: duplicate wp/ prefixes are rejected regardless of value validity.
        String validExpectedCommandString = INDEX_FIRST_PERSON.getOneBased() + " " + PLAN_DESC_AMY;

        // multiple wp/ values are not allowed
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + PLAN_DESC_AMY + PLAN_DESC_BOB,
                getErrorMessageForDuplicatePrefixes(PREFIX_PLAN));

        // invalid value followed by valid value still reports duplicate prefix first
        assertParseFailure(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "Bench Press" + PLAN_DESC_AMY,
                getErrorMessageForDuplicatePrefixes(PREFIX_PLAN));

        // valid value followed by invalid value still reports duplicate prefix first
        assertParseFailure(parser, validExpectedCommandString + " " + PREFIX_PLAN + "Bench Press",
                getErrorMessageForDuplicatePrefixes(PREFIX_PLAN));

        // blank value followed by valid value still reports duplicate prefix first
        assertParseFailure(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "   " + PLAN_DESC_AMY,
                getErrorMessageForDuplicatePrefixes(PREFIX_PLAN));
    }

    /**
     * Fails fast when parser input is null.
     */
    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}

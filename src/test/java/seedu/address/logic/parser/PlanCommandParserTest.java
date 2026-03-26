package seedu.address.logic.parser;

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

    private PlanCommandParser parser = new PlanCommandParser();

    @Test
    public void parse_planPrefix_success() {
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

    @Test
    public void parse_missingIndexOrParams_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_USAGE);

        // No parameters
        assertParseFailure(parser, "", expectedMessage);

        // Only prefix, no index
        assertParseFailure(parser, PREFIX_PLAN + VALID_PLAN_AMY, expectedMessage);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + VALID_PLAN_AMY, expectedMessage);
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + "", expectedMessage);
    }

    @Test
    public void parse_blankPrefixedValue_failure() {
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, Plan.getDefaultPlan());
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "   ", expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "Bench Press",
                Plan.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsPlanCommand() {
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan(VALID_PLAN_AMY));

        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PLAN_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_caseInsensitiveValue_success() {
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("PUSH"));

        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "pUsH", expectedCommand);
    }

    @Test
    public void parse_whitespaceInValue_success() {
        PlanCommand expectedCommand = new PlanCommand(INDEX_FIRST_PERSON, new Plan("FULL BODY"));

        assertParseSuccess(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PLAN + "   FULL    BODY   ",
                expectedCommand);
    }

    @Test
    public void parse_repeatedPlanValue_failure() {
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
    }
}

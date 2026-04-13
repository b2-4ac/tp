package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_helpWithoutArgs_success() {
        String expectedMessage = CommandRegistry.getUsageMap().values().stream()
                .collect(Collectors.joining("\n\n")) + "\n\n" + HelpCommand.USER_GUIDE_REFERENCE;
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(new HelpCommand(""), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpWithSpecificCommand_success() {
        CommandResult expectedCommandResult = new CommandResult(AddCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand("add"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpWithUnknownCommand_success() {
        String expectedMessage = "Unknown command: xyz\n\nType 'help' to see all available commands.";
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(new HelpCommand("xyz"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand helpAllCommand = new HelpCommand("");
        HelpCommand helpAddCommand = new HelpCommand("add");

        // EP: same object -> returns true
        assertTrue(helpAllCommand.equals(helpAllCommand));

        // EP: same values -> returns true
        assertTrue(helpAllCommand.equals(new HelpCommand("")));

        // EP: different types -> returns false
        assertFalse(helpAllCommand.equals(1));

        // EP: null -> returns false
        assertFalse(helpAllCommand.equals(null));

        // EP: different targetCommand -> returns false
        assertFalse(helpAllCommand.equals(helpAddCommand));

        // EP: case-insensitive normalization — "ADD" and "add" are the same command
        assertTrue(new HelpCommand("ADD").equals(new HelpCommand("add")));
    }

    @Test
    public void hashCode_equalCommands_sameHash() {
        assertEquals(new HelpCommand("add").hashCode(), new HelpCommand("add").hashCode());
        assertEquals(new HelpCommand("ADD").hashCode(), new HelpCommand("add").hashCode());
    }

    @Test
    public void toStringMethod() {
        HelpCommand helpCommand = new HelpCommand("add");
        String expected = HelpCommand.class.getCanonicalName() + "{targetCommand=add}";
        assertEquals(expected, helpCommand.toString());
    }
}

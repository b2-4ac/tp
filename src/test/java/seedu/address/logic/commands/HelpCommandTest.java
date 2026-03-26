package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_helpWithoutArgs_success() {
        String expectedMessage = AddCommand.MESSAGE_USAGE + "\n\n"
                + DeleteCommand.MESSAGE_USAGE + "\n\n"
                + EditCommand.MESSAGE_USAGE + "\n\n"
                + NoteCommand.MESSAGE_USAGE + "\n\n"
                + StatusCommand.MESSAGE_USAGE + "\n\n"
                + RateCommand.MESSAGE_USAGE + "\n\n"
                + FindCommand.MESSAGE_USAGE + "\n\n"
                + FilterCommand.MESSAGE_USAGE + "\n\n"
                + ListCommand.MESSAGE_USAGE + "\n\n"
                + ClearCommand.MESSAGE_USAGE + "\n\n"
                + HelpCommand.MESSAGE_USAGE + "\n\n"
                + ExitCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, true, false);
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
}

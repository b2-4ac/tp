package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String USER_GUIDE_REFERENCE =
            "Refer to the user guide: https://ay2526s2-cs2103-f08-1a.github.io/tp/UserGuide.html";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows program usage instructions for all commands or a specific command.\n"
            + "Parameters: [COMMAND_WORD] (optional)\n"
            + "Example: " + COMMAND_WORD + " (shows all commands) or " + COMMAND_WORD
            + " add (shows add command only)";

    private final String targetCommand;

    /**
     * Creates a HelpCommand.
     *
     * @param targetCommand the command word to look up, or empty string to show all commands
     */
    public HelpCommand(String targetCommand) {
        requireNonNull(targetCommand);
        this.targetCommand = targetCommand.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (targetCommand.isEmpty()) {
            return new CommandResult(appendUserGuideReference(getAllCommandsUsage()), false, false);
        } else {
            return new CommandResult(getCommandUsage(targetCommand), false, false);
        }
    }

    private static String getAllCommandsUsage() {
        return CommandRegistry.getUsageMap().values().stream()
                .collect(Collectors.joining("\n\n"));
    }

    private static String getCommandUsage(String targetCommand) {
        return CommandRegistry.getUsageMap().getOrDefault(
                targetCommand,
                "Unknown command: " + targetCommand + "\n\nType 'help' to see all available commands.");
    }

    private static String appendUserGuideReference(String helpContent) {
        return helpContent + "\n\n" + USER_GUIDE_REFERENCE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HelpCommand)) {
            return false;
        }

        HelpCommand otherHelpCommand = (HelpCommand) other;
        return targetCommand.equals(otherHelpCommand.targetCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetCommand", targetCommand)
                .toString();
    }
}

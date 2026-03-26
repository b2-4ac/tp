package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows program usage instructions for all commands or a specific command.\n"
            + "Parameters: [COMMAND_WORD] (optional)\n"
            + "Example: " + COMMAND_WORD + " (shows all commands) or " + COMMAND_WORD + " add (shows add command only)";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final String targetCommand;

    public HelpCommand(String targetCommand) {
        this.targetCommand = targetCommand;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (targetCommand.isEmpty()) {
            return new CommandResult(getAllCommandsUsage(), true, false);
        } else {
            return new CommandResult(getCommandUsage(targetCommand), false, false);
        }
    }

    private String getAllCommandsUsage() {
        return AddCommand.MESSAGE_USAGE + "\n\n"
                + DeleteCommand.MESSAGE_USAGE + "\n\n"
                + EditCommand.MESSAGE_USAGE + "\n\n"
                + NoteCommand.MESSAGE_USAGE + "\n\n"
                + StatusCommand.MESSAGE_USAGE + "\n\n"
                + MeasureCommand.MESSAGE_USAGE + "\n\n"
                + RateCommand.MESSAGE_USAGE + "\n\n"
                + LogCommand.MESSAGE_USAGE + "\n\n"
                + LastCommand.MESSAGE_USAGE + "\n\n"
                + FindCommand.MESSAGE_USAGE + "\n\n"
                + FilterCommand.MESSAGE_USAGE + "\n\n"
                + SortCommand.MESSAGE_USAGE + "\n\n"
                + ViewCommand.MESSAGE_USAGE + "\n\n"
                + ListCommand.MESSAGE_USAGE + "\n\n"
                + ClearCommand.MESSAGE_USAGE + "\n\n"
                + HelpCommand.MESSAGE_USAGE + "\n\n"
                + ExitCommand.MESSAGE_USAGE;
    }

    private String getCommandUsage(String targetCommand) {
        switch (targetCommand.toLowerCase()) {
        case AddCommand.COMMAND_WORD:
            return AddCommand.MESSAGE_USAGE;
        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.MESSAGE_USAGE;
        case EditCommand.COMMAND_WORD:
            return EditCommand.MESSAGE_USAGE;
        case FindCommand.COMMAND_WORD:
            return FindCommand.MESSAGE_USAGE;
        case ListCommand.COMMAND_WORD:
            return ListCommand.MESSAGE_USAGE;
        case ClearCommand.COMMAND_WORD:
            return ClearCommand.MESSAGE_USAGE;
        case ExitCommand.COMMAND_WORD:
            return ExitCommand.MESSAGE_USAGE;
        case NoteCommand.COMMAND_WORD:
            return NoteCommand.MESSAGE_USAGE;
        case StatusCommand.COMMAND_WORD:
            return StatusCommand.MESSAGE_USAGE;
        case MeasureCommand.COMMAND_WORD:
            return MeasureCommand.MESSAGE_USAGE;
        case RateCommand.COMMAND_WORD:
            return RateCommand.MESSAGE_USAGE;
        case FilterCommand.COMMAND_WORD:
            return FilterCommand.MESSAGE_USAGE;
        case LogCommand.COMMAND_WORD:
            return LogCommand.MESSAGE_USAGE;
        case LastCommand.COMMAND_WORD:
            return LastCommand.MESSAGE_USAGE;
        case SortCommand.COMMAND_WORD:
            return SortCommand.MESSAGE_USAGE;
        case ViewCommand.COMMAND_WORD:
            return ViewCommand.MESSAGE_USAGE;
        case COMMAND_WORD:
            return MESSAGE_USAGE;
        default:
            return "Unknown command: " + targetCommand + "\n\nType 'help' to see all available commands.";
        }
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
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_APPEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.model.person.Note;

public class NoteCommandParserTest {
    private NoteCommandParser parser = new NoteCommandParser();
    private final String nonEmptyNote = "Some note.";

    @Test
    public void parse_noteWithNotePrefix_success() {
        // With note content
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NOTE + nonEmptyNote;
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(nonEmptyNote), false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Without note content (delete)
        userInput = targetIndex.getOneBased() + " " + PREFIX_NOTE;
        expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(""), false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noteWithAppendPrefix_success() {
        // With append content
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NOTE_APPEND + nonEmptyNote;
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(nonEmptyNote), true);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Without append content (append empty string)
        userInput = targetIndex.getOneBased() + " " + PREFIX_NOTE_APPEND;
        expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(""), true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndexOrParams_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

        // No parameters
        assertParseFailure(parser, "", expectedMessage);

        // Only prefix, no index
        assertParseFailure(parser, PREFIX_NOTE + nonEmptyNote, expectedMessage);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + nonEmptyNote,
            expectedMessage);
    }

    @Test
    public void parse_bothPrefixes_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_NOTE + nonEmptyNote
                + " " + PREFIX_NOTE_APPEND + nonEmptyNote;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicateNotePrefixes_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_NOTE + "first"
                + " " + PREFIX_NOTE + "second";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOTE));
    }

    @Test
    public void parse_duplicateAppendPrefixes_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_NOTE_APPEND + "first"
                + " " + PREFIX_NOTE_APPEND + "second";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOTE_APPEND));
    }
}

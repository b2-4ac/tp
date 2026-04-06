package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Adds or appends a note to a person in PowerRoster.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or appends a note to the specified client by index number used "
            + "in the displayed client list.\n"
            + "Use 'n/' to replace/delete existing notes, or 'a/' to append to existing notes.\n"
            + "Only one prefix (n/ or a/) should be provided, not both.\n"
            + "Parameters: INDEX (must be a positive integer) [n/NOTE_CONTENT | a/NOTE_CONTENT]\n"
            + "Examples:\n" + "  " + COMMAND_WORD
            + " 1 n/Focuses on strength more. (replaces existing note)\n" + "  " + COMMAND_WORD
            + " 1 a/Goes to gym daily. (appends to existing note)";

    public static final String MESSAGE_ADD_SUCCESS = "Note added to client: %1$s";
    public static final String MESSAGE_APPEND_SUCCESS = "Note appended to client: %1$s";
    public static final String MESSAGE_NOCHANGE_SUCCESS =
            "No changes made to note for client: %1$s";
    public static final String MESSAGE_DELETE_SUCCESS = "Note deleted from client: %1$s";
    public static final String MESSAGE_NOTE_ALREADY_CLEARED = "Note is already cleared for client: %1$s";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Note: %2$s";

    private static final Logger logger = LogsCenter.getLogger(ViewCommand.class);

    private final Index index;
    private final Note note;
    private final boolean isAppend;

    /**
     * Creates an NoteCommand to add/append the specified {@code note} to the person at the
     * specified {@code index}.
     *
     * @param index of the person in the last person list to edit the note
     * @param note of the person to be updated to
     * @param isAppend whether to append (true) or replace (false) the note
     */
    public NoteCommand(Index index, Note note, boolean isAppend) {
        requireNonNull(index);
        requireNonNull(note);

        this.index = index;
        this.note = note;
        this.isAppend = isAppend;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing note command for index: " + index.getOneBased());
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Note finalNote = buildFinalNote(personToEdit);
        Person editedPerson = createPersonWithUpdatedNote(personToEdit, finalNote);
        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(generateSuccessMessage(editedPerson, personToEdit, finalNote));
    }

    private Note buildFinalNote(Person personToEdit) {
        if (!isAppend) {
            return note;
        }

        String existingNote = personToEdit.getNote().value;
        String appendedNote = note.value.trim();

        if (existingNote.isEmpty()) {
            return new Note(appendedNote);
        }
        if (appendedNote.isEmpty()) {
            return new Note(existingNote);
        }
        return new Note(existingNote + " " + appendedNote);
    }

    private Person createPersonWithUpdatedNote(Person personToEdit, Note finalNote) {
        return new Person(
                personToEdit.getId(),
                personToEdit.getName(),
                personToEdit.getGender(),
                personToEdit.getDateOfBirth(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getLocation(),
                finalNote,
                personToEdit.getPlan(),
                personToEdit.getRate(),
                personToEdit.getStatus(),
                personToEdit.getHeight(),
                personToEdit.getWeight(),
                personToEdit.getBodyFatPercentage(),
                personToEdit.getTags());
    }

    /**
     * Generates a command execution success message based on the mode and note content.
     * - Append mode: shows "Note appended"
     * - Replace/Delete mode: shows "Note added" if content is non-empty, "Note deleted" if empty
     *
     * @param editedPerson the person that was edited (only note field changed)
     * @param personToEdit the original person before editing
     * @param finalNote the final note after addition/appending
     */
    private String generateSuccessMessage(Person editedPerson, Person personToEdit, Note finalNote) {
        if (isAppend && editedPerson.getNote().value.equals(personToEdit.getNote().value)) {
            return String.format(MESSAGE_NOCHANGE_SUCCESS, Messages.format(editedPerson));
        }

        if (isAppend && !finalNote.value.isEmpty()) {
            return String.format(MESSAGE_APPEND_SUCCESS, Messages.format(editedPerson));
        }

        if (finalNote.value.isEmpty() && personToEdit.getNote().value.isEmpty()) {
            return String.format(MESSAGE_NOTE_ALREADY_CLEARED, Messages.format(editedPerson));
        }

        String message = finalNote.value.isEmpty() ? MESSAGE_DELETE_SUCCESS : MESSAGE_ADD_SUCCESS;
        return String.format(message, Messages.format(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand otherNoteCommand = (NoteCommand) other;
        return index.equals(otherNoteCommand.index) && note.equals(otherNoteCommand.note)
                && isAppend == otherNoteCommand.isAppend;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("note", note)
                .add("isAppend", isAppend)
                .toString();
    }
}

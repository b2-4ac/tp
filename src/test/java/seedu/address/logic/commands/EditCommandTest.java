package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutLogBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new WorkoutLogBook());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {

        Person editedPerson = new PersonBuilder()
                .withNote(model.getFilteredPersonList().get(0).getNote().value)
                .withPlan(model.getFilteredPersonList().get(0).getPlan().toString())
                .withRate(model.getFilteredPersonList().get(0).getRate().value)
                .withHeight(model.getFilteredPersonList().get(0).getHeight().value)
                .withWeight(model.getFilteredPersonList().get(0).getWeight().value)
                .withBodyFatPercentage(model.getFilteredPersonList().get(0).getBodyFatPercentage().value)
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = expectedEditOutcome(model.getFilteredPersonList().get(0), editedPerson, descriptor);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()),
                new UserPrefs(),
                new WorkoutLogBook());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);


    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = expectedEditOutcome(lastPerson, editedPerson, descriptor);

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = expectedEditOutcome(editedPerson, editedPerson, new EditPersonDescriptor());

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = expectedEditOutcome(personInFilteredList, editedPerson,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new WorkoutLogBook());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit client in filtered list into a duplicate in PowerRoster
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of clients in PowerRoster
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of PowerRoster list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    private String expectedEditOutcome(Person beforeEdit, Person afterEdit, EditPersonDescriptor descriptor) {
        String clientName = afterEdit.getName().toString();
        StringBuilder builder = new StringBuilder();

        appendOutcomeIfEdited(builder,
                descriptor.getName().isPresent(),
                beforeEdit.getName().equals(afterEdit.getName()),
                String.format(EditCommand.MESSAGE_NAME_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_NAME_SET_SUCCESS, clientName, afterEdit.getName()));

        appendOutcomeIfEdited(builder,
                descriptor.getGender().isPresent(),
                beforeEdit.getGender().equals(afterEdit.getGender()),
                String.format(EditCommand.MESSAGE_GENDER_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_GENDER_SET_SUCCESS, clientName, afterEdit.getGender()));

        appendOutcomeIfEdited(builder,
                descriptor.getDateOfBirth().isPresent(),
                beforeEdit.getDateOfBirth().equals(afterEdit.getDateOfBirth()),
                String.format(EditCommand.MESSAGE_DOB_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_DOB_SET_SUCCESS, clientName, afterEdit.getDateOfBirth()));

        appendOutcomeIfEdited(builder,
                descriptor.getPhone().isPresent(),
                beforeEdit.getPhone().equals(afterEdit.getPhone()),
                String.format(EditCommand.MESSAGE_PHONE_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_PHONE_SET_SUCCESS, clientName, afterEdit.getPhone()));

        appendOutcomeIfEdited(builder,
                descriptor.getEmail().isPresent(),
                beforeEdit.getEmail().equals(afterEdit.getEmail()),
                String.format(EditCommand.MESSAGE_EMAIL_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_EMAIL_SET_SUCCESS, clientName, afterEdit.getEmail()));

        appendOutcomeIfEdited(builder,
                descriptor.getAddress().isPresent(),
                beforeEdit.getAddress().equals(afterEdit.getAddress()),
                String.format(EditCommand.MESSAGE_ADDRESS_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_ADDRESS_SET_SUCCESS, clientName, afterEdit.getAddress()));

        String displayLocation = afterEdit.getLocation().value.isEmpty() ? "N/A" : afterEdit.getLocation().value;
        appendOutcomeIfEdited(builder,
                descriptor.getLocation().isPresent(),
                beforeEdit.getLocation().equals(afterEdit.getLocation()),
                String.format(EditCommand.MESSAGE_LOCATION_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_LOCATION_SET_SUCCESS, clientName, displayLocation));

        appendOutcomeIfEdited(builder,
                descriptor.getTags().isPresent(),
                beforeEdit.getTags().equals(afterEdit.getTags()),
                String.format(EditCommand.MESSAGE_TAGS_UNCHANGED, clientName),
                String.format(EditCommand.MESSAGE_TAGS_SET_SUCCESS, clientName, formatTags(afterEdit.getTags())));

        if (builder.length() == 0) {
            return String.format(EditCommand.MESSAGE_NO_CHANGES, clientName);
        }
        return builder.toString();
    }

    private void appendOutcomeIfEdited(StringBuilder builder, boolean isEdited, boolean isUnchanged,
                                       String unchangedMessage, String changedMessage) {
        if (!isEdited) {
            return;
        }
        appendLine(builder, isUnchanged ? unchangedMessage : changedMessage);
    }

    private void appendLine(StringBuilder builder, String messageLine) {
        if (builder.length() > 0) {
            builder.append("\n");
        }
        builder.append(messageLine);
    }

    private String formatTags(Set<Tag> tags) {
        if (tags.isEmpty()) {
            return "[]";
        }
        return tags.stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(java.util.stream.Collectors.joining(", ", "[", "]"));
    }

}

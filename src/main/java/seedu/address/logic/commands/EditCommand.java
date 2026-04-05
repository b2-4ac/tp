package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Height;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Plan;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Status;
import seedu.address.model.person.Weight;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing client in PowerRoster.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the details of the client identified "
                    + "by the index number used in the displayed client list. "
                    + "Existing values will be overwritten by the input values.\n"
                    + "Parameters: INDEX (must be a positive integer) " + "[" + PREFIX_NAME
                    + "NAME] " + "[" + PREFIX_GENDER + "GENDER] " + "[" + PREFIX_DOB + "DOB] " + "["
                    + PREFIX_PHONE + "PHONE] " + "[" + PREFIX_EMAIL + "EMAIL] " + "["
                    + PREFIX_ADDRESS + "ADDRESS] " + "[" + PREFIX_LOCATION + "LOCATION] " + "["
                    + PREFIX_TAG + "TAG]...\n" + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PHONE
                    + "91234567 " + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_NO_CHANGES = "No effective changes for client: %1$s";
    public static final String MESSAGE_NAME_SET_SUCCESS = "Name added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_NAME_UNCHANGED = "Name is unchanged for client: %1$s";
    public static final String MESSAGE_GENDER_SET_SUCCESS = "Gender added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_GENDER_UNCHANGED = "Gender is unchanged for client: %1$s";
    public static final String MESSAGE_DOB_SET_SUCCESS = "Date of Birth added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_DOB_UNCHANGED = "Date of Birth is unchanged for client: %1$s";
    public static final String MESSAGE_PHONE_SET_SUCCESS = "Phone added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_PHONE_UNCHANGED = "Phone is unchanged for client: %1$s";
    public static final String MESSAGE_EMAIL_SET_SUCCESS = "Email added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_EMAIL_UNCHANGED = "Email is unchanged for client: %1$s";
    public static final String MESSAGE_ADDRESS_SET_SUCCESS = "Address added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_ADDRESS_UNCHANGED = "Address is unchanged for client: %1$s";
    public static final String MESSAGE_LOCATION_SET_SUCCESS = "Location added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_LOCATION_UNCHANGED = "Location is unchanged for client: %1$s";
    public static final String MESSAGE_TAGS_SET_SUCCESS = "Tags added/updated to %2$s for client: %1$s";
    public static final String MESSAGE_TAGS_UNCHANGED = "Tags are unchanged for client: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This client already exists in PowerRoster.";

    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the client in the filtered client list to edit
     * @param editPersonDescriptor details to edit the client with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing edit command for index: " + index.getOneBased());
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.warning("Edit command failed due to invalid index: " + index.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        assert editedPerson != null : "Edited client should not be null";
        assert editedPerson.getId().equals(personToEdit.getId()) : "Client ID must remain unchanged";
        assert editedPerson.getNote().equals(personToEdit.getNote()) : "Note should remain unchanged by edit";
        assert editedPerson.getPlan().equals(personToEdit.getPlan()) : "Plan should remain unchanged by edit";
        assert editedPerson.getRate().equals(personToEdit.getRate()) : "Rate should remain unchanged by edit";
        assert editedPerson.getStatus().equals(personToEdit.getStatus()) : "Status should remain unchanged by edit";
        assert editedPerson.getHeight().equals(personToEdit.getHeight()) : "Height should remain unchanged by edit";
        assert editedPerson.getWeight().equals(personToEdit.getWeight()) : "Weight should remain unchanged by edit";
        assert editedPerson.getBodyFatPercentage().equals(personToEdit.getBodyFatPercentage())
                : "Body fat percentage should remain unchanged by edit";

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            logger.warning("Edit command failed because edited client duplicates an existing client: "
                    + editedPerson.getName());
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        String outcomeMessage = formatOutcomeMessages(personToEdit, editedPerson, editPersonDescriptor);
        logger.fine("Edit command completed for client: " + editedPerson.getName());
        return new CommandResult(outcomeMessage);
    }

    /** Returns field-specific outcomes in deterministic order based on specified edit prefixes. */
    private static String formatOutcomeMessages(Person beforeEdit, Person afterEdit,
            EditPersonDescriptor descriptor) {
        String clientName = afterEdit.getName().toString();
        StringJoiner joiner = new StringJoiner("\n");

        if (descriptor.getName().isPresent()) {
            joiner.add(beforeEdit.getName().equals(afterEdit.getName())
                    ? String.format(MESSAGE_NAME_UNCHANGED, clientName)
                    : String.format(MESSAGE_NAME_SET_SUCCESS, clientName, afterEdit.getName()));
        }
        if (descriptor.getGender().isPresent()) {
            joiner.add(beforeEdit.getGender().equals(afterEdit.getGender())
                    ? String.format(MESSAGE_GENDER_UNCHANGED, clientName)
                    : String.format(MESSAGE_GENDER_SET_SUCCESS, clientName, afterEdit.getGender()));
        }
        if (descriptor.getDateOfBirth().isPresent()) {
            joiner.add(beforeEdit.getDateOfBirth().equals(afterEdit.getDateOfBirth())
                    ? String.format(MESSAGE_DOB_UNCHANGED, clientName)
                    : String.format(MESSAGE_DOB_SET_SUCCESS, clientName, afterEdit.getDateOfBirth()));
        }
        if (descriptor.getPhone().isPresent()) {
            joiner.add(beforeEdit.getPhone().equals(afterEdit.getPhone())
                    ? String.format(MESSAGE_PHONE_UNCHANGED, clientName)
                    : String.format(MESSAGE_PHONE_SET_SUCCESS, clientName, afterEdit.getPhone()));
        }
        if (descriptor.getEmail().isPresent()) {
            joiner.add(beforeEdit.getEmail().equals(afterEdit.getEmail())
                    ? String.format(MESSAGE_EMAIL_UNCHANGED, clientName)
                    : String.format(MESSAGE_EMAIL_SET_SUCCESS, clientName, afterEdit.getEmail()));
        }
        if (descriptor.getAddress().isPresent()) {
            joiner.add(beforeEdit.getAddress().equals(afterEdit.getAddress())
                    ? String.format(MESSAGE_ADDRESS_UNCHANGED, clientName)
                    : String.format(MESSAGE_ADDRESS_SET_SUCCESS, clientName, afterEdit.getAddress()));
        }
        if (descriptor.getLocation().isPresent()) {
            joiner.add(beforeEdit.getLocation().equals(afterEdit.getLocation())
                    ? String.format(MESSAGE_LOCATION_UNCHANGED, clientName)
                    : String.format(MESSAGE_LOCATION_SET_SUCCESS, clientName, displayLocation(afterEdit)));
        }
        if (descriptor.getTags().isPresent()) {
            joiner.add(beforeEdit.getTags().equals(afterEdit.getTags())
                    ? String.format(MESSAGE_TAGS_UNCHANGED, clientName)
                    : String.format(MESSAGE_TAGS_SET_SUCCESS, clientName, formatTags(afterEdit.getTags())));
        }

        String outcome = joiner.toString();
        return outcome.isEmpty() ? String.format(MESSAGE_NO_CHANGES, clientName) : outcome;
    }

    private static String displayLocation(Person person) {
        return person.getLocation().value.isEmpty() ? "N/A" : person.getLocation().value;
    }

    private static String formatTags(Set<Tag> tags) {
        if (tags.isEmpty()) {
            return "[]";
        }
        return tags.stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit} edited with
     * {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
            EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        ClientId fixedId = personToEdit.getId();
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        DateOfBirth updatedDob =
                editPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress =
                editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Location updatedLocation =
                editPersonDescriptor.getLocation().orElse(personToEdit.getLocation());
        Note oldNote = personToEdit.getNote(); // Note is not editable through EditCommand
        Plan oldPlan = personToEdit.getPlan(); // Plan is not editable through EditCommand
        Height oldHeight = personToEdit.getHeight(); // Height is not editable through EditCommand
        Weight oldWeight = personToEdit.getWeight(); // Weight is not editable through EditCommand
        // Body fat percentage is not editable through EditCommand
        BodyFatPercentage oldBodyFatPercentage = personToEdit.getBodyFatPercentage();
        Rate oldRate = personToEdit.getRate(); // Rate is not editable through EditCommand
        Status oldStatus = personToEdit.getStatus(); // Status is not editable through EditCommand
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(fixedId, updatedName, updatedGender, updatedDob, updatedPhone, updatedEmail,
                updatedAddress, updatedLocation, oldNote, oldPlan, oldRate, oldStatus,
                oldHeight, oldWeight, oldBodyFatPercentage,
                updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor).toString();
    }

    /**
     * Stores the details to edit the client with. Each non-empty field value will replace the
     * corresponding field value of the client.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Gender gender;
        private DateOfBirth dob;
        private Phone phone;
        private Email email;
        private Address address;
        private Location location;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setDateOfBirth(toCopy.dob);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setLocation(toCopy.location);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, dob, phone, email, address, location,
                    tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setDateOfBirth(DateOfBirth dob) {
            this.dob = dob;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dob);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of {@code tags} is used
         * internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException} if
         * modification is attempted. Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags))
                    : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(gender, otherEditPersonDescriptor.gender)
                    && Objects.equals(dob, otherEditPersonDescriptor.dob)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(location, otherEditPersonDescriptor.location)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("gender", gender)
                    .add("dob", dob)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("location", location)
                    .add("tags", tags)
                    .toString();
        }
    }
}

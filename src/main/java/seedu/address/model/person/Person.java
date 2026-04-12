package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in PowerRoster.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Unique ID
    private final ClientId id;

    // Identity fields
    private final Name name;
    private final Gender gender;
    private final DateOfBirth dob;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Location location;
    private final Note note;
    private final Height height;
    private final Weight weight;
    private final BodyFatPercentage bodyFatPercentage;
    private final Plan plan;
    private final Rate rate;
    private final Status status;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a {@code Person}.
     */
    public Person(ClientId id,
            Name name,
            Gender gender,
            DateOfBirth dob,
            Phone phone,
            Email email,
            Address address,
            Location location,
            Note note,
            Plan plan,
            Rate rate,
            Status status,
            Height height,
            Weight weight,
            BodyFatPercentage bodyFatPercentage,
            Set<Tag> tags) {
        requireAllNonNull(id, name, gender, dob, phone, email, address, location, note, plan, rate, status,
            height, weight, bodyFatPercentage, tags);
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.location = location;
        this.note = note;
        this.plan = plan;
        this.rate = rate;
        this.status = status;
        this.height = height;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
        this.tags.addAll(tags);
    }

    public ClientId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public DateOfBirth getDateOfBirth() {
        return dob;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Returns the note of the person.
     */
    public Note getNote() {
        return note;
    }

    /**
     * Returns the rate of the person.
     */
    public Rate getRate() {
        return rate;
    }

    /**
     * Returns the status of the person.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the height of the person
     */
    public Height getHeight() {
        return height;
    }

    /**
     * Returns the weight of the person
     */
    public Weight getWeight() {
        return weight;
    }

    /**
     * Returns the body fat percentage of the person
     */
    public BodyFatPercentage getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    /**
     * Returns the workout plan of the person.
     */
    public Plan getPlan() {
        return plan;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the current client is active.
     */
    public boolean isActive() {
        return status.value.equals(Status.StatusEnum.ACTIVE);
    }

    /**
     * Returns a {@link Builder} pre-populated with this person's fields.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Returns a copy of this person with the given status.
     */
    public Person withStatus(Status newStatus) {
        requireAllNonNull(newStatus);
        return toBuilder().setStatus(newStatus).build();
    }

    /**
     * Returns a copy of this person with the given note.
     */
    public Person withNote(Note newNote) {
        requireAllNonNull(newNote);
        return toBuilder().setNote(newNote).build();
    }

    /**
     * Returns a copy of this person with the given rate.
     */
    public Person withRate(Rate newRate) {
        requireAllNonNull(newRate);
        return toBuilder().setRate(newRate).build();
    }

    /**
     * Returns a copy of this person with the given plan.
     */
    public Person withPlan(Plan newPlan) {
        requireAllNonNull(newPlan);
        return toBuilder().setPlan(newPlan).build();
    }

    /**
     * Returns a copy of this person with the given measurements.
     */
    public Person withMeasurements(Height newHeight, Weight newWeight, BodyFatPercentage newBodyFatPercentage) {
        requireAllNonNull(newHeight, newWeight, newBodyFatPercentage);
        return toBuilder().setHeight(newHeight).setWeight(newWeight).setBodyFatPercentage(newBodyFatPercentage).build();
    }

    /**
     * Builder for creating modified copies of a {@link Person}.
     * Use {@link Person#toBuilder()} to obtain a pre-populated instance.
     */
    public static class Builder {
        private ClientId id;
        private Name name;
        private Gender gender;
        private DateOfBirth dob;
        private Phone phone;
        private Email email;
        private Address address;
        private Location location;
        private Note note;
        private Plan plan;
        private Rate rate;
        private Status status;
        private Height height;
        private Weight weight;
        private BodyFatPercentage bodyFatPercentage;
        private Set<Tag> tags;

        /** Copy constructor — copies all fields from {@code person}. */
        public Builder(Person person) {
            this.id = person.id;
            this.name = person.name;
            this.gender = person.gender;
            this.dob = person.dob;
            this.phone = person.phone;
            this.email = person.email;
            this.address = person.address;
            this.location = person.location;
            this.note = person.note;
            this.plan = person.plan;
            this.rate = person.rate;
            this.status = person.status;
            this.height = person.height;
            this.weight = person.weight;
            this.bodyFatPercentage = person.bodyFatPercentage;
            this.tags = new HashSet<>(person.tags);
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setNote(Note note) {
            this.note = note;
            return this;
        }

        public Builder setRate(Rate rate) {
            this.rate = rate;
            return this;
        }

        public Builder setPlan(Plan plan) {
            this.plan = plan;
            return this;
        }

        public Builder setHeight(Height height) {
            this.height = height;
            return this;
        }

        public Builder setWeight(Weight weight) {
            this.weight = weight;
            return this;
        }

        public Builder setBodyFatPercentage(BodyFatPercentage bodyFatPercentage) {
            this.bodyFatPercentage = bodyFatPercentage;
            return this;
        }

        /** Builds and returns the new {@link Person}. */
        public Person build() {
            return new Person(id, name, gender, dob, phone, email, address, location,
                    note, plan, rate, status, height, weight, bodyFatPercentage, tags);
        }
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same ID.
     * This defines a weaker notion of equality between two persons.
     * This is used to check if two Person objects refer to the same real-world person, even if their names are changed.
     */
    public boolean isSamePersonId(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getId().equals(getId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && gender.equals(otherPerson.gender)
                && dob.equals(otherPerson.dob)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && location.equals(otherPerson.location)
                && note.equals(otherPerson.note)
                && plan.equals(otherPerson.plan)
                && rate.equals(otherPerson.rate)
                && status.equals(otherPerson.status)
                && height.equals(otherPerson.height)
                && weight.equals(otherPerson.weight)
                && bodyFatPercentage.equals(otherPerson.bodyFatPercentage)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, gender, dob, phone, email, address, location,
                note, plan, rate, status, height, weight, bodyFatPercentage, tags);
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
                .add("note", note)
                .add("plan", plan)
                .add("rate", rate)
                .add("status", status)
                .add("height", height)
                .add("weight", weight)
                .add("bodyFatPercentage", bodyFatPercentage)
                .add("tags", tags)
                .toString();
    }

}

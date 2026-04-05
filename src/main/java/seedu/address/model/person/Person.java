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
        requireAllNonNull(id, name, gender, phone, email, address, location, note, plan, rate, status,
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

package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_ID = "f0962b9a-41d3-4627-897b-944a195b2173";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_DOB = "24/04/1976";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_LOCATION = "ActiveSG @ Fernvale Square";
    public static final String DEFAULT_NOTE = "";
    public static final String DEFAULT_PLAN = "Unassigned";
    public static final String DEFAULT_RATE = "";
    public static final String DEFAULT_STATUS = "active";
    public static final String DEFAULT_HEIGHT = "";
    public static final String DEFAULT_WEIGHT = "";
    public static final String DEFAULT_BODY_FAT = "";

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

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        id = new ClientId(DEFAULT_ID);
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        dob = new DateOfBirth(DEFAULT_DOB);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        location = new Location(DEFAULT_LOCATION);
        note = new Note(DEFAULT_NOTE);
        plan = new Plan(DEFAULT_PLAN);
        rate = new Rate(DEFAULT_RATE);
        status = new Status(DEFAULT_STATUS);
        height = new Height(DEFAULT_HEIGHT);
        weight = new Weight(DEFAULT_WEIGHT);
        bodyFatPercentage = new BodyFatPercentage(DEFAULT_BODY_FAT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        id = personToCopy.getId();
        name = personToCopy.getName();
        gender = personToCopy.getGender();
        dob = personToCopy.getDateOfBirth();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        location = personToCopy.getLocation();
        note = personToCopy.getNote();
        plan = personToCopy.getPlan();
        height = personToCopy.getHeight();
        weight = personToCopy.getWeight();
        bodyFatPercentage = personToCopy.getBodyFatPercentage();
        rate = personToCopy.getRate();
        status = personToCopy.getStatus();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(String dob) {
        this.dob = new DateOfBirth(dob);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are
     * building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Person} that we are building.
     */
    public PersonBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code ClientId} of the {@code Person} that we are building.
     */
    public PersonBuilder withId(String id) {
        this.id = new ClientId(id);
        return this;
    }

    /**
     * Sets the {@code Height} of the {@code Person} that we are building.
     */
    public PersonBuilder withHeight(String height) {
        this.height = new Height(height);
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code Person} that we are building.
     */
    public PersonBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }

    /**
     * Sets the {@code BodyFatPercentage} of the {@code Person} that we are building.
     */
    public PersonBuilder withBodyFatPercentage(String bodyFatPercentage) {
        this.bodyFatPercentage = new BodyFatPercentage(bodyFatPercentage);
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code Person} that we are building.
     */
    public PersonBuilder withRate(String rate) {
        this.rate = new Rate(rate);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public PersonBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Plan} of the {@code Person} that we are building.
     */
    public PersonBuilder withPlan(String plan) {
        this.plan = new Plan(plan);
        return this;
    }

    /**
     * Builds and returns a {@code Person} instance with the configured fields.
     */
    public Person build() {
        return new Person(id, name, gender, dob, phone, email, address, location,
                note, plan, rate, status, height, weight, bodyFatPercentage, tags);
    }

}

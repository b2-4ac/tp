package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withGender("F")
            .withDateOfBirth("14/10/1992")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withLocation("Jurong West ActiveSG Gym")
            .withNote("Likes morning workouts")
            .withTags("friends")
            .build();
    public static final Person BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withGender("M")
            .withDateOfBirth("03/03/1985")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withLocation("Clementi ActiveSG Gym")
            .withNote("Focuses on strength training")
            .withTags("friends")
            .build();
    public static final Person CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withGender("M")
            .withDateOfBirth("22/07/2001")
            .withAddress("wall street")
            .withEmail("heinz@example.com")
            .withPhone("95352563")
            .withLocation("Anytime Fitness Tampines East")
            .withNote("Enjoys cardio workouts")
            .build();
    public static final Person DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withGender("M")
            .withDateOfBirth("11/11/1978")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withLocation("Anytime Fitness Marine Parade")
            .withNote("Enjoys cardio and yoga")
            .withTags("friends")
            .build();
    public static final Person ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withGender("F")
            .withDateOfBirth("29/02/1996")
            .withPhone("94822242")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withLocation("Anytime Fitness Buona Vista")
            .withNote("Loves HIIT")
            .build();
    public static final Person FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withGender("F")
            .withDateOfBirth("05/08/2010")
            .withPhone("94824275")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withLocation("Anytime Fitness Taman Jurong")
            .withNote("Enjoys group fitness classes")
            .build();
    public static final Person GEORGE = new PersonBuilder()
            .withName("George Best")
            .withGender("M")
            .withDateOfBirth("19/05/1999")
            .withPhone("94824423")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withLocation("Anytime Fitness Lower Peirce")
            .withNote("Focuses on cardio and HIIT workouts")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withGender("F")
            .withDateOfBirth("25/03/1992")
            .withPhone("84824242")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withLocation("Heartbeat @ Bedok ActiveSG Gym")
            .withNote("Interested in martial arts")
            .build();
    public static final Person IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withGender("F")
            .withDateOfBirth("15/06/1998")
            .withPhone("84821317")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withLocation("Jurong Lake Gardens ActiveSG Gym")
            .withNote("Enjoys cardio")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withGender(VALID_GENDER_AMY)
            .withDateOfBirth(VALID_DOB_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withLocation(VALID_LOCATION_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();
    public static final Person BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withGender(VALID_GENDER_BOB)
            .withDateOfBirth(VALID_DOB_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withLocation(VALID_LOCATION_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

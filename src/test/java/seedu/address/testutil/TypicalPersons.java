package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
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
            .withId("4f63c6d5-a3d1-4e78-bc4a-bf60a92f8087")
            .withName("Alice Pauline")
            .withGender("F")
            .withDateOfBirth("14/10/1992")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withLocation("Jurong West ActiveSG Gym")
            .withNote("Likes morning workouts")
            .withPlan("PUSH")
            .withTags("friends")
            .build();
    public static final Person BENSON = new PersonBuilder()
            .withId("524f0c90-efc4-4c6e-8270-e5550a26d701")
            .withName("Benson Meier")
            .withGender("M")
            .withDateOfBirth("03/03/1985")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withLocation("Clementi ActiveSG Gym")
            .withNote("Focuses on strength training")
            .withPlan("PULL")
            .withTags("friends")
            .build();
    public static final Person CARL = new PersonBuilder()
            .withId("d3e9f4c1-4b12-40e1-959c-7e61b7b75294")
            .withName("Carl Kurz")
            .withGender("M")
            .withDateOfBirth("22/07/2001")
            .withAddress("wall street")
            .withEmail("heinz@example.com")
            .withPhone("95352563")
            .withLocation("Anytime Fitness Tampines East")
            .withNote("Enjoys cardio workouts")
            .withPlan("CARDIO")
            .build();
    public static final Person DANIEL = new PersonBuilder()
            .withId("ae8677c3-2287-4303-9d41-3510e194916a")
            .withName("Daniel Meier")
            .withGender("M")
            .withDateOfBirth("11/11/1978")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withLocation("Anytime Fitness Marine Parade")
            .withNote("Enjoys cardio and yoga")
            .withPlan("LEGS")
            .withTags("friends")
            .build();
    public static final Person ELLE = new PersonBuilder()
            .withId("1a4c9c64-0715-467f-8d2b-18a7a0b3f52a")
            .withName("Elle Meyer")
            .withGender("F")
            .withDateOfBirth("29/02/1996")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withLocation("Anytime Fitness Buona Vista")
            .withNote("Loves HIIT")
            .withPlan("FULL_BODY")
            .build();
    public static final Person FIONA = new PersonBuilder()
            .withId("89b9d15e-a28a-40a8-b649-e26b47c47d77")
            .withName("Fiona Kunz")
            .withGender("F")
            .withDateOfBirth("05/08/2010")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withLocation("Anytime Fitness Buona Vista")
            .withNote("Enjoys group fitness classes")
            .withPlan("CORE")
            .build();
    public static final Person GEORGE = new PersonBuilder()
            .withId("24f9f7a7-53ef-4f1d-b8d9-601e3097c55c")
            .withName("George Best")
            .withGender("M")
            .withDateOfBirth("19/05/1999")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withLocation("Anytime Fitness Lower Peirce")
            .withNote("Focuses on cardio and HIIT workouts")
            .withPlan("CONDITIONING")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withId("0965d214-e054-4696-9818-4775432a5275")
            .withName("Hoon Meier")
            .withGender("F")
            .withDateOfBirth("25/03/1992")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withLocation("Heartbeat @ Bedok ActiveSG Gym")
            .withNote("Interested in martial arts")
            .withPlan("CARDIO")
            .build();
    public static final Person IDA = new PersonBuilder()
            .withId("7efd3761-9c60-4592-8086-4416a9282360")
            .withName("Ida Mueller")
            .withGender("F")
            .withDateOfBirth("15/06/1998")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withLocation("Jurong Lake Gardens ActiveSG Gym")
            .withNote("Enjoys cardio")
            .withPlan("CONDITIONING")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withId(VALID_ID_AMY)
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
            .withId(VALID_ID_BOB)
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

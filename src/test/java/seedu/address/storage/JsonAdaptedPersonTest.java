package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
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
import seedu.address.model.person.Weight;

public class JsonAdaptedPersonTest {
    private static final String INVALID_ID = "123-456-789";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GENDER = "asdf";
    private static final String INVALID_DOB = "25-7-12";
    private static final String INVALID_PHONE = "++651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_LOCATION = "/";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PLAN = "   ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_HEIGHT = "49.9";
    private static final String INVALID_WEIGHT = "19.9";
    private static final String INVALID_BODY_FAT = "80.0";

    private static final String VALID_ID = BENSON.getId().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_DOB = BENSON.getDateOfBirth().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_LOCATION = BENSON.getLocation().toString();
    private static final String VALID_NOTE = BENSON.getNote().toString();
    private static final String VALID_PLAN = BENSON.getPlan().toString();
    private static final String VALID_HEIGHT = BENSON.getHeight().toString();
    private static final String VALID_WEIGHT = BENSON.getWeight().toString();
    private static final String VALID_BODY_FAT = BENSON.getBodyFatPercentage().toString();
    private static final String VALID_RATE = BENSON.getRate().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                INVALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = ClientId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClientId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                INVALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                null,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                INVALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                null,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                INVALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                null,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                INVALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                null,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                INVALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                null,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                INVALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                null,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                INVALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                null,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                null,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPlan_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                INVALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Plan.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPlan_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                null,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Plan.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRate_defaultsToEmptyRate() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                null,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStatus_defaultsToActive() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                null,
                VALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        Person convertedPerson = person.toModelType();
        assertEquals("Active", convertedPerson.getStatus().toString());
    }

    @Test
    public void toModelType_invalidHeight_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                INVALID_HEIGHT,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Height.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullHeight_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                null,
                VALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Height.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidWeight_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                INVALID_WEIGHT,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = Weight.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullWeight_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                null,
                VALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Weight.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidBodyFatPercentage_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                INVALID_BODY_FAT,
                VALID_TAGS);
        String expectedMessage = BodyFatPercentage.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullBodyFatPercentage_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_ID,
                VALID_NAME,
                VALID_GENDER,
                VALID_DOB,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_LOCATION,
                VALID_NOTE,
                VALID_PLAN,
                VALID_RATE,
                VALID_STATUS,
                VALID_HEIGHT,
                VALID_WEIGHT,
                null,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                BodyFatPercentage.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}

package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Email;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Weight;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "++651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_RATE = "120.000";
    private static final String INVALID_HEIGHT = "49.9";
    private static final String INVALID_WEIGHT = "10.0";
    private static final String INVALID_BODY_FAT = "90.0";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_RATE = "120.";
    private static final String VALID_RATE_WITHOUT_TRAILING_ZERO = ".5";
    private static final String VALID_HEIGHT = "175.5";
    private static final String VALID_WEIGHT = "72.0";
    private static final String VALID_BODY_FAT = "14.8";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        // EP: non-numeric index input
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        // EP: numeric input outside int range
        // BVA: just above Integer.MAX_VALUE boundary
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        // EP: valid one-based index
        // BVA: lower valid boundary (1)
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        // EP: null input partition
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        // EP: invalid name format
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        // EP: valid content with leading/trailing whitespace
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        // EP: null input partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        // EP: invalid phone format.
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        // EP: valid phone without surrounding whitespace.
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        // EP: valid phone with surrounding whitespace (trim behavior).
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        // EP: null input partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        // EP: invalid address value.
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        // EP: valid address without surrounding whitespace.
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        // EP: valid address with surrounding whitespace (trim behavior).
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        // EP: null input partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        // EP: invalid email format.
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        // EP: valid email without surrounding whitespace.
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        // EP: valid email with surrounding whitespace (trim behavior).
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseRate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRate((String) null));
    }

    @Test
    public void parseRate_invalidValue_throwsParseException() {
        // EP: invalid decimal precision for rate
        assertThrows(ParseException.class, () -> ParserUtil.parseRate(INVALID_RATE));
    }

    @Test
    public void parseRate_emptyValue_returnsEmptyRate() throws Exception {
        // EP: empty string to clear rate
        // BVA: empty string boundary
        Rate expectedRate = new Rate("");
        assertEquals(expectedRate, ParserUtil.parseRate(""));
    }

    @Test
    public void parseRate_validValueWithoutWhitespace_returnsNormalisedRate() throws Exception {
        // EP: valid numeric input
        Rate expectedRate = new Rate("120.00");
        assertEquals(expectedRate, ParserUtil.parseRate(VALID_RATE));
    }

    @Test
    public void parseRate_validValueWithWhitespace_returnsTrimmedNormalisedRate() throws Exception {
        // EP: valid numeric input with surrounding whitespace to test trimming
        // BVA: decimal without leading zero (".5")
        String rateWithWhitespace = WHITESPACE + VALID_RATE_WITHOUT_TRAILING_ZERO + WHITESPACE;
        Rate expectedRate = new Rate("0.50");
        assertEquals(expectedRate, ParserUtil.parseRate(rateWithWhitespace));
    }

    @Test
    public void parseHeight_null_throwsNullPointerException() {
        // EP: null input partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHeight((String) null));
    }

    @Test
    public void parseHeight_invalidValue_throwsParseException() {
        // EP: invalid height value.
        // BVA: below lower bound.
        assertThrows(ParseException.class, () -> ParserUtil.parseHeight(INVALID_HEIGHT));
    }

    @Test
    public void parseHeight_validValueWithoutWhitespace_returnsHeight() throws Exception {
        // EP: valid height without surrounding whitespace.
        Height expectedHeight = new Height(VALID_HEIGHT);
        assertEquals(expectedHeight, ParserUtil.parseHeight(VALID_HEIGHT));
    }

    @Test
    public void parseHeight_validValueWithWhitespace_returnsTrimmedHeight() throws Exception {
        // EP: valid height with surrounding whitespace (trim behavior).
        String heightWithWhitespace = WHITESPACE + VALID_HEIGHT + WHITESPACE;
        Height expectedHeight = new Height(VALID_HEIGHT);
        assertEquals(expectedHeight, ParserUtil.parseHeight(heightWithWhitespace));
    }

    @Test
    public void parseWeight_null_throwsNullPointerException() {
        // EP: null input partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWeight((String) null));
    }

    @Test
    public void parseWeight_invalidValue_throwsParseException() {
        // EP: invalid weight value.
        // BVA: below lower bound.
        assertThrows(ParseException.class, () -> ParserUtil.parseWeight(INVALID_WEIGHT));
    }

    @Test
    public void parseWeight_validValueWithoutWhitespace_returnsWeight() throws Exception {
        // EP: valid weight without surrounding whitespace.
        Weight expectedWeight = new Weight(VALID_WEIGHT);
        assertEquals(expectedWeight, ParserUtil.parseWeight(VALID_WEIGHT));
    }

    @Test
    public void parseWeight_validValueWithWhitespace_returnsTrimmedWeight() throws Exception {
        // EP: valid weight with surrounding whitespace (trim behavior).
        String weightWithWhitespace = WHITESPACE + VALID_WEIGHT + WHITESPACE;
        Weight expectedWeight = new Weight(VALID_WEIGHT);
        assertEquals(expectedWeight, ParserUtil.parseWeight(weightWithWhitespace));
    }

    @Test
    public void parseBodyFatPercentage_null_throwsNullPointerException() {
        // EP: null input partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBodyFatPercentage((String) null));
    }

    @Test
    public void parseBodyFatPercentage_invalidValue_throwsParseException() {
        // EP: invalid body-fat value.
        // BVA: above upper bound.
        assertThrows(ParseException.class, () -> ParserUtil.parseBodyFatPercentage(INVALID_BODY_FAT));
    }

    @Test
    public void parseBodyFatPercentage_validValueWithoutWhitespace_returnsBodyFatPercentage() throws Exception {
        // EP: valid body-fat value without surrounding whitespace.
        BodyFatPercentage expectedBodyFatPercentage = new BodyFatPercentage(VALID_BODY_FAT);
        assertEquals(expectedBodyFatPercentage, ParserUtil.parseBodyFatPercentage(VALID_BODY_FAT));
    }

    @Test
    public void parseBodyFatPercentage_validValueWithWhitespace_returnsTrimmedBodyFatPercentage() throws Exception {
        // EP: valid body-fat value with surrounding whitespace (trim behavior).
        String bodyFatWithWhitespace = WHITESPACE + VALID_BODY_FAT + WHITESPACE;
        BodyFatPercentage expectedBodyFatPercentage = new BodyFatPercentage(VALID_BODY_FAT);
        assertEquals(expectedBodyFatPercentage, ParserUtil.parseBodyFatPercentage(bodyFatWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        // EP: null input partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        // EP: invalid tag format.
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        // EP: valid tag without surrounding whitespace.
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        // EP: valid tag with surrounding whitespace (trim behavior).
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        // EP: null collection partition.
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        // EP: mixed-validity collection contains at least one invalid tag
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        // EP: empty collection partition
        // BVA: zero-sized collection
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        // EP: collection with only valid tags.
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}

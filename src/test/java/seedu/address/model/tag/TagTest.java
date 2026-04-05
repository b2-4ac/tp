package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // space only
        assertFalse(Tag.isValidTagName("-tag")); // starts with hyphen
        assertFalse(Tag.isValidTagName("-")); // hyphen only

        // valid tag names
        assertTrue(Tag.isValidTagName("tag")); // single word
        assertTrue(Tag.isValidTagName("123")); // numbers only
        assertTrue(Tag.isValidTagName("123test")); // numbers and letters
        assertTrue(Tag.isValidTagName("tag name")); // with spaces
        assertTrue(Tag.isValidTagName("tag-name")); // with hyphen
        assertTrue(Tag.isValidTagName("tag-name with spa-ces")); // with hyphens and spaces
        assertTrue(Tag.isValidTagName("a-b-c")); // multiple hyphens
    }

}

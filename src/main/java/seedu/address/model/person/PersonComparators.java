package seedu.address.model.person;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Utility class providing comparators for sorting Person objects by various attributes.
 * This class encapsulates the business logic for creating comparators,
 * following the Single Responsibility Principle.
 */
public class PersonComparators {

    /** Attribute name for sorting by person name. */
    public static final String ATTRIBUTE_NAME = "name";

    /** Attribute name for sorting by location. */
    public static final String ATTRIBUTE_LOCATION = "location";

    /** Attribute name for sorting by date of birth. */
    public static final String ATTRIBUTE_DOB = "date of birth";

    /** Attribute name for sorting by phone number. */
    public static final String ATTRIBUTE_PHONE = "phone";

    /** Attribute name for sorting by email. */
    public static final String ATTRIBUTE_EMAIL = "email";

    /** Attribute name for sorting by address. */
    public static final String ATTRIBUTE_ADDRESS = "address";

    /** Attribute name for sorting by gender. */
    public static final String ATTRIBUTE_GENDER = "gender";

    /** Order constant for ascending sort. */
    public static final String ORDER_ASC = "asc";

    /** Order constant for descending sort. */
    public static final String ORDER_DESC = "desc";

    /** Default sort order when not specified. */
    public static final String DEFAULT_ORDER = ORDER_ASC;

    private static final Logger logger = LogsCenter.getLogger(PersonComparators.class);

    /**
     * Container class for an attribute comparator with its display name.
     */
    public static class AttributeComparator {
        private final String attributeName;
        private final Comparator<Person> comparator;

        /**
         * Creates an AttributeComparator.
         *
         * @param attributeName The display name of the attribute.
         * @param comparator The comparator for sorting by this attribute.
         */
        public AttributeComparator(String attributeName, Comparator<Person> comparator) {
            this.attributeName = attributeName;
            this.comparator = comparator;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public Comparator<Person> getComparator() {
            return comparator;
        }
    }

    private static final Map<String, AttributeComparator> COMPARATORS = new HashMap<>();

    static {
        COMPARATORS.put(ATTRIBUTE_NAME, new AttributeComparator(ATTRIBUTE_NAME,
                Comparator.comparing(p -> p.getName().fullName.toLowerCase())));
        COMPARATORS.put(ATTRIBUTE_LOCATION, new AttributeComparator(ATTRIBUTE_LOCATION,
                Comparator.comparing(p -> p.getLocation().value.toLowerCase())));
        COMPARATORS.put(ATTRIBUTE_DOB, new AttributeComparator(ATTRIBUTE_DOB,
                Comparator.comparing(p -> p.getDateOfBirth().value)));
        COMPARATORS.put(ATTRIBUTE_PHONE, new AttributeComparator(ATTRIBUTE_PHONE,
                Comparator.comparing(p -> p.getPhone().value)));
        COMPARATORS.put(ATTRIBUTE_EMAIL, new AttributeComparator(ATTRIBUTE_EMAIL,
                Comparator.comparing(p -> p.getEmail().value.toLowerCase())));
        COMPARATORS.put(ATTRIBUTE_ADDRESS, new AttributeComparator(ATTRIBUTE_ADDRESS,
                Comparator.comparing(p -> p.getAddress().value.toLowerCase())));
        COMPARATORS.put(ATTRIBUTE_GENDER, new AttributeComparator(ATTRIBUTE_GENDER,
                Comparator.comparing(p -> p.getGender().value)));
    }

    /**
     * Returns the comparator for a given attribute, optionally reversed for descending order.
     *
     * @param attribute The attribute name to sort by.
     * @param isDescending Whether to sort in descending order.
     * @return The appropriate comparator, or null if attribute not found.
     */
    public static Comparator<Person> getComparator(String attribute, boolean isDescending) {
        assert attribute != null : "Attribute cannot be null";
        logger.fine("Getting comparator for attribute: " + attribute + ", descending: " + isDescending);

        AttributeComparator attrComparator = COMPARATORS.get(attribute);
        if (attrComparator == null) {
            logger.warning("No comparator found for attribute: " + attribute);
            return null;
        }

        Comparator<Person> comparator = attrComparator.getComparator();
        assert comparator != null : "Base comparator should not be null";
        return isDescending ? comparator.reversed() : comparator;
    }

    /**
     * Returns the attribute comparator for a given attribute key.
     *
     * @param attribute The attribute name.
     * @return The AttributeComparator, or null if not found.
     */
    public static AttributeComparator getAttributeComparator(String attribute) {
        return COMPARATORS.get(attribute);
    }

    /**
     * Checks if the given attribute is supported for sorting.
     *
     * @param attribute The attribute name to check.
     * @return True if the attribute is supported, false otherwise.
     */
    public static boolean isValidAttribute(String attribute) {
        assert attribute != null : "Attribute cannot be null";
        return COMPARATORS.containsKey(attribute);
    }
}

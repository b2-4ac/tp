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

    /** Attribute name for sorting by status. */
    public static final String ATTRIBUTE_STATUS = "status";

    /** Attribute name for sorting by workout plan. */
    public static final String ATTRIBUTE_PLAN = "plan";

    /** Attribute name for sorting by session rate. */
    public static final String ATTRIBUTE_RATE = "rate";

    /** Order constant for ascending sort. */
    public static final String ORDER_ASC = "asc";

    /** Order constant for descending sort. */
    public static final String ORDER_DESC = "desc";

    /** Default sort order when not specified. */
    public static final String DEFAULT_ORDER = ORDER_ASC;

    private static final Logger logger = LogsCenter.getLogger(PersonComparators.class);

    private static final Map<String, Comparator<Person>> COMPARATORS = new HashMap<>();

    static {
        COMPARATORS.put(ATTRIBUTE_NAME, Comparator.comparing(p -> p.getName().fullName.toLowerCase()));
        COMPARATORS.put(ATTRIBUTE_LOCATION, Comparator.comparing(p -> p.getLocation().value.toLowerCase()));
        COMPARATORS.put(ATTRIBUTE_DOB, Comparator.comparing(p -> p.getDateOfBirth().value));
        COMPARATORS.put(ATTRIBUTE_PHONE, Comparator.comparing(p -> p.getPhone().value));
        COMPARATORS.put(ATTRIBUTE_EMAIL, Comparator.comparing(p -> p.getEmail().value.toLowerCase()));
        COMPARATORS.put(ATTRIBUTE_ADDRESS, Comparator.comparing(p -> p.getAddress().value.toLowerCase()));
        COMPARATORS.put(ATTRIBUTE_GENDER, Comparator.comparing(p -> p.getGender().value));
        COMPARATORS.put(ATTRIBUTE_STATUS, Comparator.comparing(p -> p.getStatus().value.name()));
        COMPARATORS.put(ATTRIBUTE_PLAN, Comparator.comparing(p -> p.getPlan().value.name()));
        COMPARATORS.put(ATTRIBUTE_RATE, Comparator.comparingDouble((Person p) ->
                p.getRate().value.isEmpty() ? -1.0 : Double.parseDouble(p.getRate().value)));
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

        Comparator<Person> comparator = COMPARATORS.get(attribute);
        if (comparator == null) {
            logger.warning("No comparator found for attribute: " + attribute);
            return null;
        }

        return isDescending ? comparator.reversed() : comparator;
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

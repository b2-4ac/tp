package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's assigned workout plan in PowerRoster.
 * Guarantees: immutable; is valid as declared in {@link #isValidPlan(String)}
 */
public class Plan {
    public static final String MESSAGE_CONSTRAINTS =
            "Plan should be exactly one category: PUSH, PULL, LEGS, CORE, CARDIO, MOBILITY, FULL BODY, "
                    + "or CONDITIONING.";
    public static final String DEFAULT_PLAN_TEXT = "Unassigned";

    public final PlanCategoryEnum value;

    /**
     * Constructs a {@code Plan}.
     */
    public Plan(String plan) {
        requireNonNull(plan);
        String normalizedPlan = normalizeWhitespace(plan);
        checkArgument(isValidPlan(normalizedPlan), MESSAGE_CONSTRAINTS);
        if (DEFAULT_PLAN_TEXT.equalsIgnoreCase(normalizedPlan)) {
            value = PlanCategoryEnum.UNASSIGNED;
        } else {
            value = PlanCategoryEnum.fromString(normalizedPlan);
        }
    }

    /**
     * Returns true if this plan is unassigned.
     */
    public boolean isUnassigned() {
        return value == PlanCategoryEnum.UNASSIGNED;
    }

    /**
     * Returns a boolean indicating whether the given string is a valid plan category.
     * @param test The string to test.
     * @return True if the string is a valid plan category or the default unassigned text, false otherwise.
     */
    public static boolean isValidPlan(String test) {
        if (test == null) {
            return false;
        }

        if (DEFAULT_PLAN_TEXT.equalsIgnoreCase(test)) {
            return true;
        }

        try {
            PlanCategoryEnum.fromString(test);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Returns a default unassigned plan used for existing data and new entries.
     */
    public static Plan getDefaultPlan() {
        return new Plan(DEFAULT_PLAN_TEXT);
    }


    private static String normalizeWhitespace(String text) {
        return text.trim().replaceAll("\\s+", " ");
    }

    @Override
    public String toString() {
        return value == PlanCategoryEnum.UNASSIGNED
                ? DEFAULT_PLAN_TEXT
                : value.toString().replace('_', ' ');
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Plan
                && value == ((Plan) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

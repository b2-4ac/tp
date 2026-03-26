package seedu.address.model.person;

/**
 * Supported workout exercise categories for plan entries.
 */
public enum PlanCategoryEnum {
    PUSH,
    PULL,
    LEGS,
    CORE,
    CARDIO,
    MOBILITY,
    FULL_BODY,
    CONDITIONING,
    UNASSIGNED;

    /**
     * Parses a category in a case-insensitive manner.
     */
    public static PlanCategoryEnum fromString(String rawCategory) {
        String normalized = rawCategory.trim()
                .replaceAll("[\\s-]+", "_")
                .toUpperCase();
        if ("UNASSIGNED".equals(normalized)) {
            return UNASSIGNED;
        }
        return PlanCategoryEnum.valueOf(normalized);
    }
}




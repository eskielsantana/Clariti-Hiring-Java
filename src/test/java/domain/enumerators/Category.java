package domain.enumerators;

public enum Category {
    ABM("ABM"),
    PRE_SALES("Pre Sales"),
    SALES_ENGINEERGING("Sales Engineering"),
    CODING("Coding"),
    QUALITY_ASSURANCE("Quality Assurance"),
    HUMAN_RESOURCES("Human Resources"),
    PERFORMANCE_MANAGEMENT("Performance Management"),
    TIER_1("Tier 1"),
    TIER_2("Tier 2"),
    TIER_3("Tier 3");

    public final String name;

    Category(String name) {
        this.name = name;
    }
}

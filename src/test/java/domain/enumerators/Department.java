package domain.enumerators;

import java.util.Arrays;
import java.util.List;

import static domain.helper.RandomHelper.randomElement;

public enum Department {
    MARKETING("Marketing", Category.ABM),
    SALES("Sales", Category.PRE_SALES, Category.SALES_ENGINEERGING),
    DEVELOPMENT("Development", Category.CODING, Category.QUALITY_ASSURANCE),
    OPERATIONS("Operations", Category.HUMAN_RESOURCES, Category.PERFORMANCE_MANAGEMENT),
    SUPPORT("Support", Category.TIER_1, Category.TIER_2, Category.TIER_3);

    public final String name;
    public final List<Category> categories;

    Department(String name, Category... categories) {
        this.name = name;
        this.categories = Arrays.asList(categories);
    }

    public Category getRandomCategory() {
        return randomElement(categories);
    }
    public static Department getRandomDepartment() {
        return randomElement(Department.values());
    }
}

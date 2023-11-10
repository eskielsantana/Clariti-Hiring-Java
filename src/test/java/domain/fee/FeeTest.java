package domain.fee;

import domain.enumerators.Category;
import domain.enumerators.Department;
import org.junit.Test;

import static domain.RandomHelper.randomDouble;
import static domain.RandomHelper.randomNumber;
import static domain.helper.CurrencyHelper.currencyFormat;
import static domain.fee.FeeFactory.getRandomFeeId;
import static domain.fee.FeeFactory.getRandomFeeSubCategory;
import static domain.fee.FeeFactory.getRandomFeeType;
import static domain.helper.MathHelper.percentDifference;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FeeTest {

    @Test
    public void constructor_WhenInstantiated_FeeObjectHasAllValuesProperlyAssigned() {
        String id = getRandomFeeId(), name = "Test Fee 1", description = "This is a test fee";
        String subCat = getRandomFeeSubCategory(), type = getRandomFeeType();

        Department department = Department.DEVELOPMENT;
        Category category = department.getRandomCategory();

        int quantity = randomNumber(1, 10);
        double price = randomDouble(1, 100);
        double departmentSubCharge = 1.2;
        double finalPrice = price * quantity * departmentSubCharge;

        Fee fee = new Fee(id, name, description, department.name, category.name, subCat, type, quantity, price);

        assertEquals(id, fee.getId());
        assertEquals(name, fee.getName());
        assertEquals(description, fee.getDescription());
        assertEquals(department.name, fee.getDepartment());
        assertEquals(category.name, fee.getCategory());
        assertEquals(subCat, fee.getSubCategory());
        assertEquals(type, fee.getType());
        assertEquals(finalPrice, fee.getSubChargedPrice(), 0.01);
    }

    @Test
    public void toString_WhenCalled_ReturnsStringFormattedWithExpectedData() {
        String id = getRandomFeeId(), name = "Test Fee 2";
        String subCat = getRandomFeeSubCategory(), type = getRandomFeeType();

        Department department = Department.DEVELOPMENT;
        Category category = department.getRandomCategory();

        int quantity = randomNumber(1, 10);
        double price = randomDouble(1, 100);
        double departmentSubCharge = 1.2;
        double finalPrice = price * quantity * departmentSubCharge;

        Fee fee = new Fee(id, name, "", department.name, category.name, subCat, type, quantity, price);

        String result = fee.toString();

        assertNotNull(result);
        assertTrue(result.contains(id));
        assertTrue(result.contains(name));
        assertTrue(result.contains(currencyFormat(price)));
        assertTrue(result.contains(String.valueOf(quantity)));
        assertTrue(result.contains(percentDifference(departmentSubCharge)));
        assertTrue(result.contains(currencyFormat(finalPrice)));
    }
}
package infrastructure;

import domain.fee.Fee;
import infrastructure.reader.CSVFileReader;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeeRepositoryTest {
    private FeeRepository repository;
    private CSVFileReader reader;

    @Before
    public void setUp() {
        reader = mock(CSVFileReader.class);
        repository = new FeeRepository(reader);
    }

    @Test
    public void fetchFeeList_WhenFileReadHasProperData_ReturnsExpectedListOfFees() {
        List<String[]> expected = Arrays.asList(
                new String[]{"Fee Id1", "First Fee" , "This is a raw fee.", "Test Development", "Coding"           , "Cat2", "TypeC", "3", "97.03"},
                new String[]{"Fee Id2", "Second Fee", "This is a raw fee.", "Test Development", "Quality Assurance", "Cat3", "TypeA", "5", "99.94"}
        );

        when(reader.read(anyString())).thenReturn(expected);

        List<Fee> result = repository.fetchFeeList();

        assertNotNull(result);
        assertEquals(expected.size(), result.size());
        double feeSubCharge;
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i)[0], result.get(i).getId());
            assertEquals(expected.get(i)[1], result.get(i).getName());
            assertEquals(expected.get(i)[2], result.get(i).getDescription());
            assertEquals(expected.get(i)[3], result.get(i).getDepartment());
            assertEquals(expected.get(i)[4], result.get(i).getCategory());
            assertEquals(expected.get(i)[5], result.get(i).getSubCategory());
            assertEquals(expected.get(i)[6], result.get(i).getType());
            feeSubCharge = Integer.parseInt(expected.get(i)[7]) * Double.parseDouble(expected.get(i)[8]);
            assertEquals(feeSubCharge, result.get(i).getSubChargedPrice(), 0.10);
        }
    }

    @Test
    public void fetchFeeList_WhenFileReadHasDataWithEmptySpaces_EmptySpaceIsTrimmedAndReturnsExpectedListOfFee() {
        String expectedId = "Fee Id1", expectedDescription = "This is a raw fee.", expectedDepartment = "Development";

        List<String[]> expected = Collections.singletonList(
                new String[]{
                        String.format("            %s", expectedId),
                        "First Fee",
                        String.format("      %s     ", expectedDescription),
                        String.format("%s     ", expectedDepartment),
                        "Coding", "Cat2", "TypeC", "3", "97.03"
                }
        );

        when(reader.read(anyString())).thenReturn(expected);

        List<Fee> result = repository.fetchFeeList();

        assertNotNull(result);
        assertEquals(expectedId, result.get(0).getId());
        assertEquals(expectedDescription, result.get(0).getDescription());
        assertEquals(expectedDepartment, result.get(0).getDepartment());
    }

    @Test(expected = NumberFormatException.class)
    public void fetchFeeList_WhenFileReadHasLineWithStringOnQuantityColumn_ThrowsNumberFormatExceptionError() {
        List<String[]> expected = Collections.singletonList(
                new String[]{"Fee Id1", "First Fee", "This is a raw fee.", "Development", "Coding", "Cat2", "TypeC", "Quantity", "97.03"}
        );

        when(reader.read(anyString())).thenReturn(expected);

        repository.fetchFeeList();
    }

    @Test(expected = NumberFormatException.class)
    public void fetchFeeList_WhenFileReadHasLineWithStringOnPriceColumn_ThrowsNumberFormatExceptionError() {
        List<String[]> expected = Collections.singletonList(
                new String[]{"Fee Id1", "First Fee", "This is a raw fee.", "Development", "Coding", "Cat2", "TypeC", "4", "Price"}
        );

        when(reader.read(anyString())).thenReturn(expected);

        repository.fetchFeeList();
    }
}

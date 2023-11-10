package domain.helper;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static domain.helper.RandomHelper.randomDouble;
import static domain.helper.RandomHelper.randomElement;
import static domain.helper.RandomHelper.randomNumber;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RandomHelperTest {

    @Test
    public void randomElement_WhenGivenFilledCollectionOfStrings_ReturnsRandomStringFromTheGivenCollection() {
        List<String> collection = Arrays.asList("A", "B", "C", "D", "E");

        String element = randomElement(collection);

        assertNotNull(element);
        assertTrue(collection.contains(element));
    }

    @Test
    public void randomElement_WhenGivenFilledSetOfStrings_ReturnsRandomStringFromTheGivenSet() {
        String[] set = new String[]{"A", "B", "C", "D", "E"};

        String element = randomElement(set);

        assertNotNull(element);
        assertTrue(Arrays.asList(set).contains(element));
    }

    @Test(expected = IllegalArgumentException.class)
    public void randomElement_WhenGivenEmptyCollectionOfStrings_ThrowsIllegalArgumentExceptionError() {
        randomElement(Collections.emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void randomElement_WhenGivenEmptySetOfStrings_ThrowsIllegalArgumentExceptionError() {
        randomElement(new String[]{});
    }

    @Test
    public void randomNumber_WhenGivenNumberRange_ReturnsNumberInsideTheGivenRange() {
        int min = 10, max = 100;
        int number = randomNumber(min, max);

        assertTrue(number >= min);
        assertTrue(number <= max);
    }

    @Test
    public void randomDouble_WhenGivenDoubleRange_ReturnsDoubleInsideTheGivenRange() {
        double min = 10.0, max = 100.0;
        double number = randomDouble(min, max);

        assertTrue(number >= min);
        assertTrue(number <= max);
    }
}

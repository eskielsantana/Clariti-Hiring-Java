package domain.helper;

import org.junit.Test;

import static domain.helper.MathHelper.percentDifference;
import static org.junit.Assert.assertEquals;

public class MathHelperTest {

    @Test
    public void percentage_WhenGivenDoubleValueBiggerThanOne_ReturnStringFormattedPercentage() {
        double given = 1.65;
        String expected = "+65%";

        String formatted = percentDifference(given);

        assertEquals(expected, formatted);
    }

    @Test
    public void percentage_WhenGivenDoubleValueSmallerThanOne_ReturnStringFormattedPercentage() {
        double given = 0.85;
        String expected = "-15%";

        String formatted = percentDifference(given);

        assertEquals(expected, formatted);
    }
}

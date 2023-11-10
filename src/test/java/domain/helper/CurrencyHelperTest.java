package domain.helper;

import org.junit.Test;

import static domain.helper.CurrencyHelper.currencyFormat;
import static org.junit.Assert.assertEquals;

public class CurrencyHelperTest {

    @Test
    public void currencyFormat_WhenGivenPositiveValue_ReturnStringFormattedCurrency() {
        double given = 1665.2333333;
        String expected = "$1,665.23";

        String formatted = currencyFormat(given);

        assertEquals(expected, formatted);
    }

    @Test
    public void currencyFormat_WhenGivenNegativeValue_ReturnStringFormattedCurrency() {
        double given = -2345.551234;
        String expected = "-$2,345.55";

        String formatted = currencyFormat(given);

        assertEquals(expected, formatted);
    }
}

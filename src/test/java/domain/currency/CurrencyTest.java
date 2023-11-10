package domain.currency;

import org.junit.Test;

import static domain.currency.Currency.currencyFormat;
import static domain.currency.Currency.percentDifference;
import static org.junit.Assert.assertEquals;

public class CurrencyTest {

    @Test
    public void currencyFormat_WhenGivenDoubleValue_ReturnStringFormattedCurrency() {
        double given = 1665.2333333;
        String expected = "$1,665.23";

        String formatted = currencyFormat(given);

        assertEquals(expected, formatted);
    }

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

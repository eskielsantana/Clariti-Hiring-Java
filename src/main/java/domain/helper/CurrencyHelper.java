package domain.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyHelper {
    private static final NumberFormat CANADIAN = NumberFormat.getCurrencyInstance(Locale.CANADA);

    private CurrencyHelper() { }
    public static String currencyFormat(double amount) {
        return CANADIAN.format(amount);
    }
}

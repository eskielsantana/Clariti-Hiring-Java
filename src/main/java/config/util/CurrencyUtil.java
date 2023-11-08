package config.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtil {
    private final static NumberFormat CANADIAN = NumberFormat.getCurrencyInstance(Locale.CANADA);

    public static String CurrencyFormatter(double amount) {
        return CANADIAN.format(amount);
    }
}

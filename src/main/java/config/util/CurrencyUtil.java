package config.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtil {
    private static final NumberFormat CANADIAN = NumberFormat.getCurrencyInstance(Locale.CANADA);

    private CurrencyUtil() { }
    public static String CurrencyFormatter(double amount) {
        return CANADIAN.format(amount);
    }
}

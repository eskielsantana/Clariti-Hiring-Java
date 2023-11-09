package config.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtil {
    private static final NumberFormat CANADIAN = NumberFormat.getCurrencyInstance(Locale.CANADA);

    private CurrencyUtil() { }
    public static String currencyFormat(double amount) {
        return CANADIAN.format(amount);
    }

    public static String percentage(double number) {
        int percentage = (int) Math.round((number - 1) * 100);
        return (percentage > 0 ? "+" : "") + percentage + "%";
    }
}

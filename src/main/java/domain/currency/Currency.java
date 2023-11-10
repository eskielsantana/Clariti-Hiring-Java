package domain.currency;

import java.text.NumberFormat;
import java.util.Locale;

public class Currency {
    private static final NumberFormat CANADIAN = NumberFormat.getCurrencyInstance(Locale.CANADA);

    private Currency() { }
    public static String currencyFormat(double amount) {
        return CANADIAN.format(amount);
    }

    public static String percentDifference(double number) {
        int percentage = (int) Math.round((number - 1) * 100);
        return (percentage > 0 ? "+" : "") + percentage + "%";
    }
}

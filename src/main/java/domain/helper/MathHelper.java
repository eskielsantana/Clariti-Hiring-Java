package domain.helper;

public class MathHelper {

    private MathHelper() {}

    public static String percentDifference(double number) {
        int percentage = (int) Math.round((number - 1) * 100);
        return (percentage > 0 ? "+" : "") + percentage + "%";
    }
}

package config.util;

import java.util.Collection;
import java.util.Random;

public class RandomUtil {
    public static final Random RANDOM = new Random();

    private RandomUtil() { }
    public static <E> E randomElement(Collection<? extends E> collection) {
        if(collection.isEmpty()) return null;
        return collection.stream().toList().get(RANDOM.nextInt(collection.size()));
    }

    public static <E> E randomElement(E[] collection) {
        if(collection.length == 0) return null;
        return collection[RANDOM.nextInt(collection.length)];
    }

    public static int randomNumber(int min, int max) {
        return (min + RANDOM.nextInt(max - min));
    }

    public static double randomDouble(double min, double max) {
        return RANDOM.nextDouble() * (max - min + 1) + min;
    }
}

package domain.helper;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;

public class RandomHelper {
    public static final Random RANDOM = new Random();

    private RandomHelper() {}

    public static <E> E randomElement(Collection<? extends E> collection) {
        Objects.requireNonNull(collection, "Collection must not be null");
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Collection must not be empty");
        }
        return collection.stream().toList().get(RANDOM.nextInt(collection.size()));
    }

    public static <E> E randomElement(E[] collection) {
        Objects.requireNonNull(collection, "Collection must not be null");
        if (collection.length == 0) {
            throw new IllegalArgumentException("Collection must not be empty");
        }
        return collection[RANDOM.nextInt(collection.length)];
    }

    public static int randomNumber(int min, int max) {
        return (min + RANDOM.nextInt(max - min));
    }

    public static double randomDouble(double min, double max) {
        return RANDOM.nextDouble() * (max - min) + min;
    }
}

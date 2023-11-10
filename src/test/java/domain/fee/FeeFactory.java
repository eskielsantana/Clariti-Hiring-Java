package domain.fee;

import domain.enumerators.Department;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static domain.helper.RandomHelper.RANDOM;
import static domain.helper.RandomHelper.randomDouble;
import static domain.helper.RandomHelper.randomElement;
import static domain.helper.RandomHelper.randomNumber;

public class FeeFactory {
    private final static List<String> SUB_CATEGORIES = Arrays.asList("Cat1", "Cat2", "Cat3");
    private final static List<String> TYPES = Arrays.asList("Type A", "Type B", "Type C");

    public static List<Fee> mockFeeList(int count) {
        return IntStream.range(0, count).mapToObj(FeeFactory::mockFee).toList();
    }

    public static Fee mockFee(int i) {
        Department department = Department.getRandomDepartment();
        return mockFee(i, department);
    }

    public static Fee mockFee(int i, Department department) {
        return new Fee(
                getRandomFeeId(),
                String.format("Random Fee %d", i),
                "This is a randomly generated fee. Isn't it useful?",
                department.name,
                department.getRandomCategory().name,
                getRandomFeeSubCategory(),
                getRandomFeeType(),
                randomNumber(1, 10),
                randomDouble(1, 100)
        );
    }

    public static String getRandomFeeId() {
        return String.format("RND%06d", RANDOM.nextInt(1000000));
    }

    public static String getRandomFeeSubCategory() {
        return randomElement(SUB_CATEGORIES);
    }

    public static String getRandomFeeType() {
        return randomElement(TYPES);
    }
}

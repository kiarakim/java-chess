package techcourse.fp.mission;

import java.util.List;
import java.util.function.Function;

public class Calculator {

    public static int sumTotal(List<Integer> numbers, Condition condition) {
        return condition.apply(numbers);
    }

    public static int sumAll(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }

    public static int sumAllEven(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            if (number % 2 == 0) {
                total += number;
            }
        }
        return total;
    }

    public static int sumAllOverThree(List<Integer> numbers) {
        int total = 0;

        for (int number : numbers) {
            if (number > 3) {
                total += number;
            }
        }

        return total;
    }
}

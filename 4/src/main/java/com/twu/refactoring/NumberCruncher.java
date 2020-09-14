package com.twu.refactoring;

import java.util.function.Predicate;

public class NumberCruncher {
    private final int[] numbers;

    public NumberCruncher(int... numbers) {
        this.numbers = numbers;
    }

    public int countEven() {
      return count(o -> o % 2 ==0);
    }

    public int countOdd() {
        return count(o -> o % 2 == 1);
    }

    public int countPositive() {
        return count(o -> o > 0);
    }

    public int countNegative() {
        return count(o -> o < 0);
    }

    private int count(Predicate<Integer> predicate) {
        int cnt = 0;
        for (int number : numbers) {
            if (predicate.test(number)) {
                ++cnt;
            }
        }
        return cnt;
    }
}

package com.twu.refactoring;

import java.util.HashMap;
import java.util.Map;

public class Direction {
    private static final Map<Character, Character> right = new HashMap<>();
    private static final Map<Character, Character> left = new HashMap<>();
    private final char direction;

    public Direction(char direction) {
        this.direction = direction;
        right.put('N', 'E');
        right.put('S', 'W');
        right.put('E', 'N');
        right.put('W', 'S');

        left.put('N', 'W');
        left.put('S', 'E');
        left.put('E', 'N');
        left.put('W', 'S');
    }

    public Direction turnRight() {
        if (!right.containsKey(direction)) {
            throw new IllegalArgumentException();
        }
        return new Direction(right.get(direction));
    }

    public Direction turnLeft() {
       if (!left.containsKey(direction)) {
           throw new IllegalArgumentException();
       }
       return new Direction(left.get(direction));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direction direction1 = (Direction) o;

        if (direction != direction1.direction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) direction;
    }

    @Override
    public String toString() {
        return "Direction{direction=" + direction + '}';
    }
}

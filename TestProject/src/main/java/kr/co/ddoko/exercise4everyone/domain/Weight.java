package kr.co.ddoko.exercise4everyone.domain;

import lombok.Getter;

@Getter
public enum Weight {
    ONE(-69),
    TWO(-74),
    THREE(-79),
    FOUR(-85),
    FIVE(+85);

    private final int value;

    Weight(int value) {
        this.value = value;
    }

    // Get Weight enum by index
    public static Weight getByIndex(int index) {
        return switch (index) {
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            default -> throw new IllegalArgumentException("Invalid index for Weight enum: " + index);
        };
    }
}

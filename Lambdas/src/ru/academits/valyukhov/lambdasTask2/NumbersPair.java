package ru.academits.valyukhov.lambdasTask2;

class NumbersPair {
    private final int value1;
    private final int value2;

    public NumbersPair(int value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public NumbersPair(NumbersPair previousPair) {
        value1 = previousPair.getValue2();
        value2 = previousPair.getValue1() + value1;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}
package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    private final T value;
    private final String description;

    public SleepAnalysisResult(T value, String description) {
        this.value = value;
        this.description = description;
    }

    public void printResult() {
        System.out.println(description + ": " + value);
    }

    public T getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

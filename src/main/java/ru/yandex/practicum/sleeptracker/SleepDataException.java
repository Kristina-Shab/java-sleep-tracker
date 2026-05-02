package ru.yandex.practicum.sleeptracker;

public class SleepDataException extends RuntimeException {

    public SleepDataException(String message) {
        super(message);
    }

    public SleepDataException(String message, Exception e) {
        super(message, e);
    }
}

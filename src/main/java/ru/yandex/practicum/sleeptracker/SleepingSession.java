package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality quality;
    private final Duration durationSleep;

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime sleepEnd, SleepQuality quality) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.quality = quality;
        durationSleep = Duration.between(sleepStart, sleepEnd);
    }

    public Duration getDurationSleep() {
        return durationSleep;
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getSleepEnd() {
        return sleepEnd;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "sleepStart=" + sleepStart +
                ", sleepEnd=" + sleepEnd +
                ", quality=" + quality +
                '}' + System.lineSeparator();
    }
}

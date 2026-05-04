package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class MaxSessionDurationAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final String DESCRIPTION = "Максимальная продолжительность сессии (в минутах)";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        Long max = sleepingSessions.stream()
                .map(session -> session.getDurationSleep().toMinutes())
                .max(Long::compareTo)
                .orElse(0L);
        return new SleepAnalysisResult<>(max, DESCRIPTION);
    }
}
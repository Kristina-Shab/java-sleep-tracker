package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class AverageSessionDurationAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final String DESCRIPTION = "Средняя продолжительность сессии (в минутах)";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        Long average = getSumSessionDuration(sleepingSessions) / sleepingSessions.size();
        return new SleepAnalysisResult<>(average, DESCRIPTION);
    }

    private Long getSumSessionDuration(List<SleepingSession> sleepingSessions) {
        return sleepingSessions.stream()
                .map(session -> session.getDurationSleep().toMinutes())
                .reduce((a, b) -> a + b)
                .orElse(0L);
    }
}

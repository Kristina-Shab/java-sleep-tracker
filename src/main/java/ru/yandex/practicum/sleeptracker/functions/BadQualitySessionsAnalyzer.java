package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final String DESCRIPTION = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long badSleep = sleepingSessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult<>(badSleep, DESCRIPTION);
    }
}

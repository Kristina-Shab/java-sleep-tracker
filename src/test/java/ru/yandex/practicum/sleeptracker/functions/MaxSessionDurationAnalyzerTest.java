package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSessionDurationAnalyzerTest {
    private static MaxSessionDurationAnalyzer analyzer;
    private static SleepingSession testSession1;
    private static SleepingSession testSession3;
    private static SleepingSession testSession4;

    @BeforeAll
    static void beforeAll() {
        analyzer = new MaxSessionDurationAnalyzer();
        testSession1 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 8, 30),
                SleepQuality.GOOD
        );
        testSession3 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 8, 29),
                SleepQuality.GOOD
        );
        testSession4 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 2, 1),
                SleepQuality.GOOD
        );
    }

    @Test
    void applyValidDescription() {
        List<SleepingSession> sessions = List.of(testSession1);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals("Максимальная продолжительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void applyIdenticalSessions() {
        List<SleepingSession> sessions = List.of(testSession1, testSession3);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(390L, result.getValue());
    }

    @Test
    void applyMinDurationInSessions() {
        List<SleepingSession> sessions = List.of(testSession4, testSession3);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(389L, result.getValue());
    }
}

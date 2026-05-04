package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinSessionDurationAnalyzerTest {
    private static MinSessionDurationAnalyzer analyzer;
    private static SleepingSession testSession1;
    private static SleepingSession testSession2;
    private static SleepingSession testSession3;

    @BeforeAll
    static void beforeAll() {
        analyzer = new MinSessionDurationAnalyzer();
        testSession1 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 8, 30),
                SleepQuality.GOOD
        );
        testSession2 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 8, 29),
                SleepQuality.GOOD
        );
        testSession3 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 2, 1),
                SleepQuality.GOOD
        );
    }

    @Test
    void applySingleSessionWithValidDescription() {
        List<SleepingSession> sessions = List.of(testSession1);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(390L, result.getValue());
        assertEquals("Минимальная продолжительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void applyMinDurationInSessions() {
        List<SleepingSession> sessions = List.of(testSession1, testSession2);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(389L, result.getValue());
    }

    @Test
    void applySessionWithMinDuration() {
        List<SleepingSession> sessions = List.of(testSession3, testSession2);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(1L, result.getValue());
    }
}

package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsAnalyzerTest {
    private static SleeplessNightsAnalyzer analyzer;
    private static SleepingSession testSession1;
    private static SleepingSession testSession2;
    private static SleepingSession testSession3;
    private static SleepingSession testSession4;
    private static SleepingSession testSession5;
    private static SleepingSession testSession6;

    @BeforeAll
    static void beforeAll() {
        analyzer = new SleeplessNightsAnalyzer();
        testSession1 = new SleepingSession(
                LocalDateTime.of(2026, 3, 29, 13, 0),
                LocalDateTime.of(2026, 3, 29, 20, 30),
                SleepQuality.BAD
        );
        testSession2 = new SleepingSession(
                LocalDateTime.of(2026, 4, 2, 6, 01),
                LocalDateTime.of(2026, 4, 2, 9, 30),
                SleepQuality.BAD
        );
        testSession3 = new SleepingSession(
                LocalDateTime.of(2026, 5, 1, 23, 00),
                LocalDateTime.of(2026, 5, 2, 7, 05),
                SleepQuality.GOOD
        );
        testSession4 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 13, 00),
                LocalDateTime.of(2026, 5, 2, 15, 05),
                SleepQuality.GOOD
        );
        testSession5 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 23, 00),
                LocalDateTime.of(2026, 5, 3, 7, 05),
                SleepQuality.GOOD
        );
        testSession6 = new SleepingSession(
                LocalDateTime.of(2026, 5, 3, 18, 00),
                LocalDateTime.of(2026, 5, 3, 23, 59),
                SleepQuality.GOOD
        );
    }

    @Test
    void applySingleSession() {
        List<SleepingSession> sessions = List.of(testSession3);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(0L, result.getValue());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void applyForMissingNightsInLogs() {
        List<SleepingSession> sessions = List.of(testSession1, testSession2);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(4L, result.getValue());
    }

    @Test
    void applyTwoSessions() {
        List<SleepingSession> sessions = List.of(testSession3, testSession5);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    @Test
    void applySeveralSessionsInOneDay() {
        List<SleepingSession> sessions = List.of(testSession3, testSession4, testSession5, testSession6);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(1L, result.getValue());
    }
}

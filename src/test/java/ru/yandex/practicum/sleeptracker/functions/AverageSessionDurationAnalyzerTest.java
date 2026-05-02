package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageSessionDurationAnalyzerTest {
    private static AverageSessionDurationAnalyzer analyzer;
    private static SleepingSession testSession1;
    private static SleepingSession testSession2;

    @BeforeAll
    static void beforeAll() {
        analyzer = new AverageSessionDurationAnalyzer();
        testSession1 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 8, 30),
                SleepQuality.GOOD
        );
        testSession2 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 9, 30),
                SleepQuality.GOOD
        );
    }

    @Test
    void applyValidDescription() {
        List<SleepingSession> sessions = List.of(testSession1);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals("Средняя продолжительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void applySingleSession() {
        List<SleepingSession> sessions = List.of(testSession1);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(390L, result.getValue());
    }

    @Test
    void applyMultiSessions() {
        List<SleepingSession> sessions = List.of(testSession1, testSession2);
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(420L, result.getValue());
    }
}

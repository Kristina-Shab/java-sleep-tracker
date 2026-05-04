package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountSessionDurationAnalyzerTest {
    private static CountSessionDurationAnalyzer analyzer;
    private static SleepingSession testSession;

    @BeforeAll
    static void beforeAll() {
        analyzer = new CountSessionDurationAnalyzer();
        testSession = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 2, 0),
                LocalDateTime.of(2026, 5, 2, 8, 30),
                SleepQuality.GOOD
        );
    }

    @Test
    void applySingleSessionWithValidDescription() {
        List<SleepingSession> sessions = List.of(testSession);
        SleepAnalysisResult<Integer> result = analyzer.apply(sessions);
        assertEquals(1, result.getValue());
        assertEquals("Количество сессий сна за представленный период", result.getDescription());
    }

    @Test
    void applyMultiSession() {
        List<SleepingSession> sessions = List.of(testSession, testSession, testSession);
        SleepAnalysisResult<Integer> result = analyzer.apply(sessions);
        assertEquals(3, result.getValue());
    }
}

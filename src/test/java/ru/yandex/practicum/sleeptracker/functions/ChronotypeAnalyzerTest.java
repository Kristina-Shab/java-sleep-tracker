package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeAnalyzerTest {
    private static ChronotypeAnalyzer analyzer;
    private static SleepingSession testSession1;
    private static SleepingSession testSession2;
    private static SleepingSession testSession3;
    private static SleepingSession testSession4;
    private static SleepingSession testSession5;

    @BeforeAll
    static void beforeAll() {
        analyzer = new ChronotypeAnalyzer();
        testSession1 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 13, 0),
                LocalDateTime.of(2026, 5, 2, 20, 30),
                SleepQuality.BAD
        );
        testSession2 = new SleepingSession(
                LocalDateTime.of(2026, 5, 2, 6, 1),
                LocalDateTime.of(2026, 5, 2, 9, 30),
                SleepQuality.BAD
        );
        testSession3 = new SleepingSession(
                LocalDateTime.of(2026, 5, 1, 23, 10),
                LocalDateTime.of(2026, 5, 2, 9, 5),
                SleepQuality.GOOD
        );
        testSession4 = new SleepingSession(
                LocalDateTime.of(2026, 5, 1, 21, 55),
                LocalDateTime.of(2026, 5, 2, 6, 55),
                SleepQuality.GOOD
        );
        testSession5 = new SleepingSession(
                LocalDateTime.of(2026, 5, 1, 20, 10),
                LocalDateTime.of(2026, 5, 2, 0, 10),
                SleepQuality.GOOD
        );
    }

    @Test
    void applyValidDescription() {
        List<SleepingSession> sessions = List.of(testSession1);
        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals("Хронотип", result.getDescription());
    }

    @Test
    void applyIgnoresDaytimeSessionsAndSleeplessNights() {
        List<SleepingSession> sessions = List.of(testSession1, testSession2, testSession3);
        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals("Сова", result.getValue());
    }

    @Test
    void applySingleSessionReturnLark() {
        List<SleepingSession> sessions = List.of(testSession3, testSession4, testSession5);
        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals("Жаворонок", result.getValue());
    }
}

package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionFileLoaderTest {

    @Test
    void getSessionsWithValidFilePath() {
        List<SleepingSession> result = SessionFileLoader.getSessions("src/test/resources/Valid10.txt");
        assertEquals(10, result.size());
    }

    @Test
    void getSessionsWithEmptyFile() {
        boolean exceptionThrown = false;
        try {
            SessionFileLoader.getSessions("src/test/resources/Empty.txt");
        } catch (SleepDataException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    void getSessionsWithInvalidPath() {
        boolean exceptionThrown = false;
        try {
            SessionFileLoader.getSessions("src/test/resources");
        } catch (SleepDataException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    void getSessionsWithMissingData() {
        List<SleepingSession> result = SessionFileLoader.getSessions("src/test/resources/MissingData.txt");
        assertEquals(8, result.size());
    }
}

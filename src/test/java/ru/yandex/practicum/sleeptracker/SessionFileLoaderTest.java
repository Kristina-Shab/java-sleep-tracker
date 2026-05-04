package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionFileLoaderTest {

    @Test
    void getSessionsWithValidFilePath() {
        List<SleepingSession> result = SessionFileLoader.getSessions("src/test/resources/Valid10.txt");
        assertEquals(10, result.size());
    }

    @Test
    void getSessionsWithEmptyFile() {
        assertThrows(SleepDataException.class, () -> SessionFileLoader.getSessions("src/test/resources/Empty.txt"));
    }

    @Test
    void getSessionsWithInvalidPath() {
        assertThrows(SleepDataException.class, () -> SessionFileLoader.getSessions("src/test/resources"));
    }

    @Test
    void getSessionsWithMissingData() {
        List<SleepingSession> result = SessionFileLoader.getSessions("src/test/resources/MissingData.txt");
        assertEquals(8, result.size());
    }
}

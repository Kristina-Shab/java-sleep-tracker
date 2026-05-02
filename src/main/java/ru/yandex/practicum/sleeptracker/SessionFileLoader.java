package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class SessionFileLoader {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private SessionFileLoader() {
    }

    public static List<SleepingSession> getSessions(String file) {
        Path filePath = getFailPath(file);
        List<String> sessionsLines = getSessionsLine(filePath);
        return sessionsLines.stream()
                .map(SessionFileLoader::lineToSession)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static Path getFailPath(String file) {
        Path filePath = Paths.get(file);
        if (!Files.isRegularFile(filePath)) {
            throw new SleepDataException("В указанном пути нет файла, путь = " + filePath);
        }
        return filePath;
    }

    private static Optional<SleepingSession> lineToSession(String session) {
        String[] parts = session.split(";");
        if (parts.length != 3) {
            return Optional.empty();
        }
        SleepingSession sleepingSession = new SleepingSession(
                LocalDateTime.parse(parts[0], DATE_TIME_FORMATTER),
                LocalDateTime.parse(parts[1], DATE_TIME_FORMATTER),
                SleepQuality.valueOf(parts[2].toUpperCase())
        );
        return Optional.of(sleepingSession);
    }

    private static List<String> getSessionsLine(Path pathFile) {
        checkNotEmpty(pathFile);
        try (Stream<String> lines = Files.lines(pathFile, StandardCharsets.UTF_8)) {
            return lines.map(String::trim)
                    .filter(str -> !str.isEmpty())
                    .toList();
        } catch (IOException e) {
            throw new SleepDataException("Ошибка чтения файла c логом: " + e.getMessage(), e);
        }
    }

    private static void checkNotEmpty(Path pathFile) {
        try {
            if (Files.size(pathFile) == 0) {
                throw new SleepDataException("Файл пуст: " + pathFile);
            }
        } catch (IOException e) {
            throw new SleepDataException("Ошибка проверки файла: " + e.getMessage(), e);
        }
    }
}

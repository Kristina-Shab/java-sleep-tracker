package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class ChronotypeAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult<String>> {
    private static final String DESCRIPTION = "Хронотип";
    private static final LocalTime OWL_BEDTIME_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKETIME_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_BEDTIME_END = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKETIME_START = LocalTime.of(7, 0);
    private static final LocalTime CORE_SLEEP_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sleepingSessions) {
        List<SleepingSession> validSession = sleepingSessions.stream()
                .filter(this::isNightSleep)
                .toList();
        long ownSessions = getOwnSession(validSession);
        long larkSessions = getLarkSession(validSession);
        long pigeonSessions = getPigeonSession(validSession);
        Chronotype type = getDominantChronotype(ownSessions, larkSessions, pigeonSessions);
        return new SleepAnalysisResult<>(type.getDisplayName(), DESCRIPTION);
    }

    private Chronotype getTypeBySession(SleepingSession session) {
        LocalTime start = session.getSleepStart().toLocalTime();
        LocalTime end = session.getSleepEnd().toLocalTime();
        if (start.isAfter(OWL_BEDTIME_START) && end.isAfter(OWL_WAKETIME_END)) {
            return Chronotype.OWL;
        }
        if (start.isBefore(LARK_BEDTIME_END) && end.isBefore(LARK_WAKETIME_START)) {
            return Chronotype.LARK;
        }
        return Chronotype.PIGEON;
    }

    private Chronotype getDominantChronotype(long ownSessions, long larkSessions, long pigeonSessions) {
        if (ownSessions > larkSessions && ownSessions > pigeonSessions) {
            return Chronotype.OWL;
        }
        if (larkSessions > ownSessions && larkSessions > pigeonSessions) {
            return Chronotype.LARK;
        }
        return Chronotype.PIGEON;
    }

    private long getOwnSession(List<SleepingSession> sleepingSessions) {
        return sleepingSessions.stream()
                .filter(session -> getTypeBySession(session) == Chronotype.OWL)
                .count();
    }

    private long getLarkSession(List<SleepingSession> sleepingSessions) {
        return sleepingSessions.stream()
                .filter(session -> getTypeBySession(session) == Chronotype.LARK)
                .count();
    }

    private long getPigeonSession(List<SleepingSession> sleepingSessions) {
        return sleepingSessions.stream()
                .filter(session -> getTypeBySession(session) == Chronotype.PIGEON)
                .count();
    }

    private boolean isNightSleep(SleepingSession sleepingSession) {
        if (sleepingSession.getSleepStart().toLocalTime().isBefore(CORE_SLEEP_END)) {
            return true;
        }
        if (!sleepingSession.getSleepStart().toLocalDate().equals(sleepingSession.getSleepEnd().toLocalDate())) {
            return true;
        }
        return false;
    }
}


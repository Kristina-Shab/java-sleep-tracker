package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightsAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final String DESCRIPTION = "Количество бессонных ночей";
    private static final LocalTime CORE_SLEEP_END = LocalTime.of(6, 0);
    private static final int DAY_BOUNDARY_HOUR = 12;

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long sleeplessNightsInLoggedPeriod = getSleeplessNightsInLoggedPeriod(sleepingSessions);
        int loggedNights = getLoggedNightsList(sleepingSessions).size();
        long missingNights = getNightsInPeriod(sleepingSessions) - loggedNights;
        long sleeplessNights = sleeplessNightsInLoggedPeriod + missingNights;
        return new SleepAnalysisResult<>(sleeplessNights, DESCRIPTION);
    }

    private Long getSleeplessNightsInLoggedPeriod(List<SleepingSession> sleepingSessions) {
        Map<LocalDate, List<SleepingSession>> sessionsByNight = sleepingSessions.stream()
                .collect(Collectors.groupingBy(session -> getNightDate(session.getSleepStart())));

        return sessionsByNight.values().stream()
                .filter(nightSessions -> nightSessions.stream().allMatch(this::isSleeplessNight))
                .count();
    }

    private boolean isSleeplessNight(SleepingSession session) {
        if (session.getSleepStart().toLocalTime().isBefore(CORE_SLEEP_END)) {
            return false;
        }
        if (!session.getSleepStart().toLocalDate().equals(session.getSleepEnd().toLocalDate())) {
            return false;
        }
        return true;
    }

    private Long getNightsInPeriod(List<SleepingSession> sleepingSessions) {
        LocalDate firstNight = getNightDate(sleepingSessions.getFirst().getSleepStart());
        LocalDate lastNight = getNightDate(sleepingSessions.getLast().getSleepStart());
        return lastNight.toEpochDay() - firstNight.toEpochDay() + 1;
    }

    public List<LocalDate> getLoggedNightsList(List<SleepingSession> sleepingSessions) {
        return sleepingSessions.stream()
                .map(sleepingSession -> getNightDate(sleepingSession.getSleepStart()))
                .distinct()
                .toList();
    }

    private LocalDate getNightDate(LocalDateTime sleepStart) {
        int hour = sleepStart.getHour();
        if (hour >= DAY_BOUNDARY_HOUR) {
            return sleepStart.toLocalDate().plusDays(1);
        } else {
            return sleepStart.toLocalDate();
        }
    }
}

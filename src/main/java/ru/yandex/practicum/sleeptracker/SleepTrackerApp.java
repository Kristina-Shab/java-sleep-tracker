package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Для работы программы нужно указать путь к файлу с логом сна");
            return;
        }

        String filePath = args[0];
        List<SleepingSession> sessions = SessionFileLoader.getSessions(filePath);
        List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> functionsList = createFunctionsList();

        functionsList.stream()
                .map(function -> function.apply(sessions))
                .forEach(SleepAnalysisResult::printResult);
    }

    private static List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> createFunctionsList() {
        return List.of(
                new CountSessionDurationAnalyzer(),
                new MaxSessionDurationAnalyzer(),
                new MinSessionDurationAnalyzer(),
                new AverageSessionDurationAnalyzer(),
                new BadQualitySessionsAnalyzer(),
                new SleeplessNightsAnalyzer(),
                new ChronotypeAnalyzer()
        );
    }
}
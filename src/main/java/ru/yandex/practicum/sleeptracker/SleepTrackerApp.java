package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SleepTrackerApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Для работы программы нужно указать путь к файлу с логом сна");
            return;
        }

        String filePath = args[0];
        List<SleepingSession> sessions = SessionFileLoader.getSessions(filePath);
        System.out.println(sessions);
    }
}
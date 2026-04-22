package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsForDay = timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>());
        ArrayList<TrainingSession> sessions = trainingsForDay.computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>());
        sessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.computeIfAbsent(dayOfWeek, k -> new TreeMap<>());
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsForDay = timetable.computeIfAbsent(dayOfWeek, k -> new TreeMap<>());
        return trainingsForDay.computeIfAbsent(timeOfDay, k -> new ArrayList<>());
    }

    public void printTimetable() {
        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            System.out.println(dayOfWeek);
            for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> entry : timetable.get(dayOfWeek).entrySet()) {
                System.out.println(entry.getKey());
                for (TrainingSession trainingSession : entry.getValue()) {
                    System.out.println(trainingSession);
                }
            }
        }
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> countByCoaches = new HashMap<>();
        ArrayList<CounterOfTrainings> listCounterOfTrainings = new ArrayList<>();
        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> entry : timetable.get(dayOfWeek).entrySet()) {
                for (TrainingSession trainingSession : entry.getValue()) {
                    if (countByCoaches.containsKey(trainingSession.getCoach())) {
                        countByCoaches.put(trainingSession.getCoach(), countByCoaches.get(trainingSession.getCoach()) + 1);
                    } else {
                        countByCoaches.put(trainingSession.getCoach(), 1);
                    }
                }
            }
        }
        for (Map.Entry<Coach, Integer> entry : countByCoaches.entrySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(entry.getKey(), entry.getValue());
            listCounterOfTrainings.add(counterOfTrainings);
        }

        listCounterOfTrainings.sort(Comparator.reverseOrder());
        return listCounterOfTrainings;

    }
}

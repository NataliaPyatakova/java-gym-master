package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        ArrayList<TrainingSession> trainingSessionArrayList;
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionTreeMap;
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            trainingSessionTreeMap = timetable.get(trainingSession.getDayOfWeek());
            if (trainingSessionTreeMap.containsKey(trainingSession.getTimeOfDay())) {
                trainingSessionArrayList = trainingSessionTreeMap.get(trainingSession.getTimeOfDay());
                trainingSessionArrayList.add(trainingSession);
            } else {
                trainingSessionArrayList = new ArrayList<>();
                trainingSessionArrayList.add(trainingSession);
                trainingSessionTreeMap.put(trainingSession.getTimeOfDay(), trainingSessionArrayList);
            }
        } else {
            trainingSessionArrayList = new ArrayList<>();
            trainingSessionArrayList.add(trainingSession);
            trainingSessionTreeMap = new TreeMap<>();
            trainingSessionTreeMap.put(trainingSession.getTimeOfDay(), trainingSessionArrayList);
            timetable.put(trainingSession.getDayOfWeek(), trainingSessionTreeMap);
        }
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        ArrayList<TrainingSession> trainingSessionArrayList;
        if (timetable.containsKey(dayOfWeek)) {
            trainingSessionArrayList = timetable.get(dayOfWeek).get(timeOfDay);
        } else {
            trainingSessionArrayList = null;  //если HashMap вообще пустая. то тут рвет
        }
        return trainingSessionArrayList;
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

        Collections.sort(listCounterOfTrainings);
        Collections.reverse(listCounterOfTrainings);
        return listCounterOfTrainings;

    }
}

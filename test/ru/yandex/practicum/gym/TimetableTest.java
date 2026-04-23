package ru.yandex.practicum.gym;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableTest {

    private static Timetable timetable;
    private static Coach coach;
    private static Coach coachSecond;
    private static Coach coachThird;
    private static Group groupChild;
    private static Group groupAdult;

    @BeforeAll
    public static void beforeAll() {
        coach = new Coach("Васильев", "Николай", "Сергеевич");
        coachSecond = new Coach("Петров", "Александр", "Сергеевич");
        coachThird = new Coach("Иванов", "Алексей", "Сергеевич");
        groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
    }

    @BeforeEach
    public void beforeEach() {
        timetable = new Timetable();
    }

    @Test
    void testGetTrainingSessionsForDayWithoutSession() {
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeOfDayArrayListTreeMap = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(new TimeOfDay(13, 0), timeOfDayArrayListTreeMap.firstEntry().getKey());
        assertEquals(new TimeOfDay(20, 0), timeOfDayArrayListTreeMap.lastEntry().getKey());
        // Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeWithoutSession() {
        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coachSecond,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        // Проверить, что за четверг вернулось два занятия
        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(20, 0)).size());
    }

    @Test
    void testGetCountByCoachesWithNoCoach() {
        //проверить что Не вернулся ни один тренер
        assertEquals(0, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetCountByCoachesWithOneCoach() {
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);
        //проверить что вернулся всего один тренер
        assertEquals(1, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetCountByCoachesWithMultipleCoach() {
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coachSecond,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coachThird,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        //проверить что вернулось 3 тренера
        assertEquals(3, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetCountByCoachesWithMultipleTrainingSessions() {
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        //проверить что вернулось три занятия для одного тренера
        ArrayList<CounterOfTrainings> listCounterOfTrainings = timetable.getCountByCoaches();
        assertEquals(3, listCounterOfTrainings.get(0).getCounter());
    }

    @Test
    void testGetCountByCoachesWithMultipleTrainingSessionsSortedDescending() {
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        mondayChildTrainingSession = new TrainingSession(groupChild, coachSecond,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        thursdayChildTrainingSession = new TrainingSession(groupChild, coachSecond,
                DayOfWeek.THURSDAY, new TimeOfDay(15, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        //проверить что вернулось три занятия для одного тренера
        ArrayList<CounterOfTrainings> listCounterOfTrainings = timetable.getCountByCoaches();
        assertEquals(coach, listCounterOfTrainings.get(0).getCoach());
        assertEquals(coachSecond, listCounterOfTrainings.get(1).getCoach());
    }

}

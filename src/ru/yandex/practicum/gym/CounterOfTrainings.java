package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private Coach coach;
    private int counter;

    public CounterOfTrainings(Coach coach, int counter) {
        this.coach = coach;
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public Coach getCoach() {
        return coach;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return Integer.compare(counter, o.counter);
    }

    @Override
    public String toString() {
        return coach + ": " + counter;
    }
}

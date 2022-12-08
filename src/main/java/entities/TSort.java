package entities;

import entities.base.Timetable;

import java.util.*;

public class TSort {

    /**
     * Returns the ordered list of timetables based off their timetable score, in ascending order
     * @param timetableValues a map of all the possible timetables, corresponding with their score
     * @return the list of all the possible timetables by their timetable score, in ascending order
     */
    public static List<Timetable> sort(Map<Timetable, Double> timetableValues) {
        List<Map.Entry<Timetable, Double>> list = new ArrayList<>(timetableValues.entrySet());
        list.sort(Map.Entry.comparingByValue());

        List<Timetable> timetables = new ArrayList<>();
        for (Map.Entry<Timetable, Double> entry: list){
            timetables.add(entry.getKey());
        }
        return timetables;
    }
}

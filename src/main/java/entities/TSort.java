package entities;

import entities.base.Timetable;

import java.util.*;

public class TSort {

    private TSort() {}

    /**
     * Returns a timetable which has the corresponding score as the value given
     * @param timetableScores the HashMap of all the timetables corresponding with their timetable score
     * @param valueToFind the Double value to find in the HashMap given
     * @return the timetable which is a Timetable object
     */
    private static Timetable getTimetable(Map<Timetable, Double> timetableScores, Double valueToFind) {
        Timetable timetableToReturn = null;

        for (Map.Entry<Timetable, Double> timetable : timetableScores.entrySet()) {
            if (Objects.equals(timetable.getValue(), valueToFind)) {
                timetableToReturn = timetable.getKey();
                break;
            }
        }
        return timetableToReturn;
    }

    /**
     * Returns the ordered list of timetables based off their timetable score, in ascending order
     * @param allTimetables an array of all the possible timetables, which are stored as a list of Timetable objects
     * @return the list of all the possible timetables by their timetable score, in ascending order
     */
    public static List<Timetable> sort(List<Timetable> allTimetables, Map<Timetable, Double> timetableValues) {
        ArrayList<Timetable> orderedWaitlist = new ArrayList<>();

        List<Double> valuesList = new ArrayList<>(timetableValues.values());

        while (orderedWaitlist.size() != allTimetables.size()) {
            double min = valuesList.get(0);
            for (int i = 1; i < valuesList.size(); i++) {
                if (valuesList.get(i) < min) {
                    min = valuesList.get(i);
                }
            }

            orderedWaitlist.add(getTimetable(timetableValues, min));
            valuesList.remove(min);
        }

        return orderedWaitlist;
    }
}

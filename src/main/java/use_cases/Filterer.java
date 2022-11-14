package use_cases;

import java.util.ArrayList;
import java.util.HashSet;

import entities.Timetable;

public class Filterer {
    public ArrayList<Timetable> filterAll(ArrayList<Timetable> timetable_list, HashSet<String> desired_tags) {
        ArrayList<Timetable> filtered_timetables = new ArrayList<>();

        for (Timetable timetable: timetable_list) {
            if (timetable.getTags().containsAll(desired_tags)) {
                filtered_timetables.add(timetable);
            }
        }
        return filtered_timetables;
    }
}

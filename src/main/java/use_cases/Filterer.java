package use_cases;

import java.util.ArrayList;

import entities.Timetable;
import entities.Tags;

public class Filterer {
    public ArrayList<Timetable> filterAll(ArrayList<Timetable> timetable_list, Tags[] desired_tags) {
        ArrayList<Timetable> filtered_timetables = new ArrayList<>();

        for (Timetable timetable: timetable_list) {
            if (timetable.tags in desired_tags) {
                filtered_timetables.add(timetable);
            }
        }
        return filtered_timetables;
    }
}

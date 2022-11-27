package use_cases;

import java.util.ArrayList;
import java.util.HashSet;

import entities.base.Timetable;

/**
 * This class returns a sublist of the ArrayList containing all timetables that have all the tags in desired_tags.
 */
public class Filterer {
    /**
     * A static function to filter the timetables given desired_tags argument
     * @param timetable_list the timetables to filter from
     * @param desired_tags the tags in which the timetable_list will be filtered to
     * @return a subarray of timetable_list such that every timetable's tags attribute is a superset of desired_tags
     */
    public static ArrayList<Timetable> filterAll(ArrayList<Timetable> timetable_list, HashSet<String> desired_tags) {
        ArrayList<Timetable> filtered_timetables = new ArrayList<>(); // accumulator for filtered tables

        for (Timetable timetable: timetable_list) {
            // check if all desired_tags present in the timetable's tags attribute
            if (timetable.getTags().containsAll(desired_tags)) {
                // add the timetable to the accumulator
                filtered_timetables.add(timetable);
            }
        }
        return filtered_timetables;
    }
}

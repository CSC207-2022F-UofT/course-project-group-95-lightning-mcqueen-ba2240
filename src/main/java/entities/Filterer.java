package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import entities.base.Timetable;

/**
 * This class returns a sublist of the ArrayList containing all timetables that have all the tags in desired_tags.
 */
public class Filterer {

    private Filterer(){}

    /**
     * A static function to filter the timetables given desired_tags argument
     * @param timetables the timetables to filter from
     * @param desiredTags the tags in which the timetable_list will be filtered to
     * @return a subarray of timetable_list such that every timetable's tags attribute is a superset of desired_tags
     */
    public static List<Timetable> filterAll(List<Timetable> timetables, Set<String> desiredTags) {
        List<Timetable> filteredTimetables = new ArrayList<>(); // accumulator for filtered tables

        for (Timetable timetable: timetables) {
            // check if all desired_tags present in the timetable's tags attribute
            if (timetable.getTags().containsAll(desiredTags)) {
                // add the timetable to the accumulator
                filteredTimetables.add(timetable);
            }
        }
        return filteredTimetables;
    }
}

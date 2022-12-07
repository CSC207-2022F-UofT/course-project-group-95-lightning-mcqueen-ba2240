package entities.base;

import java.sql.Time;
import java.util.ArrayList;

public interface TimetableSortable {
    /**
     * Check if an ArrayList of Timetable entities can be sorted by a user specified parameter
     * @param timetables The ArrayList of Timetable entities to check whether it is timetableSortable
     * @return boolean whether the ArrayList is sortable
     */
    boolean isTimetableSortable(ArrayList<Timetable> timetables);

    /**
     * Sort the ArrayList of Timetables from TODO: which order? (greatest to least?)
     * @param timetables The ArrayList of Timetable entities to
     * @param parameter the parameter for which the ArrayList of Timetable entities will be sorted by
     * @return the sorted ArrayList of Timetable entities
     */
    ArrayList<Timetable> timetableSort(ArrayList<Timetable> timetables, String parameter);
}

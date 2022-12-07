package use_cases.waitlist;

import entities.base.Timetable;

import java.util.List;

/**
 * This data class is responsible for handling how the waitlist feature responds when utilized.
 */

public class WaitlistResponseModel {

    List<Timetable> sortedTimetables;

    /**
     * A constructor for WaitlistResponseModel.
     * @param timetables the list of sorted timetables given from a list of timetables
     */
    public WaitlistResponseModel(List<Timetable> timetables) {
        this.sortedTimetables = timetables;
    }

    /**
     * A getter for the list of timetables.
     * @return return a list of sorted timetables as a list of timetables
     */
    public List<Timetable> getSortedTimetables() {
        return this.sortedTimetables;
    }

    /**
     * A setter for the set of courses.
     * @param timetables the sorted list of timetables to set as a list of timetables
     */
    public void setSortedTimetables(List<Timetable> timetables) {
        this.sortedTimetables = timetables;
    }
}

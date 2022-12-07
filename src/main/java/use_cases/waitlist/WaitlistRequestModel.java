package use_cases.waitlist;

import entities.base.Timetable;

import java.util.List;

/**
 * This data class is responsible for handling the required information needed for the waitlist feature
 * to perform its required tasks.
 */

public class WaitlistRequestModel {

    private List<Timetable> timetables;

    /**
     * Constructor for WaitlistRequestModel.
     * @param timetables the list of timetables given
     */
    public WaitlistRequestModel(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    /**
     * A getter for timetables
     * @return return the list of timetables as a list of timetables
     */
    public List<Timetable> getTimetables() {
        return timetables;
    }


    /**
     * A setter for timetables
     * @param timetables the timetables to be set as a list of timetables
     */
    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }


}

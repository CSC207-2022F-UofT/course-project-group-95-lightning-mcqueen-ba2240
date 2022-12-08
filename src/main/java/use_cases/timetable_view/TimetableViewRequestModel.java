package use_cases.timetable_view;

import entities.base.Timetable;

import java.util.List;

public class TimetableViewRequestModel {
    private List<Timetable> timetables;
    private int index;

    /**
     * Constructor for TimetableViewRequestModel.
     * @param index an integer used as index
     * @param timetables the list of timetables given
     */
    public TimetableViewRequestModel(List<Timetable> timetables, int index) {
        this.timetables = timetables;
        this.index = index;
    }

    /**
     * Getter for Timetables.
     * @return return list of timetables
     */
    public List<Timetable> getTimetables() {
        return timetables;
    }

    /**
     * Setter for Filter.
     * @param timetables the list of timetables to be set as timetables
     */
    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    /**
     * Getter for index.
     * @return return Index as an integer
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter for Filter.
     * @param index the integer index to be set as index
     */
    public void setIndex(int index) {
        this.index = index;
    }
}

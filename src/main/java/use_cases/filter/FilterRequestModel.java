package use_cases.filter;

import entities.base.Timetable;

import java.util.List;

/**
 * This data class is responsible for handling the required information needed for the filter feature
 * to perform its required tasks.
 */

public class FilterRequestModel {
    private String filter;
    private List<Timetable> timetables;

    /**
     * Constructor for FilterRequestModel.
     * @param filter a string representing filter
     * @param timetables the list of timetables given
     */
    public FilterRequestModel(String filter, List<Timetable> timetables) {
        this.filter = filter;
        this.timetables = timetables;
    }

    /**
     * Getter for Filter.
     * @return return a filter as a String
     */
    public String getFilter() {
        return filter;
    }
    /**
     * Setter for Filter.
     * @param filter the filter String to be set as the filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Getter for Timetables.
     * @return return a list of timetables
     */
    public List<Timetable> getTimetables() {
        return timetables;
    }

    /**
     * Setter for Timetables.
     * @param timetables the list of timetables to be set as the timetables
     */
    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}

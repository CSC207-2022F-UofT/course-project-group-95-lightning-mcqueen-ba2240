package use_cases.filter;

import entities.base.Timetable;

import java.util.List;

/**
 * This data class is responsible for handling how the filter feature responds when utilized.
 */
public class FilterResponseModel {
    private List<Timetable> timetables;

    /**
     * A constructor for FilterResponseModel.
     * @param timetables the list of sorted timetables given from a list of timetables
     */
    public FilterResponseModel(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    /**
     * A getter for the list of timetables.
     * @return return a list of sorted timetables as a list of timetables
     */
    public List<Timetable> getTimetables() {
        return timetables;
    }

    /**
     * A setter for the set of timetables.
     * @param timetables the sorted list of timetables to set as a list of timetables
     */
    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}

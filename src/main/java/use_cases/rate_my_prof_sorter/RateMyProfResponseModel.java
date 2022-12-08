package use_cases.rate_my_prof_sorter;

import entities.base.Timetable;

import java.util.List;

/**
 * This data class is responsible for containing the objects that is needed by the controller
 * to update the view.
 */
public class RateMyProfResponseModel {
    private List<Timetable> timetableList;

    /**
     * Constructor for RateMyProfResponseModel
     * @param timetableList the sorted list of timetables to be returned
     */
    public RateMyProfResponseModel(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    /**
     * A getter for the timetableList
     * @return the instance attribute timetableList
     */
    public List<Timetable> getTimetableList() {
        return timetableList;
    }

    /**
     * A setter for timetableList
     * @param timetableList the timetableList to be set as an instance attribute
     */
    public void setTimetableList(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }
}

package use_cases.rate_my_prof_sorter;

import entities.base.Timetable;

import java.util.List;

/**
 * This data class is responsible for passing the required data along to the RateMyProfInteractor whilst
 * maintaining Clean Architecture principles. It encompasses one object, a list of timetables to be sorted.
 */
public class RateMyProfRequestModel {
    private List<Timetable> timetableList;

    /**
     * Constructor for RateMyProfRequestModel, setting the timetableList which is to be sorted as an instance
     * attribute
     * @param timetableList the list to be sorted by the RateMyProfInteractor
     */
    public RateMyProfRequestModel(List<Timetable> timetableList) {
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

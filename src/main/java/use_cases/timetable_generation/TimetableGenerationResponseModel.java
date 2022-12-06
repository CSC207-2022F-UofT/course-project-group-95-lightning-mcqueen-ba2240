package use_cases.timetable_generation;

import entities.base.Timetable;


import java.util.List;


/**
* This data class is responsible for handling how the Timetable Generator feature responds when used.
 */

public class TimetableGenerationResponseModel {
    List<Timetable> timetableList;

    /**
     * A constructor function for the TimetableGenerator Response Model
     * @param timetableList is the list of potential timetables. ##COME BACK TO THIS POINT#
     */
    public TimetableGenerationResponseModel(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    /**
     * Getter method for timetableList
     * @return a list containing the potential timetables.
     */
    public List<Timetable> getTimetableList(){
        return timetableList;
    }

    /**
     * Setter method for timetableList
     * @param timetableList is the list containing the potential timetables.
     */
    public void setTimetableList(List<Timetable> timetableList){
        this.timetableList = timetableList;
    }
}

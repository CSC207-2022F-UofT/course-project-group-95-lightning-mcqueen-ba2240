package use_cases.RateMyProfSorter;

import entities.base.Timetable;

import java.util.List;

public class RateMyProfResponseModel {
    private List<Timetable> timetableList;

    public RateMyProfResponseModel(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    public List<Timetable> getTimetableList() {
        return timetableList;
    }

    public void setTimetableList(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }
}

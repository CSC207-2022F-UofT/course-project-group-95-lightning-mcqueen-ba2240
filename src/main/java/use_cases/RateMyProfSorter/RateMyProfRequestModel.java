package use_cases.RateMyProfSorter;

import entities.base.Timetable;

import java.util.List;

public class RateMyProfRequestModel {
    private List<Timetable> timetableList;

    public RateMyProfRequestModel(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    public List<Timetable> getTimetableList() {
        return timetableList;
    }

    public void setTimetableList(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }
}

package use_cases.timetable_view;

import entities.base.Timetable;

import java.util.List;

public class TimetableViewRequestModel {
    private List<Timetable> timetables;
    private int index;

    public TimetableViewRequestModel(List<Timetable> timetables, int index) {
        this.timetables = timetables;
        this.index = index;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

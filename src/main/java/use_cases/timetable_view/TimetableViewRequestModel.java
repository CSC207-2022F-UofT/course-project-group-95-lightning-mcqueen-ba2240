package use_cases.timetable_view;

import entities.base.Timetable;

public class TimetableViewRequestModel {
    private Timetable timetable;

    public TimetableViewRequestModel(Timetable timetable) {
        this.timetable = timetable;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
}

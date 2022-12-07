package use_cases.persistance;

import entities.base.Timetable;

import java.util.List;

public class PersistenceDataModel {
    List<Timetable> timetables;

    public PersistenceDataModel(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}

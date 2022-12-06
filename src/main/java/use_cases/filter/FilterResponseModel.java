package use_cases.filter;

import entities.base.Timetable;

import java.util.List;

public class FilterResponseModel {
    private List<Timetable> timetables;

    public FilterResponseModel(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}

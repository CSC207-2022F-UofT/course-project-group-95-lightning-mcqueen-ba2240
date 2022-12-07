package use_cases.filter;

import entities.base.Timetable;

import java.util.List;

public class FilterRequestModel {
    private String filter;
    private List<Timetable> timetables;

    public FilterRequestModel(String filter, List<Timetable> timetables) {
        this.filter = filter;
        this.timetables = timetables;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}

package use_cases.timetable_view;

public interface TimetableViewInputBoundary {
    TimetableViewResponseModel getView(TimetableViewRequestModel model);
}

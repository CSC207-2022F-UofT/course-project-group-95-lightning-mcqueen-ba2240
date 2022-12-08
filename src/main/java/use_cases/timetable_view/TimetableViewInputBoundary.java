package use_cases.timetable_view;

/**
 * An interface implemented by TimetableViewInteractor
 */
public interface TimetableViewInputBoundary {
    TimetableViewResponseModel getView(TimetableViewRequestModel model);
}

package use_cases.timetable_view;

import entities.TimetableViewer;

public class TimetableViewInteractor implements TimetableViewInputBoundary{
    /**
     * Return a CalendarFXDetailedWeekView of a Timetable
     * @param model A TimetableViewRequestModel containing a Timetable object
     * @return A TimetableViewResponseModel containing the Node(CalendarFx.DetailedWeekView)
     */
    @Override
    public TimetableViewResponseModel getView(TimetableViewRequestModel model) {
        return new TimetableViewResponseModel(TimetableViewer.get(model.getTimetable()));
    }
}

package use_cases.timetable_view;

import entities.TimetableViewer;
import entities.base.Timetable;

public class TimetableViewInteractor implements TimetableViewInputBoundary{
    /**
     * Return a CalendarFXDetailedWeekView of a Timetable
     * @param model A TimetableViewRequestModel containing a Timetable object
     * @return A TimetableViewResponseModel containing the Node(CalendarFx.DetailedWeekView)
     */
    @Override
    public TimetableViewResponseModel getView(TimetableViewRequestModel model) {
        Timetable timetable = model.getTimetables().get(model.getIndex());
        return new TimetableViewResponseModel(TimetableViewer.get(timetable),
                String.join(", ", timetable.getTags()));
    }
}

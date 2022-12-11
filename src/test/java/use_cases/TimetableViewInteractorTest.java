package use_cases;

import com.calendarfx.model.Calendar;
import com.calendarfx.view.DetailedWeekView;
import entities.base.Meeting;
import entities.base.Session;
import entities.base.Timetable;
import entities.stgartsci.StGArtSciMeeting;
import javafx.application.Platform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_cases.timetable_view.TimetableViewInteractor;
import use_cases.timetable_view.TimetableViewRequestModel;
import use_cases.timetable_view.TimetableViewResponseModel;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class TimetableViewInteractorTest{

    @Test
    @BeforeAll
    static void testTimetableViewInteractorNode() {
        Platform.startup(() -> {
            Session session1 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(5,0));
            Session session2 = new Session(DayOfWeek.MONDAY, LocalTime.of(5, 0), LocalTime.of(6,0));

            Meeting meeting1 = new StGArtSciMeeting("0101", StGArtSciMeeting.StGArtSciType.TUT, "L McQueen", 100, 70, 0, session1, session2);

            Timetable timetable = new Timetable(List.of(meeting1), Set.of("Tag"));

            TimetableViewInteractor interactor = new TimetableViewInteractor();
            TimetableViewRequestModel request = new TimetableViewRequestModel(List.of(timetable), 0);
            TimetableViewResponseModel response  = interactor.getView(request);

            DetailedWeekView node = (DetailedWeekView) response.getNode();

            Calendar calendar = node.getCalendarSources().get(0).getCalendars().get(0);
            List entries = calendar.findEntries(meeting1.getSection());
            Assertions.assertEquals(2, entries.size());
        });
    }

    @Test
    void testTimetableViewInteractorTags() {
        Platform.runLater(() -> {
            Timetable timetable = new Timetable(new ArrayList<>(), Set.of("Tag"));

            TimetableViewInteractor interactor = new TimetableViewInteractor();
            TimetableViewRequestModel request = new TimetableViewRequestModel(List.of(timetable), 0);
            TimetableViewResponseModel response  = interactor.getView(request);

            Assertions.assertEquals("Tag", response.getTags());
        });

    }
}

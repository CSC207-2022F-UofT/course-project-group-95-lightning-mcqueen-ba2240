package entities;

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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

class TimetableViewerTest{
    @Test
    @BeforeAll
    static void testViewer() {
        Platform.startup(() -> {
            Session session1 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(5,0));
            Session session2 = new Session(DayOfWeek.MONDAY, LocalTime.of(5, 0), LocalTime.of(6,0));

            Meeting meeting1 = new StGArtSciMeeting("0101", StGArtSciMeeting.StGArtSciType.TUT, "L McQueen", 100, 70, 0, session1, session2);

            Timetable timetable = new Timetable(List.of(meeting1), new HashSet<>());

            DetailedWeekView node = (DetailedWeekView) TimetableViewer.get(timetable);

            Calendar calendar = node.getCalendarSources().get(0).getCalendars().get(0);
            List entries = calendar.findEntries(meeting1.getSection());
            Assertions.assertEquals(2, entries.size());
        });
    }
}

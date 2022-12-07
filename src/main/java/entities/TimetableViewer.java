package entities;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.DetailedWeekView;
import entities.base.Meeting;
import entities.base.Session;
import entities.base.Timetable;
import javafx.scene.Node;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableViewer {

    private TimetableViewer(){}

    public static Node get(Timetable timetable) {
        DetailedWeekView calendarView = new DetailedWeekView();

        calendarView.setNumberOfDays(6);
        calendarView.setDate(getWeek().get(0));
        calendarView.setStartTime(LocalTime.parse("08:00"));
        calendarView.setAdjustToFirstDayOfWeek(true);
        calendarView.setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        calendarView.setVisibleHours(14);

        CalendarSource source = new CalendarSource("");
        source.getCalendars().addAll(getCalendars(timetable));

        calendarView.getCalendarSources().addAll(source);

        return calendarView;
    }

    private static List<Calendar> getCalendars(Timetable timetable) {
        List<Calendar> calendars = new ArrayList<>();
        List<Meeting> meetings = timetable.getMeetings();
        for (Meeting meeting: meetings){
            Calendar calendar = getCalendar(meeting);
            // Hack to match colors for Meetings of the same Course
            calendar.setStyle(Style.getStyle(meeting.getSection().substring(0, 5).hashCode()));
            calendars.add(calendar);
        }
        return calendars;
    }

    private static Calendar getCalendar(Meeting meeting) {
        List<LocalDate> week = getWeek();
        Calendar calendar = new Calendar(meeting.getSection());
        for (Session session: meeting.getSessions()){
            LocalDate sessionDay = week.get(session.getDay().getValue() - 1);
            calendar.addEntry(new Entry<>(meeting.getSection(),
                    new Interval(sessionDay, session.getStartTime(), sessionDay, session.getEndTime())
            ));
        }
        return calendar;
    }

    private static List<LocalDate> getWeek() {
        LocalDate today = LocalDate.now();
        List<LocalDate> week = new ArrayList<>(List.of(today));

        // Go backward to get Monday
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
            week.add(0, monday);
        }

        // Go forward to get Sunday
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
            week.add(sunday);
        }
        return week;
    }
}

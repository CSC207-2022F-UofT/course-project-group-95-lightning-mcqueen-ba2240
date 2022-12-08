package use_cases.filter;

import entities.base.Meeting;
import entities.base.Timetable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterRequestModelTest {

    private static List<Timetable> getTestTimetables() {
        List<Meeting> exampleMeetings = new ArrayList<>();

        Timetable timetable1 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Morning-heavy", "Tag1")));
        Timetable timetable2 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Afternoon-heavy", "Tag1", "Tag2")));
        Timetable timetable3 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Evening-heavy", "Tag1", "Tag2")));

        return List.of(timetable1, timetable2, timetable3);
    }

    /**
     * Test that the getFilter() method returns the correct filter string
     */
    @Test
    void testGetFilter() {
        String filter_str = "Morning-heavy";
        List<Timetable> timetable_list = getTestTimetables();

        FilterRequestModel request = new FilterRequestModel(filter_str, timetable_list);

        assertEquals(filter_str, request.getFilter());
    }

    /**
     * Test that the getTimetables() method returns the correct timetable list
     */
    @Test
    void testGetTimetables() {
        String filter_str = "Morning-heavy";
        List<Timetable> timetable_list = getTestTimetables();

        FilterRequestModel request = new FilterRequestModel(filter_str, timetable_list);

        assertEquals(timetable_list, request.getTimetables());
    }
}
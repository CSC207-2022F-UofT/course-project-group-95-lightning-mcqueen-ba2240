package use_cases.filter;

import entities.base.Meeting;
import entities.base.Timetable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


class FilterInteractorTest {
    private final FilterInteractor filter = new FilterInteractor();
    private static List<Timetable> getTestTimetables() {
        List<Meeting> exampleMeetings = new ArrayList<>();

        Timetable timetable1 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Morning-heavy", "Tag1")));
        Timetable timetable2 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Afternoon-heavy", "Tag1", "Tag2")));
        Timetable timetable3 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Evening-heavy", "Tag1", "Tag2")));

        return List.of(timetable1, timetable2, timetable3);
    }

    /**
     * Test that the filter functionality properly filters the given timetables to only include the ones
     * that possess the desired tags
     */
    @Test
    void testFilter() {
        String filter_str = "Morning-heavy";
        List<Timetable> timetable_list = getTestTimetables();

        FilterRequestModel request = new FilterRequestModel(filter_str, timetable_list);

        FilterResponseModel response = filter.filter(request);
        List<Timetable> response_timetables = response.getTimetables();
        assertTrue(1 == response_timetables.size() &&
                new HashSet<>(response_timetables.get(0).getTags()).equals(new HashSet<>(Arrays.asList("Morning-heavy", "Tag1"))));
    }
}
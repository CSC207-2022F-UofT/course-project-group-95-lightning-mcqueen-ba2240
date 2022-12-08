package use_cases.filter;

import entities.base.Meeting;
import entities.base.Timetable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterResponseModelTest {

    private static List<Timetable> getTestTimetables() {
        List<Meeting> exampleMeetings = new ArrayList<>();

        Timetable timetable1 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Morning-heavy", "Tag1")));
        Timetable timetable2 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Afternoon-heavy", "Tag1", "Tag2")));

        return List.of(timetable1, timetable2);
    }

    /**
     * Test that the getTimetables() method returns the correct timetable list
     */
    @Test
    void testGetTimetables() {
        List<Timetable> timetable_list = getTestTimetables();

        FilterResponseModel response = new FilterResponseModel(timetable_list);

        assertEquals(response.getTimetables(), timetable_list);
    }
}
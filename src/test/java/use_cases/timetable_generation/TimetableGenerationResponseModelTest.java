package use_cases.timetable_generation;

import entities.base.Meeting;
import entities.base.Timetable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableGenerationResponseModelTest {

    private static List<Timetable> getTestTimetables() {
        List<Meeting> exampleMeetings = new ArrayList<>();

        Timetable timetable1 = new Timetable(exampleMeetings, new HashSet<>());
        Timetable timetable2 = new Timetable(exampleMeetings, new HashSet<>());
        Timetable timetable3 = new Timetable(exampleMeetings, new HashSet<>());

        return List.of(timetable1, timetable2, timetable3);
    }

    /**
     * Test that getTimetableList() returns the proper list of timetables
     */
    @Test
    void testGetTimetableList() {
        List<Timetable> timetable_list = getTestTimetables();
        TimetableGenerationResponseModel response = new TimetableGenerationResponseModel(timetable_list);

        assertEquals(timetable_list, response.getTimetableList());
    }
}
package use_cases;

import entities.base.Meeting;
import entities.base.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static use_cases.TimetableValidator.validateMeetings;

class TimetableValidatorTest {

    /**
     * Test if the TimetableValidator class outputs the correct boolean object if there is a session overlap
     */
    @Test
    void testValidateMeetingsTimeOverlap() {
        Session session1 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(5,0));
        Session session2 = new Session(DayOfWeek.MONDAY, LocalTime.of(4, 0), LocalTime.of(6,0));

        Meeting meeting1 = new Meeting("0101", Meeting.DefaultType.DEFAULT, "L McQueen", 100, 70, 0, session1);
        Meeting meeting2 = new Meeting("0201", Meeting.DefaultType.DEFAULT, "L McQueen", 100, 70, 0, session2);
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting1);
        meetings.add(meeting2);

        boolean checkOverlap = validateMeetings(meetings);

        Assertions.assertFalse(checkOverlap);
    }

    /**
     * Test if the TimetableValidator class outputs the correct
     * boolean object if there is a time overlap but no day overlap
     */
    @Test
    void testValidateMeetingsNoDayOverlap() {
        Session session1 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(5,0));
        Session session2 = new Session(DayOfWeek.TUESDAY, LocalTime.of(4, 0), LocalTime.of(6,0));

        Meeting meeting1 = new Meeting("0101", Meeting.DefaultType.DEFAULT, "L McQueen", 100, 70, 0, session1);
        Meeting meeting2 = new Meeting("0201", Meeting.DefaultType.DEFAULT, "L McQueen", 100, 70, 0, session2);
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting1);
        meetings.add(meeting2);

        boolean checkOverlap = validateMeetings(meetings);

        Assertions.assertTrue(checkOverlap);
    }

    /**
     * Test if the TimetableValidator class outputs the correct boolean object if there is no session overlap
     */
    @Test
    void testValidateMeetingsNoTimeOverlap() {
        Session session1 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(5,0));
        Session session2 = new Session(DayOfWeek.MONDAY, LocalTime.of(5, 0), LocalTime.of(6,0));

        Meeting meeting1 = new Meeting("0101", Meeting.DefaultType.DEFAULT, "L McQueen", 100, 70, 0, session1);
        Meeting meeting2 = new Meeting("0201", Meeting.DefaultType.DEFAULT, "L McQueen", 100, 70, 0, session2);
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting1);
        meetings.add(meeting2);

        boolean checkOverlap = validateMeetings(meetings);

        Assertions.assertTrue(checkOverlap);
    }
}

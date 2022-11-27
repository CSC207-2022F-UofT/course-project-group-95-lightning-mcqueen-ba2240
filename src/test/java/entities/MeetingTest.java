package entities;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class MeetingTest {
    @Test
    public void testTimeHashSameMeetings(){
        Session session1 = new Session(
                DayOfWeek.TUESDAY,
                LocalTime.parse("10:00"),
                LocalTime.parse("12:00"), ""
        );

        Session session2 = new Session(
                DayOfWeek.THURSDAY,
                LocalTime.parse("15:00"),
                LocalTime.parse("20:00"), ""
        );

        Meeting meeting1 = new Meeting(
                "0101", Meeting.Type.LEC, "John Adams",
                5, 0, 0, session1, session2);

        Meeting meeting2 = new Meeting(
                "0205", Meeting.Type.TUT, "Jeff Jeffery",
                1, 1, 2, session2, session1);

        Assertions.assertEquals(meeting1.timeHash(), meeting2.timeHash());
    }

    @Test
    public void testTimeHashDiffMeetings(){
        Session session1 = new Session(
                DayOfWeek.TUESDAY,
                LocalTime.parse("10:00"),
                LocalTime.parse("12:00"), ""
        );

        Session session2 = new Session(
                DayOfWeek.THURSDAY,
                LocalTime.parse("15:00"),
                LocalTime.parse("20:00"), ""
        );

        Session session3 = new Session(
                DayOfWeek.WEDNESDAY,
                LocalTime.parse("15:00"),
                LocalTime.parse("20:00"), ""
        );

        Meeting meeting1 = new Meeting(
                "0101", Meeting.Type.LEC, "John Adams",
                5, 0, 0, session1, session2);

        Meeting meeting2 = new Meeting(
                "0205", Meeting.Type.TUT, "Jeff Jeffery",
                1, 1, 2, session2, session3);

        Assertions.assertNotEquals(meeting1.timeHash(), meeting2.timeHash());
    }
}

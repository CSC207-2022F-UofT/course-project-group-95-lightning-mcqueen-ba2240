package gateways;

import entities.base.Meeting;
import entities.base.Session;
import entities.base.Timetable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_cases.Waitlist;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

public class WaitlistTest {
    @Test
    public void TestWaitlistOrder() {
        // Test if the averageWaitlistOrder function returns the sorted list of timetables by their timetable score
        // in descending order

        Session s1 = new Session(DayOfWeek.MONDAY,
                LocalTime.of(1, 0), LocalTime.of(2, 0));

        Meeting m1 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 100, 90,0, s1);
        Meeting m2 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 100, 100,20, s1);
        Meeting m3 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 250, 0,0, s1);
        Meeting m4 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 500, 500,60, s1);
        Meeting m5 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 100, 70,0, s1);

        Meeting m6 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 900, 900,112, s1);
        Meeting m7 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 100, 80,0, s1);
        Meeting m8 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 250, 250,10, s1);

        Meeting m9 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 100, 100,5, s1);
        Meeting m10 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 250, 250,15, s1);
        Meeting m11 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 100, 65,0, s1);
        Meeting m12 = new Meeting("", Meeting.DefaultType.DEFAULT, "", 100, 100,0, s1);

        // For the purposes of this function, the session can be the same for all meetings as it is not used at all


        List<Meeting> meetings1 = List.of(m1, m2, m3, m4, m5); // The score here for this timetable should be 1.08
        List<Meeting> meetings2 = List.of(m6, m7, m8); // The score for this timetable should be 0.0355...
        List<Meeting> meetings3 = List.of(m9, m10, m11, m12); // The score for this timetable should be 0.24

        Timetable timetable1 = new Timetable(meetings1, new HashSet<>());
        Timetable timetable2 = new Timetable(meetings2, new HashSet<>());
        Timetable timetable3 = new Timetable(meetings3, new HashSet<>());


        List<Timetable> allTimetables = List.of(timetable1, timetable2, timetable3);
        List<Timetable> expectedOutput = List.of(timetable2, timetable3, timetable1);

        Assertions.assertEquals(expectedOutput, Waitlist.averageWaitlistOrder(allTimetables));
    }
}



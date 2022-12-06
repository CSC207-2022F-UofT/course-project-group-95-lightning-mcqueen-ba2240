package entities;

import entities.base.Course;
import entities.base.Meeting;
import entities.base.Session;
import entities.base.Timetable;
import entities.stgartsci.StGArtSciMeeting;
import entities.stgartsci.StGArtSciMeeting.StGArtSciType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

class TimetableFactoryTest {
    /**
     * Test that meetings that are a Tutorial or Practical and share the same
     * time signature will collapse into one meeting to generate fewer timetables
     */
    @Test
    void testMeetingCollapse() {
        Session session1 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(5,0));
        Session session2 = new Session(DayOfWeek.MONDAY, LocalTime.of(5, 0), LocalTime.of(6,0));

        Session session3 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(5,0));
        Session session4 = new Session(DayOfWeek.MONDAY, LocalTime.of(5, 0), LocalTime.of(6,0));

        Meeting meeting1 = new StGArtSciMeeting("0101", StGArtSciType.TUT, "L McQueen", 100, 70, 0, session1, session2);
        Meeting meeting2 = new StGArtSciMeeting("0102", StGArtSciType.TUT, "L McQueen", 100, 70, 0, session3, session4);

        Course course = new Course(0, "", "", meeting1, meeting2);

        List<Timetable> timetables = TimetableFactory.generate(List.of(course));
        Assertions.assertEquals(1, timetables.size());
    }

    /**
     * Given each course has n_i non-conflicting lectures the number of timetables
     * must be n_1 * n_2 * ... * n_k where k is the number of courses
     */
    @Test
    void testPermutations() {
        Session s1 = new Session(DayOfWeek.MONDAY, LocalTime.of(1, 0), LocalTime.of(2,0));
        Meeting m1 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s1);

        Session s2 = new Session(DayOfWeek.TUESDAY, LocalTime.of(1, 0), LocalTime.of(2,0));
        Meeting m2 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s2);

        Session s3 = new Session(DayOfWeek.WEDNESDAY, LocalTime.of(1, 0), LocalTime.of(2,0));
        Meeting m3 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s3);

        Course c1 = new Course(0, "", "", m1, m2, m3);

        Session s4 = new Session(DayOfWeek.MONDAY, LocalTime.of(2, 0), LocalTime.of(3,0));
        Meeting m4 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s4);

        Session s5 = new Session(DayOfWeek.TUESDAY, LocalTime.of(2, 0), LocalTime.of(3,0));
        Meeting m5 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s5);

        Course c2 = new Course(0, "", "", m4, m5);

        Session s6 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m6 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s6);

        Session s7 = new Session(DayOfWeek.TUESDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m7 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s7);

        Session s8 = new Session(DayOfWeek.WEDNESDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m8 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s8);

        Session s9 = new Session(DayOfWeek.THURSDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m9 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s9);

        Course c3 = new Course(0, "", "", m6, m7, m8, m9);

        List<Timetable> timetables = TimetableFactory.generate(List.of(c1, c2, c3));

        Assertions.assertEquals(timetables.size(), 3 * 2 * 4);
    }

    /**
     * Given each course has n_i non-conflicting lectures the number of timetables
     * must be n_1 * n_2 * ... * n_k where k is the number of courses minus any conflicts
     */
    @Test
    void testPermutationsConflicts() {
        Session s1 = new Session(DayOfWeek.MONDAY, LocalTime.of(1, 0), LocalTime.of(2,0));
        Meeting m1 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s1);

        Session s2 = new Session(DayOfWeek.TUESDAY, LocalTime.of(1, 0), LocalTime.of(2,0));
        Meeting m2 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s2);

        Session s3 = new Session(DayOfWeek.WEDNESDAY, LocalTime.of(1, 0), LocalTime.of(2,0));
        Meeting m3 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s3);

        Course c1 = new Course(0, "", "", m1, m2, m3);

        Session s4 = new Session(DayOfWeek.MONDAY, LocalTime.of(1, 0), LocalTime.of(2,0));
        Meeting m4 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s4);

        Session s5 = new Session(DayOfWeek.TUESDAY, LocalTime.of(2, 0), LocalTime.of(3,0));
        Meeting m5 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s5);

        Course c2 = new Course(0, "", "", m4, m5);

        Session s6 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m6 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s6);

        Session s7 = new Session(DayOfWeek.TUESDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m7 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s7);

        Session s8 = new Session(DayOfWeek.WEDNESDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m8 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s8);

        Session s9 = new Session(DayOfWeek.THURSDAY, LocalTime.of(3, 0), LocalTime.of(4,0));
        Meeting m9 = new Meeting("", StGArtSciType.LEC, "", 0, 0, 0, s9);

        Course c3 = new Course(0, "", "", m6, m7, m8, m9);

        List<Timetable> timetables = TimetableFactory.generate(List.of(c1, c2, c3));

        Assertions.assertEquals(timetables.size(), (3 * 2 * 4) - 4);
    }
}

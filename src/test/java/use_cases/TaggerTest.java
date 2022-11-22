package use_cases;

import entities.Session;
import entities.Meeting;
import entities.Course;
import entities.Timetable;

import org.junit.jupiter.api.Assertions;
import use_cases.Tagger;

import java.security.KeyStore;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

    class TaggerTest {

        Session s1 = new Session(DayOfWeek.MONDAY, LocalTime.of(1, 0), LocalTime.of(2,0),
                "");
        Meeting m1 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s1);

        Session s2 = new Session(DayOfWeek.TUESDAY, LocalTime.of(1, 0), LocalTime.of(2,0),
                "");
        Meeting m2 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s2);

        Session s3 = new Session(DayOfWeek.WEDNESDAY, LocalTime.of(1, 0), LocalTime.of(2,0),
                "");
        Meeting m3 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s3);

        Course c1 = new Course(0, "", "", m1, m2, m3);

        Session s4 = new Session(DayOfWeek.MONDAY, LocalTime.of(1, 0), LocalTime.of(2,0),
                "");
        Meeting m4 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s4);

        Session s5 = new Session(DayOfWeek.TUESDAY, LocalTime.of(2, 0), LocalTime.of(3,0),
                "");
        Meeting m5 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s5);

        Course c2 = new Course(0, "", "", m4, m5);

        Session s6 = new Session(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(4,0),
                "");
        Meeting m6 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s6);

        Session s7 = new Session(DayOfWeek.TUESDAY, LocalTime.of(3, 0), LocalTime.of(4,0),
                "");
        Meeting m7 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s7);
        Session s8 = new Session(DayOfWeek.WEDNESDAY, LocalTime.of(3, 0), LocalTime.of(4,0),
                "");
        Meeting m8 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s8);

        Session s9 = new Session(DayOfWeek.THURSDAY, LocalTime.of(3, 0), LocalTime.of(4,0),
                "");
        Meeting m9 = new Meeting("", Meeting.Type.LEC, "", 0, 0, 0, s9);

        Course c3 = new Course(0, "", "", m6, m7, m8, m9);

        ArrayList<Meeting> meetings1 = new ArrayList<Meeting>(Arrays.asList(m1, m2));
        Timetable timetable1 = new Timetable(meetings1, new HashSet<>());

        HashSet<String> tags = Tagger.main(timetable1);
        HashSet<String> runOutput = new HashSet<>(Arrays.asList("Balanced", "Long Weekend"));

        if (tags.containsAll("Balanced", "Long Weekend")) {expectedOutput = true};



    }
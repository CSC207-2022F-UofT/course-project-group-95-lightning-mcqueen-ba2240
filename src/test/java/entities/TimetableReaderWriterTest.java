package entities;

import entities.base.Meeting;
import entities.base.Session;
import entities.base.Timetable;
import entities.stgartsci.StGArtSciMeeting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class TimetableReaderWriterTest {
    @Test
    void TestWriteToJSON() throws IOException {
        Session session = new Session(DayOfWeek.MONDAY, LocalTime.parse("12:00"), LocalTime.parse("18:00"));
        Meeting meetings = new Meeting("Alex", StGArtSciMeeting.StGArtSciType.LEC, "Arsal",
                30, 100, 120, session);


        ArrayList<Meeting> meetingsArray = new ArrayList<>();
        meetingsArray.add(meetings);

        HashSet<String> tags = new HashSet<>();
        tags.add("1");
        tags.add("2");
        tags.add("3");

        Timetable timetable = new Timetable(meetingsArray, tags);
        ArrayList<Timetable> timeTableArray = new ArrayList<>();
        timeTableArray.add(timetable);

        TimetableReaderWriter timetablereaderwriter = new TimetableReaderWriter();
        timetablereaderwriter.saveToJson(timeTableArray);

        BufferedReader bufferedTest = new BufferedReader(new FileReader("./data.json"));

        // read file and check if json is same file
        // compare the two files by copying the data.json file
        String fileContent = "[{\"meetings\":[{\"section\":\"Alex\",\"type\":\"LEC\",\"sessions\":[{\"day\":\"MONDAY\",\"startTime\":\"12:00\",\"endTime\":\"18:00\"}],\"instructor\":\"Arsal\",\"capacity\":30,\"enrollment\":100,\"waitlist\":120,\"rateMyProf\":null}],\"tags\":[\"1\",\"2\",\"3\"]}]";
        Assertions.assertEquals(fileContent, bufferedTest.readLine());
    }
    @Test
    void TestReadFromJSON() {
        Session session = new Session(DayOfWeek.MONDAY, LocalTime.parse("12:00"), LocalTime.parse("18:00"));
        Meeting meetings = new Meeting("Alex", StGArtSciMeeting.StGArtSciType.LEC,
                "Arsal", 30, 100, 120, session);

        ArrayList<Meeting> meetingsArray = new ArrayList<>();
        meetingsArray.add(meetings);

        HashSet<String> tags = new HashSet<>();
        tags.add("1");
        tags.add("2");
        tags.add("3");

        Timetable timetable = new Timetable(meetingsArray, tags);
        ArrayList<Timetable> timeTableArray = new ArrayList<>();
        timeTableArray.add(timetable);

        TimetableReaderWriter timetablereaderwriter = new TimetableReaderWriter();
        timetablereaderwriter.saveToJson(timeTableArray);

        List<Timetable> timetables = timetablereaderwriter.readFromJson();

        Assertions.assertEquals(timeTableArray, timetables);
    }

}
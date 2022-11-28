package use_cases;

import entities.Meeting;
import entities.Timetable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TimetableReaderWriterTest {
    @Test
    public void TestWriteToJSON() throws IOException {
        Meeting meetings = new Meeting("Alex", Meeting.Type.LEC, "Arsal", 30, 100, 120);

        ArrayList<Meeting> meetingsArray = new ArrayList<Meeting>();
        meetingsArray.add(meetings);

        HashSet<String> tags = new HashSet<>();
        tags.add("1");
        tags.add("2");
        tags.add("3");


        Timetable timetable = new Timetable(meetingsArray, tags);
        ArrayList<Timetable> timeTableArray = new ArrayList<Timetable>();
        timeTableArray.add(timetable);

        TimetableReaderWriter timetablereaderwriter = new TimetableReaderWriter();
        timetablereaderwriter.saveToJson(timeTableArray);

        BufferedReader bufferedTest = new BufferedReader(new FileReader("./data.json"));

        // read file and check if json is same file
        // compare the two files by copying the data.json file

        String fileContent = new String("[{\"meetings\":[{\"section\":\"Alex\",\"type\":\"LEC\",\"sessions\":[],\"instructor\":\"Arsal\",\"capacity\":30,\"enrollment\":100,\"waitlist\":120,\"rateMyProf\":null}],\"tags\":[\"1\",\"2\",\"3\"]}]");
        Assertions.assertEquals(fileContent, bufferedTest.readLine());
    }
    @Test
    public void TestReadFromJSON() {
        Meeting meetings = new Meeting("Alex", Meeting.Type.LEC, "Arsal", 30, 100, 120);

        ArrayList<Meeting> meetingsArray = new ArrayList<Meeting>();
        meetingsArray.add(meetings);

        HashSet<String> tags = new HashSet<>();
        tags.add("1");
        tags.add("2");
        tags.add("3");


        Timetable timetable = new Timetable(meetingsArray, tags);
        ArrayList<Timetable> timeTableArray = new ArrayList<Timetable>();
        timeTableArray.add(timetable);

        TimetableReaderWriter timetablereaderwriter = new TimetableReaderWriter();
        timetablereaderwriter.saveToJson(timeTableArray);

        List<Timetable> timetables = timetablereaderwriter.readFromJson();
        System.out.println(timetables);
        // check that object is exactly the same
        // created object
        // read from json
        // compare if equal

        Assertions.assertEquals(timeTableArray, timetables);
    }

}

package use_cases;

import entities.base.Meeting;
import entities.base.Session;
import entities.base.Timetable;
import entities.stgartsci.StGArtSciMeeting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import use_cases.persistence.PersistenceDataModel;
import use_cases.persistence.PersistenceInteractor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class PersistenceTest {
    private static List<Timetable> getTimetable() {
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

        return timeTableArray;
    }

    @Test
    void TestPersistence(){
        List<Timetable> exampleTimetable = getTimetable();
        PersistenceDataModel request = new PersistenceDataModel(exampleTimetable);
        PersistenceInteractor interactor = new PersistenceInteractor();
        interactor.save(request);
        PersistenceDataModel response = interactor.load();
        Assertions.assertEquals(exampleTimetable, response.getTimetables());
    }

}

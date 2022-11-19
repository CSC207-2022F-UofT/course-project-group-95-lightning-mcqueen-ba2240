package use_cases;

import entities.Meeting;
import entities.Session;
import entities.Timetable;

import java.util.HashSet;

public class Tagger {
    public static HashSet<String> main(Timetable timetable) {
        HashSet<String> tags = new HashSet<>();

        for (Meeting meeting: timetable.getMeetings()) {
            for (Session session : meeting.getSessions()) {

            }
        }
        return tags;
    }

    //Course Density function
    public void courseDensity(Timetable timetable) {
        
    }
}

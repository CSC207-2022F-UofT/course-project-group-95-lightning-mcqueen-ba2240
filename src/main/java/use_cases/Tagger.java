package use_cases;

import entities.Meeting;
import entities.Session;
import entities.Timetable;

import java.lang.reflect.Array;
import java.util.*;

public class Tagger {
    public static HashSet<String> main(Timetable timetable) {
        HashSet<String> tags = new HashSet<>();

        //Initial variables required for the Density Tag
        Session lastSession = null;
        List<Integer> consecutiveCount = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0));


        for (Session session : timetable.getSortedSessions()) {
            checkDensity(lastSession, session, consecutiveCount);
            addDensityTag(tags, consecutiveCount);
            lastSession = session;
        }
        return tags;
    }

    //Function to check if 2 sessions are consecutive
    private static Boolean consecutiveSessions(Session session1, Session session2) {
        if ((session1 != null) && (session1.getMeetingDay() == session2.getMeetingDay())) {
            return (session1.getMeetingEndTime().equals(session2.getMeetingStartTime())) ||
                    (session2.getMeetingEndTime().equals(session1.getMeetingStartTime()));
        }
        return false;
    }

    //Function to assign values to each element in the consecutiveCount list, where each element is the day's density of
    //courses.
    private static void checkDensity(Session lastSession, Session currentSession, List<Integer> consecutiveCount) {
        if (consecutiveSessions(lastSession, currentSession)) {
            int index = currentSession.getMeetingDay().getValue();
            consecutiveCount.set(index, consecutiveCount.get(index) + 1);
        }
    }

    //Adds the Dense tag to the list of tags if a timetable contains 3 or more dense days
    // (with 3 consecutive sessions in a day)
    private static void addDensityTag(HashSet<String> tags, List<Integer> consecutiveCount) {
        int numberOfConsecutiveDays = 0;
        for (int count: consecutiveCount) {
            if (count >= 3) {
                numberOfConsecutiveDays += 1;
            }
        }
        if (numberOfConsecutiveDays >= 3) {
            tags.add("Dense");
        }
    }
}

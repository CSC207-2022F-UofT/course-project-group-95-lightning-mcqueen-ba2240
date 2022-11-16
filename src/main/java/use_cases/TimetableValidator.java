package use_cases;

import entities.Meeting;
import entities.Session;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * A timetable validator class that checks if a timetable has no time conflicts
 */
public class TimetableValidator {
    /**
     * @param session1 one session of a meeting
     * @param session2 second session of a meeting
     * @return true if the sessions overlap with each other, otherwise false
     */
    public static boolean sessionOverlap(Session session1, Session session2){
        LocalTime s1start = session1.getMeetingStartTime().plusMinutes(10);
        LocalTime s2start = session2.getMeetingStartTime().plusMinutes(10);
        LocalTime s1end = session1.getMeetingEndTime();
        LocalTime s2end = session2.getMeetingEndTime();
        DayOfWeek s1day = session1.getMeetingDay();
        DayOfWeek s2day = session2.getMeetingDay();

        if (s1day.equals(s2day)){
            if (s1start.equals(s2start) || s1end.equals(s2end)){
                return true;
            } else {
                if (s1start.isBefore(s2start)){
                    if (s1end.isAfter(s2start)){
                        return true;
                    }
                } else {
                    if (s2end.isAfter(s1start)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param meeting1 one meeting in a timetable
     * @param meeting2 second meeting in a timetable
     * @return true if the meetings contain sessions which overlap, otherwise false
     */
    private static boolean meetingOverlap(Meeting meeting1, Meeting meeting2){
        for (Session session1 : meeting1.getSessions()){
            for (Session session2 : meeting2.getSessions()){
                if (sessionOverlap(session1, session2)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param meetings meetings in a timetable
     * @return true if the meetings contain sessions which overlap, otherwise false
     */
    public static boolean validateMeetings(List<Meeting> meetings){
        for (int i = 0; i < meetings.size(); i++){
            for (int j = 0; j < meetings.size(); j++){
                if (meetingOverlap((meetings.get(i)), meetings.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

}

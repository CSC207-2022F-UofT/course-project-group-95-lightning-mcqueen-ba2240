package entities;

import entities.base.Meeting;
import entities.base.Session;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * A timetable validator class that checks if a timetable has no time conflicts
 */
public class TimetableValidator {

    private TimetableValidator() {}

    /**
     * @param session1 one session of a meeting
     * @param session2 second session of a meeting
     * @return true if the sessions overlap with each other, otherwise false
     */
    private static boolean sessionOverlap(Session session1, Session session2){
        LocalTime s1start = session1.getStartTime().plusMinutes(10);
        LocalTime s2start = session2.getStartTime().plusMinutes(10);
        LocalTime s1end = session1.getEndTime();
        LocalTime s2end = session2.getEndTime();
        DayOfWeek s1day = session1.getDay();
        DayOfWeek s2day = session2.getDay();

        return s1day.equals(s2day) && s1start.isBefore(s2end) && s1end.isAfter(s2start);
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
            for (int j = i + 1; j < meetings.size(); j++){
                if (meetingOverlap((meetings.get(i)), meetings.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

}

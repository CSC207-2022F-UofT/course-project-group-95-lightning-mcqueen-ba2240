package use_cases;

import entities.Meeting;
import entities.Session;
import entities.Timetable;
import java.time.DayOfWeek;


import java.util.HashSet;

/**
 * This class takes in a generated timetable and returns a HashSet of tags that correspond to the methods
 * described below as Strings
 */
public class Tagger {
    public static HashSet<String> main(Timetable timetable) {
        HashSet<String> tags = new HashSet<>();
        boolean has_monday = false;
        boolean has_friday = false;

        // loop through every session
        for (Meeting meeting: timetable.getMeetings()) {
            for (Session session : meeting.getSessions()) {
                // check if session is on Monday or Friday and update the loop variables
                if (session.getMeetingDay().equals(DayOfWeek.MONDAY)) {
                    has_monday = true;
                }
                if (session.getMeetingDay().equals(DayOfWeek.FRIDAY)) {
                    has_friday = true;
                }

            }
        }
        checkLongWeekend(tags, has_monday, has_friday);
        return tags;
    }

    /**
     * Add the tag "Long Weekend" into tags if the timetable has either no classes on Monday or
     * no classes on Friday (3-day weekend)
     * @param tags the tags to be associated with the timetable
     * @param has_monday boolean value whether the timetable has Monday classes
     * @param has_friday boolean value whether the timetable has Friday classes
     */
    private static void checkLongWeekend(HashSet<String> tags, boolean has_monday, boolean has_friday) {
        if (!(has_monday && has_friday)) {
            tags.add("Long Weekend");
        }
    }
}

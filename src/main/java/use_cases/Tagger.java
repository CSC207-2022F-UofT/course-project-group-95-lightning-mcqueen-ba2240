package use_cases;

import entities.Meeting;
import entities.Session;
import entities.Timetable;
import java.time.DayOfWeek;


import java.util.HashMap;
import java.util.HashSet;

import java.time.LocalTime;

/**
 * This class takes in a generated timetable and returns a HashSet of tags that correspond to the methods
 * described below as Strings
 */
public class Tagger {
    public static HashSet<String> main(Timetable timetable) {
        HashSet<String> tags = new HashSet<>();
        boolean has_monday = false;
        boolean has_friday = false;

        HashMap<String, Integer> timesOfDay = new HashMap<>();
        timesOfDay.put("Morning", 0);
        timesOfDay.put("Afternoon", 0);
        timesOfDay.put("Evening", 0);

        int numSessions = 0;

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
                numSessions++;

                timesOfDayHeavy(session, timesOfDay);
            }
        }

        // check for morning/afternoon/evening heavy
        Integer majority = numSessions / 2;
        for (String key: timesOfDay.keySet()) {
            if (timesOfDay.get(key) > majority) {
                tags.add(key + "-heavy");
            } else {
                tags.add("Balanced");
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

    public static void timesOfDayHeavy(Session session, HashMap<String, Integer> timesOfDay) {
        LocalTime start = session.getMeetingStartTime();

        LocalTime morningStart = LocalTime.of(8,0);
        LocalTime morningEnd = LocalTime.of(13,0);

        LocalTime afternoonStart = LocalTime.of(13, 0);
        LocalTime afternoonEnd = LocalTime.of(17, 0);

        LocalTime eveningStart = LocalTime.of(17, 0);
        LocalTime eveningEnd = LocalTime.of(21, 0);

        if ((start.isAfter(morningStart) || start.equals(morningStart)) && start.isBefore(morningEnd)) {
            timesOfDay.put("Morning", timesOfDay.get("Morning") + 1);
        }
        else if ((start.isAfter(afternoonStart) || start.equals(afternoonStart)) && start.isBefore(afternoonEnd)) {
            timesOfDay.put("Afternoon", timesOfDay.get("Afternoon") + 1);
        }
        else if ((start.isAfter(eveningStart) || start.equals(eveningStart))) {
            timesOfDay.put("Evening", timesOfDay.get("Evening") + 1);
        }
    }
}

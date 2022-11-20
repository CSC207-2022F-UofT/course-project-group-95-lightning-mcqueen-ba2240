package use_cases;

import entities.Meeting;
import entities.Session;
import entities.Timetable;
import java.time.DayOfWeek;
import java.lang.reflect.Array;
import java.util.*;
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

        HashMap<String, Boolean> has_days = new HashMap<>();
        has_days.put("has_monday", false);
        has_days.put("has_friday", false);

        HashMap<String, Integer> timesOfDay = new HashMap<>();
        timesOfDay.put("Morning", 0);
        timesOfDay.put("Afternoon", 0);
        timesOfDay.put("Evening", 0);

        int numSessions = 0;

        //Initial variables required for the Density Tag
        Session lastSession = null;
        List<Integer> consecutiveCount = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0));


        for (Session session : timetable.getSortedSessions()) {
            checkDensity(lastSession, session, consecutiveCount);
            addDensityTag(tags, consecutiveCount);
            lastSession = session;

            numSessions++;

            timesOfDayHeavy(session, timesOfDay);
            updateMondayFriday(session, has_days);

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

        // check for long weekend
        if (!(has_days.get("has_monday") && has_days.get("has_friday"))) {
            tags.add("Long Weekend");
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

    /**
     * Update whether the timetable has at least one Monday or Friday class
     * @param session one of the sessions of the timetable
     * @param has_days HashMap that stores whether the timetable has a Monday or Friday class
     */
    private static void updateMondayFriday(Session session, HashMap<String, Boolean> has_days) {
        if (session.getMeetingDay().equals(DayOfWeek.MONDAY)) {
            has_days.put("has_monday", true);
        }
        if (session.getMeetingDay().equals(DayOfWeek.FRIDAY)) {
            has_days.put("has_friday", true);
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
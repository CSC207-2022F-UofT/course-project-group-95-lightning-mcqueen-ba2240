package use_cases;

import entities.base.Session;
import entities.base.Timetable;

import java.time.DayOfWeek;
import java.util.*;
import java.util.HashMap;
import java.util.HashSet;

import java.time.LocalTime;

/**
 * This class takes in a generated timetable and returns a HashSet of tags that correspond to the methods
 * described below as Strings
 */
public class Tagger {
    public static Set<String> addTags(Timetable timetable) {
        Set<String> tags = new HashSet<>();
        List<Session> timetableSessions = timetable.getSortedSessions();

        Map<String, Boolean> hasDaysMap = createHasDaysMap();

        Map<String, Integer> timesOfDayMap = createTimesOfDayMap();

        // Initial variables required for the Density Tag
        Session lastSession = null;
        List<Integer> consecutiveCount = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));

        // Main loop
        for (Session session : timetableSessions) {
            checkDensity(lastSession, session, consecutiveCount);
            addDensityTag(tags, consecutiveCount);
            lastSession = session;

            timesOfDayHeavy(session, timesOfDayMap);

            updateMondayFriday(session, hasDaysMap);
        }

        addTimeOfDayTag(tags, timetableSessions, timesOfDayMap);

        addLongWeekendTag(tags, hasDaysMap);

        return tags;
    }

    /**
     * Helper function for Tagger's main method that adds the Long Weekend tag if the timetable has a 3-day weekend
     * @param tags the set of tags in which the tag will be added
     * @param hasDaysMap the mapping from Monday, Friday to boolean values if the timetable contains a meeting on the day
     */
    private static void addLongWeekendTag(Set<String> tags, Map<String, Boolean> hasDaysMap) {
        // Check for long weekend
        if (!(hasDaysMap.get("hasMonday") && hasDaysMap.get("hasFriday"))) {
            tags.add("Long Weekend");
        }
    }

    /**
     * Helper function for Tagger's main method that adds which time of day the timetable has most meetings
     * @param tags the set of tags in which the tag will be added
     * @param timetableSessions the sessions of the given timetable
     * @param timesOfDayMap mapping counting how many meetings appear in each specified time of day (Morning/Afternoon/Evening)
     */
    private static void addTimeOfDayTag(Set<String> tags, List<Session> timetableSessions, Map<String, Integer> timesOfDayMap) {
        // Check for morning/afternoon/evening heavy
        Integer majority = timetableSessions.size() / 2;
        boolean added = false;
        for (String key : timesOfDayMap.keySet()) {
            if (timesOfDayMap.get(key) > majority) {
                // if the majority of classes are in one block, then it is heavy in that block
                tags.add(key + "-heavy");
                added = true;
            }
        }
        if (!added) {
            // if no 'heavy' tags are added, then classify the timetable as balanced
            tags.add("Balanced");
        }
    }

    /**
     * Helper function for initializing a Map for the timesOfDay tag
     * @return a Map from the time of time to how many meetings in the timetable occur in the time
     */
    private static Map<String, Integer> createTimesOfDayMap() {
        // Initial variables required for the Morning/Afternoon/Evening Heavy tag
        Map<String, Integer> timesOfDayMap = new HashMap<>();
        timesOfDayMap.put("Morning", 0);
        timesOfDayMap.put("Afternoon", 0);
        timesOfDayMap.put("Evening", 0);
        return timesOfDayMap;
    }

    /**
     * Helper function for initializing a Map for the long weekend tag
     * @return a Map from the day as a key (Monday/Friday) to a boolean on whether the day occurs in the timetable
     */
    private static Map<String, Boolean> createHasDaysMap() {
        // Initial variables required for the Has Weekend tag
        Map<String, Boolean> hasDaysMap = new HashMap<>();
        hasDaysMap.put("hasMonday", false);
        hasDaysMap.put("hasFriday", false);
        return hasDaysMap;
    }

    /**
     * Return if 2 sessions are consecutive (if they have the same meeting day and the
     * start time of one is equal to the end time of another)
     *
     * @param session1 one session being compared
     * @param session2 other one session being compared
     * @return True if the two sessions are consecutive, False if not
     */
    private static Boolean consecutiveSessions(Session session1, Session session2) {
        if ((session1 != null) && (session1.getDay() == session2.getDay())) {
            return (session1.getEndTime().equals(session2.getStartTime())) ||
                    (session2.getEndTime().equals(session1.getStartTime()));
        }
        return false;
    }

    /**
     * Assign values to each element in the consecutiveCount list, where each element is the day's density of courses
     * Check if number of consecutive courses per day - if there's more than 3 days, each with 3 or more consecutive courses
     *
     * @param currentSession the current session
     * @param lastSession the session prior the current session
     * @param consecutiveCount list with each index representing a day and the value at each index representing
     *                         the number of consecutive courses within that day
     */
    private static void checkDensity(Session currentSession, Session lastSession, List<Integer> consecutiveCount) {
        if (consecutiveSessions(lastSession, currentSession)) {
            int index = currentSession.getDay().getValue();
            consecutiveCount.set(index, consecutiveCount.get(index) + 1);
        }
    }

    /**
     * Adds the Dense tag to the list of tags if a timetable contains 3 or more dense days
     * (with 3 consecutive sessions in a day)
     *
     * @param tags the set of tags for the timetable
     * @param consecutiveCount list with each index representing a day and the value at each index representing
     *                         the number of consecutive courses within that day
     */
    private static void addDensityTag(Set<String> tags, List<Integer> consecutiveCount) {
        int numberOfConsecutiveDays = 0;
        for (int count : consecutiveCount) {
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
     *
     * @param session  one of the sessions of the timetable
     * @param hasDaysMap HashMap that stores whether the timetable has a Monday or Friday class
     */
    private static void updateMondayFriday(Session session, Map<String, Boolean> hasDaysMap) {
        if (session.getDay().equals(DayOfWeek.MONDAY)) {
            hasDaysMap.put("hasMonday", true);
        }
        if (session.getDay().equals(DayOfWeek.FRIDAY)) {
            hasDaysMap.put("hasFriday", true);

        }
    }

    /**
     * Increment the counts of morning, afternoon, or evening in timesOfDayMap according to
     * when the start time of the given session is
     *
     * @param session the current session
     * @param timesOfDayMap the hashmap containing the key-value pairs of morning, afternoon, evening and their counts
     */

    private static void timesOfDayHeavy(Session session, Map<String, Integer> timesOfDayMap) {
        LocalTime start = session.getStartTime();


        LocalTime morningStart = LocalTime.of(8, 0);
        LocalTime morningEnd = LocalTime.of(13, 0);

        LocalTime afternoonStart = LocalTime.of(13, 0);
        LocalTime afternoonEnd = LocalTime.of(17, 0);

        LocalTime eveningStart = LocalTime.of(17, 0);
        LocalTime eveningEnd = LocalTime.of(21, 0);

        if ((start.isAfter(morningStart) || start.equals(morningStart)) && start.isBefore(morningEnd)) {
            timesOfDayMap.put("Morning", timesOfDayMap.get("Morning") + 1);
        } else if ((start.isAfter(afternoonStart) || start.equals(afternoonStart)) && start.isBefore(afternoonEnd)) {
            timesOfDayMap.put("Afternoon", timesOfDayMap.get("Afternoon") + 1);
        } else if ((start.isAfter(eveningStart) || start.equals(eveningStart))) {
            timesOfDayMap.put("Evening", timesOfDayMap.get("Evening") + 1);
        }
    }
}
package use_cases;

import entities.Meeting;
import entities.Session;
import entities.Timetable;

import java.util.HashMap;
import java.util.HashSet;

import java.time.LocalTime;

public class Tagger {
    public static HashSet<String> main(Timetable timetable) {
        HashSet<String> tags = new HashSet<>();

        HashMap<String, Integer> timesOfDay = new HashMap<>();
        timesOfDay.put("Morning", 0);
        timesOfDay.put("Afternoon", 0);
        timesOfDay.put("Evening", 0);

        int numSessions = 0;

        for (Meeting meeting: timetable.getMeetings()) {
            for (Session session : meeting.getSessions()) {
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

        return tags;
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

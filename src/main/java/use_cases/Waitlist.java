package use_cases;

import entities.base.Meeting;
import entities.base.Timetable;
import entities.TSort;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * This dataclass stores the waitlist information for all the meetings in a given course.
 */

public class Waitlist {

    public final List<Timetable> allSortedTimetables;

    /**
     * Construct a Waitlist, setting the timetables in ascending order.
     * @param allPossibleTimetables every possible timetable which is constructed e.g. [[Timetable 1], [Timetable 2]]
     */

    public Waitlist(List<Timetable> allPossibleTimetables) {
        this.allSortedTimetables = averageWaitlistOrder(allPossibleTimetables);
    }

    /**
     * A getter for the sorted timetables.
     * @return the sorted list of meetings in ascending order by 'waitlist score'
     */

    public List<Timetable> getSortedTimetables() {
        return allSortedTimetables;
    }

    /**
     * Returns the meeting score of a given meeting section
     * @param meeting the specific meeting section to calculate the score of
     * @return the score of the specific meeting section as a Double
     */
    private static double calculateMeetingScore(Meeting meeting) {
        double numerator = meeting.getCapacity() - meeting.getEnrollment() - meeting.getWaitlist();
        return numerator / meeting.getCapacity();
    }

    /**
     * Returns the timetable score of a given timetable
     * @param timetable the unique timetable to iterate through all the sections of
     * @return the total timetable score of all the meetings in the timetable as a Double
     */
    private static double calculateTimetableScore(List<Meeting> timetable) {
        double total = 0.0;
        for (Meeting meeting : timetable) {
            total += calculateMeetingScore(meeting);
        }
        return total;
    }

    /**
     * Returns the ordered list of timetables based off their timetable score, in ascending order
     * @param allTimetables an array of all the possible timetables, which are stored as a list of Timetable objects
     * @return the list of all the possible timetables by their timetable score, in ascending order
     */

    public static List<Timetable> averageWaitlistOrder(List<Timetable> allTimetables) {
        Map<Timetable, Double> timetableScores = new HashMap<>();

        for (Timetable timetable : allTimetables) {
            timetableScores.put(timetable, calculateTimetableScore(timetable.getMeetings()));
        }

        return TSort.sort(allTimetables, timetableScores);
    }
}

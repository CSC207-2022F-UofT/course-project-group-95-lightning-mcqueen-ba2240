package use_cases;

import entities.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Objects;

/**
 * This dataclass stores the waitlist information for all the meetings in a given course.
 */

public class Waitlist {

    public final List<List<Meeting>> allSortedTimetables;

    /**
     * Construct a Waitlist, setting the
     * @param allPossibleTimetables every possible timetable which is constructed e.g. [[Timetable 1], [Timetable 2]]
     */

    public Waitlist(List<List<Meeting>> allPossibleTimetables) {
        this.allSortedTimetables = averageWaitlistOrder(allPossibleTimetables);
    }

    /**
     * A getter for the sorted timetables.
     * @return the sorted list of meetings in ascending order by 'waitlist score'
     */

    public List<List<Meeting>> getSortedTimetables() {
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
     * Returns a timetable which has the corresponding score as the value given
     * @param timetableScores the HashMap of all the timetables corresponding with their timetable score
     * @param valueToFind the Double value to find in the HashMap given
     * @return the timetable which is a list of Meeting objects
     */
    private static List<Meeting> getMeetingList(Map<List<Meeting>, Double> timetableScores, Double valueToFind) {
        List<Meeting> listToReturn = null;

        for (Entry<List<Meeting>, Double> entry : timetableScores.entrySet()) {
            if (Objects.equals(entry.getValue(), valueToFind)) {
                listToReturn = entry.getKey();
                break;
            }
        }
        return listToReturn;
    }

    /**
     * Returns the ordered list of timetables based off their timetable score, in ascending order
     * @param allTimetables an array of all the possible timetables, which are stored as a list of Meeting objects
     * @return the list of all the possible timetables by their timetable score, in ascending order
     */
    public static List<List<Meeting>> averageWaitlistOrder(List<List<Meeting>> allTimetables) {
        ArrayList<List<Meeting>> orderedWaitlist = new ArrayList<>();
        Map<List<Meeting>, Double> timetableScores = new HashMap<>();

        for (List<Meeting> timetable : allTimetables) {
            timetableScores.put(timetable, calculateTimetableScore(timetable));
        }

        List<Double> valuesList = new ArrayList<>(timetableScores.values());

        while (orderedWaitlist.size() != allTimetables.size()) {
            double min = valuesList.get(0);
            for (int i = 1; i < valuesList.size(); i++) {
                if (valuesList.get(i) < min) {
                    min = valuesList.get(i);
                }
            }

            orderedWaitlist.add(getMeetingList(timetableScores, min));
            valuesList.remove(min);
        }

        return orderedWaitlist;
    }
}

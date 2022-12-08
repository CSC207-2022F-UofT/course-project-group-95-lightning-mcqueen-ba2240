package use_cases.waitlist;

import entities.base.Meeting;
import entities.base.Timetable;
import entities.TSort;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * This data class is responsible for handling user interaction with the Waitlist course feature.
 */

public class WaitlistInteractor implements WaitlistInputBoundary {

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

    private static List<Timetable> averageWaitlistOrder(List<Timetable> allTimetables) {
        Map<Timetable, Double> timetableScores = new HashMap<>();

        for (Timetable timetable : allTimetables) {
            timetableScores.put(timetable, calculateTimetableScore(timetable.getMeetings()));
        }

        return TSort.sort(timetableScores);
    }


    /**
     * The sort function sorts a list of timetables by their waitlist score, which is calculated with the timetable's
     * meetings it has, in ascending order
     * @param requestModel of class WaitlistRequestModel to access the getter and setter methods to obtain the
     *                     required information of the timetables to calculate their scores
     * @return the list of timetables by their timetable score, in ascending order
     */
    @Override
    public WaitlistResponseModel sort(WaitlistRequestModel requestModel) {
        return new WaitlistResponseModel(averageWaitlistOrder(requestModel.getTimetables()));
    }
}

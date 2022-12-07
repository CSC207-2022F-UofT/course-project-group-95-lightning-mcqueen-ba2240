package use_cases.RateMyProfSorter;

import entities.TSort;
import entities.base.Meeting;
import entities.base.Timetable;
import gateways.RateMyProfGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The use case interacting responsible for sorting a list of timetables based on each timetable's corresponding
 * weighted RateMyProf score
 */
public class RateMyProfInteractor implements RateMyProfInputBoundary{
    RateMyProfGatewayAccessInterface gatewayResponseModel;
    Map<String, Double> professorMapping;

    public RateMyProfInteractor() throws IOException {
        this.gatewayResponseModel = new RateMyProfGateway();
        this.professorMapping = gatewayResponseModel.getProfessorMapping();
    }

    /**
     * Takes the timetable from the RateMyProfRequestModel and returns the corresponding response model with the list
     * of timetables sorted by RateMyProf score
     * @param requestModel the requestModel passed in by the controller from the RateMyProfInputBoundary
     * @return the responseModel containing a sorted list based on RateMyProf score
     */
    @Override
    public RateMyProfResponseModel sort(RateMyProfRequestModel requestModel) {
        List<Timetable> timetableList = requestModel.getTimetableList();
        // create mapping to store RMP scores to each timetable
        Map<Timetable, Double> rmpScore = new HashMap<>();
        for (Timetable timetable : timetableList) {
            double score = calculateScore(timetable, this.professorMapping);
            rmpScore.put(timetable, score);
        }
        List<Timetable> sortedTimetableList = TSort.sort(timetableList, rmpScore); // method call to tSortable interface

        // return the response model with sortedTimetableList
        return new RateMyProfResponseModel(sortedTimetableList);
    }

    /**
     * Calculate and return the average RateMyProf score for a given timetable by taking the mean of the existing scores
     * @param timetable the timetable for which the score will be calculated
     * @param professorMapping the mapping from professors to scores generated from the RMP gateway
     * @return a double value representing the weighted score for the timetable
     */
    private double calculateScore(Timetable timetable, Map<String, Double> professorMapping) {
        // loop through each meeting in timetable and get the instructor name
        List<Meeting> meetings = timetable.getMeetings();
        double totalScore = 0;
        int instructorsTotal = 0;
        for (Meeting meeting : meetings) {
            String instructor = meeting.getInstructor();

            if (professorMapping.containsKey(instructor)) {
                totalScore += professorMapping.get(instructor);
                instructorsTotal++;
            }
        }

        // return the weighted score
        if (instructorsTotal == 0) {
            return 0;
        } else {
            return totalScore / instructorsTotal;
        }
    }
}

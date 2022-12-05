package use_cases.auto_complete;

import gateways.course_api.CourseAPI;
import gateways.course_api.StGArtSciAPI;
import presenters.ErrorWindow;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

import static presenters.ErrorWindow.callError;

/**
 * This data class is responsible for handling user interaction with the Search Feature.
 */

public class AutoCompleteInteractor implements AutoCompleteInputBoundary {

    CourseAPI api;

    public AutoCompleteInteractor() {
        this.api = new StGArtSciAPI();
    }

    /**
     * search function handles generating the suggestions to be used for auto complete, alongside error messages
     * when invalid values are filled in the year and semester fields.
     * @param requestModel of class AutoCompleteRequestModel to access its getter and setter methods required
     *                     for the parameters to the api's getNames method.
     * @return the keys from the return of the getNames method as a set to be used as auto complete suggestions.
     */
    @Override
    public AutoCompleteResponseModel search(AutoCompleteRequestModel requestModel) {

        if (!requestModel.getSemester().equals("Fall") && !requestModel.getSemester().equals("Spring")) {
            callError("Please Choose a Semester!", "A semester has not been selected.");
        } else if (Integer.parseInt(requestModel.getYear()) > LocalDate.now().getYear()) {
            callError("Year is in the future!", "Courses for that year do not exist yet.");
        } else {
            if (requestModel.getSearch().length() == 3) {
                try {
                    HashMap<String, String> courses = api.getNames(requestModel.getYear(),
                            semesterFieldSelected(requestModel), requestModel.getSearch());
                    return new AutoCompleteResponseModel(courses.keySet());
                } catch (IOException e) {
                    ErrorWindow.callError("Network Error", "Error contacting the API, please try again later.");
                }
            }
        }
        return new AutoCompleteResponseModel(new HashSet<>());
    }

    /**
     * Helper function that returns the semester as CourseAPI.Semester type based on what the user has selected for
     * the semester box, which is to be used as a parameter for the api's getNames function used in the search
     * function above.
     * @param requestModel of class AutoCompleteRequestModel to access its getter and setter methods required
     *                     to get the semester box's value.
     * @return the semester as a CourseAPI.Semester type.
     */
    CourseAPI.Semester semesterFieldSelected(AutoCompleteRequestModel requestModel) {
        if (requestModel.getSemester().equals("Fall")) {
            return StGArtSciAPI.StGArtSciSemester.FALL;
        }
        return StGArtSciAPI.StGArtSciSemester.SPRING;
    }
}

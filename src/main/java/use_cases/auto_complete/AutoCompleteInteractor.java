package use_cases.auto_complete;

import gateways.course_api.CourseAPI;
import gateways.course_api.StGArtSciAPI;
import presenters.ErrorWindow;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

import static presenters.ErrorWindow.callError;


public class AutoCompleteInteractor implements AutoCompleteInputBoundary{

    CourseAPI api;

    public AutoCompleteInteractor() {
        this.api = new StGArtSciAPI();
    }

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

    // helper function
    CourseAPI.Semester semesterFieldSelected(AutoCompleteRequestModel requestModel) {
        if (requestModel.getSemester().equals("Fall")) {
            return StGArtSciAPI.StGArtSciSemester.FALL;
        }
        //otherwise return spring (only other option)
        return StGArtSciAPI.StGArtSciSemester.SPRING;
    }
}

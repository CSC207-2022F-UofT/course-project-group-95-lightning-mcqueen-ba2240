package use_cases.timetable_generation;

import entities.base.Course;
import entities.base.Timetable;
import gateways.course_api.StGArtSciAPI;
import presenters.ErrorWindow;
import use_cases.TimetableFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimetableGenerationInteractor implements TimetableGenerationInputBoundary {

    CourseGatewayAccessInterface api = new StGArtSciAPI();

    @Override
    public TimetableGenerationResponseModel generate(TimetableGenerationRequestModel requestModel) {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (String courseString : requestModel.getCourseList()){
            try {
                courses.add(api.getCourse(courseString, false));
            } catch (IOException e) {
                ErrorWindow.callError("Invalid Course!", "The inputted course does not exist.");
            }
        }
        List<Timetable> timetables = TimetableFactory.generate(courses);

        return new TimetableGenerationResponseModel(timetables);
    }
}


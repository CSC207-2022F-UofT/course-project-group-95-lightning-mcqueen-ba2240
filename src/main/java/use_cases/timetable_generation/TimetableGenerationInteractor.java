package use_cases.timetable_generation;

import entities.Tagger;
import entities.TimetableFactory;
import entities.base.Course;
import entities.base.Timetable;
import gateways.course_api.StGArtSciAPI;
import presenters.ErrorWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TimetableGenerationInteractor implements TimetableGenerationInputBoundary {

    CourseGatewayAccessInterface api = new StGArtSciAPI();

    @Override
    public TimetableGenerationResponseModel generate(TimetableGenerationRequestModel requestModel) {
        ArrayList<Course> courses = new ArrayList<>();
        for (String courseString : requestModel.getCourseList()) {
            try {
                courses.add(api.getCourse(courseString, false));
            } catch (IOException e) {
                ErrorWindow.callError("Invalid Course!", "The inputted course does not exist.");
            }
        }
        List<Timetable> timetables = TimetableFactory.generate(courses);

        for (Timetable timetable: timetables) {
            HashSet<String> tags = Tagger.main(timetable);
            timetable.setTags(tags);
        }

        return new TimetableGenerationResponseModel(timetables);
    }
}


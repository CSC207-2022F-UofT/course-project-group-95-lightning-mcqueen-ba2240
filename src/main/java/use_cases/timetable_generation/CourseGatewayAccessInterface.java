package use_cases.timetable_generation;

import entities.base.Course;

import java.io.IOException;

public interface CourseGatewayAccessInterface {

    public Course getCourse(String key, boolean rmp) throws IOException;
}

package use_cases.auto_complete;

import gateways.course_api.CourseAPI;

import java.io.IOException;
import java.util.HashMap;

public interface CourseNameGatewayAccessInterface {
    HashMap<String, String> getNames(String year, CourseAPI.Semester semester, String org) throws IOException;
}

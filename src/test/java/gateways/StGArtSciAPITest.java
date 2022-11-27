package gateways;

import entities.Course;
import entities.Meeting;
import gateways.course_api.CourseAPI;
import gateways.course_api.StGArtSciAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

public class StGArtSciAPITest {
    @Test
    public void TestAPISimpleCSCFall() throws IOException {
        //Test if the getSimpleCourses function maps the correct CSC course codes to the correct respective
        //course titles in the Fall term.

        CourseAPI api = new StGArtSciAPI();
        HashMap<String, String> csc = api.getNames("2022", StGArtSciAPI.StGArtSciSemester.FALL, "CSC");
        Assertions.assertEquals(csc.get("CSC108H1-F-20229"), "CSC108H1: Introduction to Computer Programming");
        Assertions.assertEquals(csc.get("CSC207H1-F-20229"), "CSC207H1: Software Design");
        Assertions.assertEquals(csc.get("CSC369H1-F-20229"), "CSC369H1: Operating Systems");
    }

    @Test
    public void TestAPISimpleMATSpring() throws IOException {
        //Test if the getSimpleCourses function maps the correct MAT course codes to the correct respective
        //course titles in the Spring term.

        CourseAPI api = new StGArtSciAPI();
        HashMap<String, String> mat = api.getNames("2022", StGArtSciAPI.StGArtSciSemester.SPRING, "MAT");
        Assertions.assertEquals(mat.get("MAT223H1-S-20229"), "MAT223H1: Linear Algebra I");
        Assertions.assertEquals(mat.get("MAT301H1-S-20229"), "MAT301H1: Groups and Symmetries");
        Assertions.assertEquals(mat.get("MAT425H1-S-20229"), "MAT425H1: Differential Topology");
    }

    @Test
    public void TestAPICourseAttributes() throws IOException {
        //Test if the getCourse function correctly returns a Course with its multiple attributes intact.
        CourseAPI api = new StGArtSciAPI();
        Course csc207 = api.getCourse("CSC207H1-F-20229", false);
        Assertions.assertEquals(csc207.getId(), 61820);
        Assertions.assertEquals(csc207.getCode(), "CSC207H1");
        Assertions.assertEquals(csc207.getName(), "Software Design");

        Meeting csc207Meeting = csc207.getMeetings().get(5);

        Assertions.assertEquals(csc207Meeting.getSection(), "0101");
        Assertions.assertEquals(csc207Meeting.getType(), Meeting.Type.LEC);
        Assertions.assertEquals(csc207Meeting.getInstructor(), "P Gries");
    }

    @Test
    public void testAPIDefinitive() throws IOException {
        CourseAPI api = new StGArtSciAPI();
        HashMap<String, String> courses = api.getNames("2022", StGArtSciAPI.StGArtSciSemester.SPRING, "");

        for (String key: courses.values()){
            Assertions.assertNotNull(api.getCourse(key, false));
        }
    }
}

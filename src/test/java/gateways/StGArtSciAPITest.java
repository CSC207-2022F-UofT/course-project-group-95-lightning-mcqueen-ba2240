package gateways;

import entities.base.Course;
import entities.base.Meeting;
import entities.stgartsci.StGArtSciMeeting;
import gateways.course_api.CourseAPI;
import gateways.course_api.StGArtSciAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

class StGArtSciAPITest {
    @Test
    void TestAPISimpleCSCFall() throws IOException {
        //Test if the getSimpleCourses function maps the correct CSC course codes to the correct respective
        //course titles in the Fall term.

        CourseAPI api = new StGArtSciAPI();
        HashMap<String, String> csc = api.getNames("2022", StGArtSciAPI.StGArtSciSemester.FALL, "CSC");
        Assertions.assertEquals("CSC108H1: Introduction to Computer Programming", csc.get("CSC108H1-F-20229"));
        Assertions.assertEquals("CSC207H1: Software Design", csc.get("CSC207H1-F-20229"));
        Assertions.assertEquals("CSC369H1: Operating Systems", csc.get("CSC369H1-F-20229"));
    }

    @Test
    void TestAPISimpleMATSpring() throws IOException {
        //Test if the getSimpleCourses function maps the correct MAT course codes to the correct respective
        //course titles in the Spring term.

        CourseAPI api = new StGArtSciAPI();
        HashMap<String, String> mat = api.getNames("2022", StGArtSciAPI.StGArtSciSemester.SPRING, "MAT");
        Assertions.assertEquals("MAT223H1: Linear Algebra I", mat.get("MAT223H1-S-20229"));
        Assertions.assertEquals("MAT301H1: Groups and Symmetries", mat.get("MAT301H1-S-20229"));
        Assertions.assertEquals("MAT425H1: Differential Topology", mat.get("MAT425H1-S-20229"));
    }

    @Test
    void TestAPICourseAttributes() throws IOException {
        //Test if the getCourse function correctly returns a Course with its multiple attributes intact.
        CourseAPI api = new StGArtSciAPI();
        Course csc207 = api.getCourse("CSC207H1-F-20229", false);
        Assertions.assertEquals(61820, csc207.getId());
        Assertions.assertEquals("CSC207H1", csc207.getCode());
        Assertions.assertEquals("Software Design", csc207.getName());

        Meeting csc207Meeting = csc207.getMeetings().get(5);

        Assertions.assertEquals("CSC207H1 LEC0101", csc207Meeting.getSection());
        Assertions.assertEquals(StGArtSciMeeting.StGArtSciType.LEC, csc207Meeting.getType());
        Assertions.assertEquals("P Gries", csc207Meeting.getInstructor());
    }

    @Test
    void testAPIDefinitive() throws IOException {
        CourseAPI api = new StGArtSciAPI();
        HashMap<String, String> courses = api.getNames("2022", StGArtSciAPI.StGArtSciSemester.SPRING, "");

        for (String key: courses.values()){
            Assertions.assertNotNull(api.getCourse(key, false));
        }
    }
}

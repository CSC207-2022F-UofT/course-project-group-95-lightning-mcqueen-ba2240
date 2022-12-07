package use_cases;

import entities.base.Course;
import entities.base.Meeting;
import entities.base.Session;
import entities.stgartsci.StGArtSciMeeting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_cases.course_builder.StGArtSciCourseBuilder;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class StGArtSciCourseBuilderTest {
    @Test
    public void TestCourseBuilderVsCourse() {
        //Test if the classes Course and CourseBuilder produce the same result given the same specifications
        Session session = new Session(DayOfWeek.MONDAY, LocalTime.of(0, 0), LocalTime.of(1,0));
        Meeting meeting = new StGArtSciMeeting("MAR101 LEC0101", StGArtSciMeeting.StGArtSciType.LEC, "M Gillani", 10, 7, 1, session);
        Course actualCourse = new Course(123456, "Maroosh Test", "MAR101", meeting);

        StGArtSciCourseBuilder expectedCourse = new StGArtSciCourseBuilder();
        expectedCourse.newCourse("123456", "Maroosh Test", "MAR101");

        expectedCourse.newMeeting("0101", "LEC", "M Gillani", 10, 7, 1);
        expectedCourse.newSession("MO", "00:00", "01:00", false);

        expectedCourse.pushSession();
        expectedCourse.pushMeeting();

        Assertions.assertEquals(actualCourse, expectedCourse.build());
    }
}

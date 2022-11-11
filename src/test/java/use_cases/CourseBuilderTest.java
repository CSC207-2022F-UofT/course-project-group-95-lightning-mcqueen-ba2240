package use_cases;

import entities.Course;
import entities.Meeting;
import entities.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class CourseBuilderTest {
    @Test
    public void TestCourseBuilderVsCourse() {
        //Test if the classes Course and CourseBuilder produce the same result given the same specifications
        Session session = new Session(DayOfWeek.MONDAY, LocalTime.of(0, 0), LocalTime.of(1,0), "MY777");
        Meeting meeting = new Meeting("0101", Meeting.Type.LEC, "M Gillani", 10, 7, 1, session);
        Course actualCourse = new Course(123456, "Maroosh Test", "MAR101", meeting);

        CourseBuilder expectedCourse = new CourseBuilder();
        expectedCourse.newCourse("123456", "Maroosh Test", "MAR101");

        expectedCourse.newMeeting("0101", "LEC", "M Gillani", 10, 7, 1);
        expectedCourse.newSession("MO", "00:00", "01:00", "MY777", false);

        expectedCourse.pushSession();
        expectedCourse.pushMeeting();

        Assertions.assertEquals(actualCourse, expectedCourse.build());
    }
}

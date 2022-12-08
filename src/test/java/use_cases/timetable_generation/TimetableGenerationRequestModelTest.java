package use_cases.timetable_generation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableGenerationRequestModelTest {

    /**
     * Test that getCourseList() returns the proper list of courses
     */
    @Test
    void testGetCourseList() {
        List<String> course_list = Arrays.asList("CSC207H1-F-20229", "MAT235H1-Y-20229", "CSC258H1-F-20229", "CSC236H1-F-20229", "STA247H1-F-20229");
        TimetableGenerationRequestModel request = new TimetableGenerationRequestModel(course_list);

        assertEquals(course_list, request.getCourseList());
    }
}
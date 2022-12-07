package use_cases;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import use_cases.auto_complete.AutoCompleteInteractor;
import use_cases.auto_complete.AutoCompleteRequestModel;
import use_cases.auto_complete.AutoCompleteResponseModel;

import java.time.LocalDate;
import java.util.Set;

public class AutoCompleteInteractorTest {
    private final AutoCompleteInteractor autoComplete = new AutoCompleteInteractor();

    /**
     * Test that when given a search whose semester attribute is neither "Spring" nor "Fall", then the returned
     * AutoCompleteResponseModel has an empty set of courses.
     */
    @Test
    public void testInvalidSemester() {
        AutoCompleteRequestModel request = new AutoCompleteRequestModel("CSC", "2007",
                "NotSpring");
        AutoCompleteResponseModel response = autoComplete.search(request);
        assertTrue(response.getCourses().isEmpty());
    }

    /**
     * Test that when given a search whose year attribute is in the future, then the returned
     * AutoCompleteResponseModel has an empty set of courses.
     */
    @Test
    public void testInvalidYear() {
        AutoCompleteRequestModel request = new AutoCompleteRequestModel("CSC", "2024",
                "Spring");
        AutoCompleteResponseModel response = autoComplete.search(request);
        assertTrue(response.getCourses().isEmpty());
    }

    /**
     * Test that a proper search will only return courses of the desired organization
     * (first 3 letters in course_code; e.g. "CSC")
     */
    @Test
    public void testProperOrganization() {
        String current_year = String.valueOf(LocalDate.now().getYear());

        AutoCompleteRequestModel request = new AutoCompleteRequestModel("CSC", current_year, "Spring");
        AutoCompleteResponseModel response = autoComplete.search(request);

        Set<String> courses = response.getCourses();

        for (String course_code: courses) {
            assertEquals("CSC", course_code.substring(0, 3));
        }
    }

    /**
     * Test that a proper search will only return courses of the desired semester or year-long courses
     */
    @Test
    public void testProperSemester() {
        String current_year = String.valueOf(LocalDate.now().getYear());

        AutoCompleteRequestModel request = new AutoCompleteRequestModel("MAT", current_year, "Fall");
        AutoCompleteResponseModel response = autoComplete.search(request);

        Set<String> courses = response.getCourses();

        for (String course_code: courses) {
            assertTrue(course_code.charAt(9) == 'F' || course_code.charAt(9) == 'Y');
        }
    }

    /**
     * Test that a proper search will only return courses within the desired year
     */
    @Test
    public void testProperYear() {
        String current_year = String.valueOf(LocalDate.now().getYear());

        AutoCompleteRequestModel request = new AutoCompleteRequestModel("CSC", current_year, "Spring");
        AutoCompleteResponseModel response = autoComplete.search(request);

        Set<String> courses = response.getCourses();

        for (String course_code: courses) {
            assertEquals(current_year, course_code.substring(11, 15));
        }
    }
}
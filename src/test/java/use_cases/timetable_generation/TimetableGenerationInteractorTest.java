package use_cases.timetable_generation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableGenerationInteractorTest {
    private final TimetableGenerationInteractor generator = new TimetableGenerationInteractor();

    @Test
    void testGenerate() {
        List<String> course_list = Arrays.asList("CSC207H1-F-20229", "MAT235Y1-Y-20229", "CSC258H1-F-20229", "CSC236H1-F-20229", "STA247H1-F-20229");
        TimetableGenerationRequestModel request = new TimetableGenerationRequestModel(course_list);

        TimetableGenerationResponseModel response = generator.generate(request);

        assertEquals(75000, response.getTimetableList().size());
    }
}
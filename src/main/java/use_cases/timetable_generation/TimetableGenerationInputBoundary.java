package use_cases.timetable_generation;

/**
 * An interface implemented by TimetableGeneratorInteractor.
 */

public interface TimetableGenerationInputBoundary {
    TimetableGenerationResponseModel generate(TimetableGenerationRequestModel requestModel);
}

package use_cases.auto_complete;

/**
 * An interface implemented by AutoCompleteInteractor.
 */
public interface AutoCompleteInputBoundary {
    AutoCompleteResponseModel search(AutoCompleteRequestModel requestModel);
}

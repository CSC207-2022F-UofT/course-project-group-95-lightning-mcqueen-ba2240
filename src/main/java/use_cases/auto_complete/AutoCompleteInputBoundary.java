package use_cases.auto_complete;

public interface AutoCompleteInputBoundary {
    AutoCompleteResponseModel search(AutoCompleteRequestModel requestModel);
}

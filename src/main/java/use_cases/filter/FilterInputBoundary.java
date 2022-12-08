package use_cases.filter;

/**
 * An interface implemented by FilterInteractor.
 */
public interface FilterInputBoundary {
    FilterResponseModel filter(FilterRequestModel request);
}

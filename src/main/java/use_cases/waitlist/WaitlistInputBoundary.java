package use_cases.waitlist;


/**
 * An interface implemented by AutoCompleteInteractor.
 */
public interface WaitlistInputBoundary {
    WaitlistResponseModel sort(WaitlistRequestModel requestModel);
}

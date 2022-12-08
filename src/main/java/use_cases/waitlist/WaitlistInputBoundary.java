package use_cases.waitlist;


/**
 * An interface implemented by WaitlistInteractor.
 */
public interface WaitlistInputBoundary {
    WaitlistResponseModel sort(WaitlistRequestModel requestModel);
}

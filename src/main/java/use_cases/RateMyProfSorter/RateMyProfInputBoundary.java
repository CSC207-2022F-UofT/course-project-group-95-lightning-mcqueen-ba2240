package use_cases.RateMyProfSorter;

/**
 * An interface implemented by RateMyProfInteractor.
 */
public interface RateMyProfInputBoundary {
    RateMyProfResponseModel sort(RateMyProfRequestModel requestModel);
}

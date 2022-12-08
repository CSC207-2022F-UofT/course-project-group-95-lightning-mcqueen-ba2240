package use_cases.rate_my_prof_sorter;

/**
 * An interface implemented by RateMyProfInteractor.
 */
public interface RateMyProfInputBoundary {
    RateMyProfResponseModel sort(RateMyProfRequestModel requestModel);
}

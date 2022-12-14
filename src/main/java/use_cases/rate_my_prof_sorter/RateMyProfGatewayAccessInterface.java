package use_cases.rate_my_prof_sorter;

import java.util.Map;

public interface RateMyProfGatewayAccessInterface {
    /**
     * Uses the RateMyProf gateway to get a mapping from all professors to their RateMyProf score, provided the score exists
     * @return a mapping from professor's to their RateMyProf score
     */
    Map<String, Double> getProfessorMapping();
}

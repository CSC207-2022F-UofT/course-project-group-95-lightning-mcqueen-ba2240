package use_cases.RateMyProfSorter;

import entities.base.Timetable;
import gateways.RateMyProfGateway;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RateMyProfInteractor implements RateMyProfInputBoundary{
    RateMyProfGatewayAccessInterface gatewayResponseModel;
    Map<String, Double> professorMapping;

    public RateMyProfInteractor() throws IOException {
        this.gatewayResponseModel = new RateMyProfGateway();
        this.professorMapping = gatewayResponseModel.getProfessorMapping();;
    }

    @Override
    public RateMyProfResponseModel sort(RateMyProfRequestModel model) {
        List<Timetable> timetableList = model.getTimetableList();
        // TODO: map each timetable to its RMP score



        // TODO: return response model with the sorted list
        return null;
    }


}

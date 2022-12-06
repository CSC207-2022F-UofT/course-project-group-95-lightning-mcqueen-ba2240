package use_cases.filter;

import entities.base.Timetable;
import entities.Filterer;

import java.util.List;
import java.util.Set;

public class FilterInteractor implements FilterInputBoundary{
    @Override
    public FilterResponseModel filter(FilterRequestModel request) {
        String[] tags = request.getFilter().replace(" ", "").split(",");
        List<Timetable> timetables = Filterer.filterAll(request.getTimetables(), Set.of(tags));
        return new FilterResponseModel(timetables);
    }
}

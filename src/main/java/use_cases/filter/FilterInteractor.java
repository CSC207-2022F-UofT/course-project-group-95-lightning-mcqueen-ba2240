package use_cases.filter;

import entities.Filterer;
import entities.base.Timetable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterInteractor implements FilterInputBoundary{
    @Override
    public FilterResponseModel filter(FilterRequestModel request) {
        Set<String> tags = new HashSet<>();
        for (String s: request.getFilter().split(",")){
            tags.add(s.strip());
        }

        List<Timetable> timetables = Filterer.filterAll(request.getTimetables(), tags);
        return new FilterResponseModel(timetables);
    }
}

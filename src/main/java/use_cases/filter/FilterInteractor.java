package use_cases.filter;

import entities.Filterer;
import entities.base.Timetable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This data class is responsible for handling user interaction with the Filter feature.
 */
public class FilterInteractor implements FilterInputBoundary{
    /**
     * The filter function splits tags by commas and removes white spaces, then filters timetables
     * containing the tags (overrides filter function from the interactor)
     * @param request of class FilterRequestModel to access the getter and setter methods to obtain the
     *                     required information of the timetables to filter them as required
     * @return the list of timetables filtered by their tags
     */
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

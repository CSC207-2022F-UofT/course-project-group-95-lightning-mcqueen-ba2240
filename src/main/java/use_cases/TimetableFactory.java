package use_cases;

import entities.base.Collapsible;
import entities.base.Course;
import entities.base.Meeting;
import entities.base.Timetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TimetableFactory {
    /**
     * Tutorials and Practicals that occur at the same
     * time can be taken as a single session to reduce the
     * number of Timetables generated
     * @param meetings is the List of Meetings that needs to be added to
     * @param meeting the Meeting to be added to the List
     */
    private static  void collapseToList(List<Meeting> meetings, Meeting meeting){
        boolean contains = false;
        for (Meeting m: meetings){
            if (m instanceof Collapsible && meeting instanceof Collapsible){
                if (((Collapsible) m).isCollapsible(meeting)){
                    contains = true;
                    ((Collapsible) m).collapse(meeting);
                }
            }
        }
        // If the meeting does not share a time signature
        if (!contains){
            meetings.add(meeting);
        }
    }

    /**
     * Sort the Meetings in a Course by Type and collapse into their required Lists
     * @param course The course to be collapsed into a list
     * @return Sorted List of Lists of Meetings by Meeting Type
     */
    private static List<List<Meeting>> sortByType(Course course) {
        List<List<Meeting>> sorted = new ArrayList<>();

        for (Meeting meeting: course.getMeetings()){
            // Check if sorted list exists for Meeting Type
            boolean exists = false;
            for (List<Meeting> list: sorted){
                if (list.get(0).getType().equals(meeting.getType())){
                    exists = true;
                    collapseToList(list, meeting);
                    break;
                }
            }

            if (!exists){
                List<Meeting> list = new ArrayList<>();
                collapseToList(list, meeting);
                sorted.add(list);
            }
        }

        return sorted;
    }

    /***
     * A recursive function that generates all valid combinations from a list of lists of Meetings
     * @param combinations The array to add all the combinations to
     * @param all List of lists from which a meeting is chosen
     * @param depth The list that is currently being sampled from
     * @return A List of Lists of Meetings that have all valid combinations of given Meetings
     */
    private static List<List<Meeting>> generateCombinations(List<List<Meeting>> combinations,
                                                            List<List<Meeting>> all, int depth) {
        if (depth == 0) {
            // The list is empty so cannot recursively add to combinations
            // Create a base set of combinations to recurse upon
            for (Meeting meeting: all.get(0)){
                List<Meeting> newList = new ArrayList<>();
                newList.add(meeting);
                combinations.add(newList);
            }
            if (depth + 1 != all.size()){
                return generateCombinations(combinations, all, depth + 1);
            }
            // Else return the final generated combination
            return combinations;
        } else {
            // For each meeting at depth, add all the meeting
            // to all existing combination if the new timetable is valid
            List<List<Meeting>> newCombinations = new ArrayList<>();
            for (List<Meeting> list: combinations){
                for (Meeting meeting: all.get(depth)) {
                    List<Meeting> newList = new ArrayList<>(list);
                    newList.add(meeting);
                    if (TimetableValidator.validateMeetings(newList)){
                        newCombinations.add(newList);
                    }
                }
            }
            // Recurse further is another list exists
            if (depth + 1 != all.size()){
                return generateCombinations(newCombinations, all, depth + 1);
            }
            // Else return the final generated combination
            return newCombinations;
        }
    }

    /**
     * Generate all possible valid meetings and tag them
     *
     * @param courses Courses being taken to generate from
     * @return A List of Lists of Meetings that have all valid combinations of given Meetings
     */
    public static List<Timetable> generate(List<Course> courses){
        List<List<Meeting>> all = new ArrayList<>();
        for (Course course:courses) {
            all.addAll(sortByType(course));
        }
        List<List<Meeting>> combinations = generateCombinations(new ArrayList<>(), all, 0);
        List<Timetable> timetables = new ArrayList<>();
        for (List<Meeting> combination: combinations){
            timetables.add(new Timetable(combination, new HashSet<>()));
        }
        return timetables;
    }
}

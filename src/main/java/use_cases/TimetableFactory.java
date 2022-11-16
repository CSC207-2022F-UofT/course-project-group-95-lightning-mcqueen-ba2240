package use_cases;

import entities.Course;
import entities.Meeting;
import entities.Timetable;
import gateways.API;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
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
            // If both meetings have an identical time signature they
            // can be considered as the same meeting so long as it is not a LEC
            if (m.timeHash().equals(meeting.timeHash())){
                contains = true;
                break;
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
    private static List<List<Meeting>> sortMeetings(Course course) {
        List<Meeting> lectures = new ArrayList<>();
        List<Meeting> tutorials = new ArrayList<>();
        List<Meeting> practicals = new ArrayList<>();

        for (Meeting meeting: course.getMeetings()){
            switch (meeting.getType()){
                case LEC:
                    lectures.add(meeting);
                    break;
                case PRA:
                    collapseToList(practicals, meeting);
                    break;
                case TUT:
                    collapseToList(tutorials, meeting);
                    break;
            }
        }

        List<List<Meeting>> sorted = new ArrayList<>();
        if (!lectures.isEmpty()){
            sorted.add(lectures);
        } if (!tutorials.isEmpty()){
            sorted.add(tutorials);
        } if (!practicals.isEmpty()){
            sorted.add(practicals);
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
            all.addAll(sortMeetings(course));
        }
        List<List<Meeting>> combinations = generateCombinations(new ArrayList<>(), all, 0);
        List<Timetable> timetables = new ArrayList<>();
        for (List<Meeting> combination: combinations){
            timetables.add(new Timetable(combination, new HashSet<>()));
        }
        return timetables;
    }

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        API api = new API();
        Course one = api.getCourse("CSC236H1-F-20229", false);
        Course two = api.getCourse("CSC207H1-F-20229", false);
        Course three = api.getCourse("STA247H1-F-20229", false);
        Course four = api.getCourse("GGR101H1-F-20229", false);
        Course five = api.getCourse("MAT235Y1-Y-20229", false);

        List<Course> courses = new ArrayList<>();
        courses.add(one);
        courses.add(two);
        courses.add(three);
        courses.add(four);
        courses.add(five);

        List<Timetable> allMeetings = generate(courses);
        System.out.println(allMeetings.size());
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }
}

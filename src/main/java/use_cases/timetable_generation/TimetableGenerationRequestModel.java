package use_cases.timetable_generation;
import java.util.List;

/**
 * This data class is responsible for handling the required information needed for the auto complete feature
 * to perform its required tasks.
 */

public class TimetableGenerationRequestModel {
    private List<String> courseList;

    /**
     * Constructor function for TimetableGenerator Request Model
     * @param courseList is the list of courses that will be featured in the timetables.
     */
    public TimetableGenerationRequestModel(List<String> courseList) {
        this.courseList = courseList;
    }

    /**
     * Getter method for courseList
     * @return returns  the current list of courses.
     */
    public List<String> getCourseList(){
        return courseList;
    }

    /**
     * Setter method for courseList
     * @param courseList is the list of courses selected by the user at the given point of time.
     */
    public void setCourseList(List<String> courseList){
        this.courseList = courseList;
    }
}


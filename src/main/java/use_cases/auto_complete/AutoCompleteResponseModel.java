package use_cases.auto_complete;

import java.util.Set;

/**
 * This data class is responsible for handling how the auto complete feature responds when utilized.
 */

public class AutoCompleteResponseModel {
    Set<String> courses;

    /**
     * A constructor for AutoCompleteResponseModel.
     * @param courses the set of courses displayed as suggestions for auto complete.
     */
    public AutoCompleteResponseModel(Set<String> courses) {
        this.courses = courses;
    }

    /**
     * A getter for the set of courses.
     * @return return a set of courses to be displayed as suggestions for auto complete.
     */
    public Set<String> getCourses() {
        return courses;
    }

    /**
     * A setter for the set of courses.
     * @param courses the set of courses to be set as suggestions for the auto complete.
     */
    public void setCourses(Set<String> courses) {
        this.courses = courses;
    }
}

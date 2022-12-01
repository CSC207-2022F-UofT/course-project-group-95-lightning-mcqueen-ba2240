package use_cases.auto_complete;

import java.util.Set;

public class AutoCompleteResponseModel {
    Set<String> courses;

    public AutoCompleteResponseModel(Set<String> courses) {
        this.courses = courses;
    }

    public Set<String> getCourses() {
        return courses;
    }

    public void setCourses(Set<String> courses) {
        this.courses = courses;
    }
}

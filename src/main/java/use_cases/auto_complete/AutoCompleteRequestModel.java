package use_cases.auto_complete;

import javafx.scene.control.TextField;

public class AutoCompleteRequestModel {
    private String search;
    private String year;
    private String semester;

    public AutoCompleteRequestModel(String search, String year, String semester) {
        this.search = search;
        this.year = year;
        this.semester = semester;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}

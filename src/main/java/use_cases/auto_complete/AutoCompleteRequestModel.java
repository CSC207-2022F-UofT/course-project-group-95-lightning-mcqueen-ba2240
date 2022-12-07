package use_cases.auto_complete;

/**
 * This data class is responsible for handling the required information needed for the auto complete feature
 * to perform its required tasks.
 */

public class AutoCompleteRequestModel {
    private String search;
    private String year;
    private String semester;

    /**
     * Constructor for AutoCompleteRequest Model
     * @param search the text inside the search field.
     * @param year the year inside the year field.
     * @param semester the semester inside the semester field.
     */
    public AutoCompleteRequestModel(String search, String year, String semester) {
        this.search = search;
        this.year = year;
        this.semester = semester;
    }

    /**
     * Getter for search
     * @return return the input to the search field as a String.
     */
    public String getSearch() {
        return search;
    }

    /**
     * A setter for the search bar.
     * @param search the text to be set in the search bar.
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * Getter for the year
     * @return return the input to the year field as a String.
     */
    public String getYear() {
        return year;
    }

    /**
     * A setter for the year bar.
     * @param year the text to be set in the year bar.
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Getter for semester
     * @return return the input to the semester field as a String.
     */
    public String getSemester() {
        return semester;
    }

    /**
     * A setter for the semester bar.
     * @param semester the text to be set in the semester bar.
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }
}

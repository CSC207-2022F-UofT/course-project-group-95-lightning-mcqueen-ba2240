package controllers;

// UI Components
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

// Presenter
import presenters.ErrorWindow;

// Use Case
import use_cases.rate_my_prof_sorter.RateMyProfInputBoundary;
import use_cases.rate_my_prof_sorter.RateMyProfInteractor;
import use_cases.rate_my_prof_sorter.RateMyProfRequestModel;
import use_cases.rate_my_prof_sorter.RateMyProfResponseModel;
import use_cases.auto_complete.AutoCompleteInputBoundary;
import use_cases.auto_complete.AutoCompleteInteractor;
import use_cases.auto_complete.AutoCompleteRequestModel;
import use_cases.auto_complete.AutoCompleteResponseModel;
import use_cases.filter.FilterInputBoundary;
import use_cases.filter.FilterInteractor;
import use_cases.filter.FilterRequestModel;
import use_cases.filter.FilterResponseModel;
import use_cases.persistence.PersistenceDataModel;
import use_cases.persistence.PersistenceInputBoundary;
import use_cases.persistence.PersistenceInteractor;
import use_cases.timetable_generation.TimetableGenerationInputBoundary;
import use_cases.timetable_generation.TimetableGenerationInteractor;
import use_cases.timetable_generation.TimetableGenerationRequestModel;
import use_cases.timetable_generation.TimetableGenerationResponseModel;
import use_cases.timetable_view.TimetableViewInputBoundary;
import use_cases.timetable_view.TimetableViewInteractor;
import use_cases.timetable_view.TimetableViewRequestModel;
import use_cases.timetable_view.TimetableViewResponseModel;
import use_cases.waitlist.WaitlistInputBoundary;
import use_cases.waitlist.WaitlistInteractor;
import use_cases.waitlist.WaitlistRequestModel;
import use_cases.waitlist.WaitlistResponseModel;

// Util
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the main view
 */

public class AppController implements Initializable {

    @FXML
    private Button generateButton;

    @FXML
    private Button rmpButton;

    @FXML
    private TextField searchField;

    @FXML
    private TextField selectedTimetableField;

    @FXML
    private TextField tagField;

    @FXML
    private Text courseListLabel;

    @FXML
    private Text timetableCountLabel;

    @FXML
    private Text tagsLabel;

    @FXML
    private ComboBox<String> semesterField;

    @FXML
    private TextField yearField;

    @FXML
    private HBox timetableBox;

    @FXML
    private ProgressIndicator spinner;

    private final AutoCompleteInputBoundary autoCompleteInputBoundary = new AutoCompleteInteractor();
    private final TimetableGenerationInputBoundary timetableGenerationInputBoundary = new
            TimetableGenerationInteractor();

    private RateMyProfInputBoundary rmp;

    private final ArrayList<String> courseList = new ArrayList<>();
    private TimetableGenerationResponseModel timetableList = new TimetableGenerationResponseModel(null);
    private FilterResponseModel filteredTimetableList = new FilterResponseModel(null);
    private int currentTimetableIndex = 0;

    /**
     * Add course into courseList and input the new addition to be updated in the UI.
     */
    @FXML
    void addCourse() {
        String course = searchField.getText();
        courseList.add(course);
        updateCourseListLabel();
        searchField.setText("");
    }

    /**
     * Delete courses from courseList
     */
    @FXML
    void deleteCourse() {
        if (!courseList.isEmpty()) {
            courseList.remove(courseList.size() - 1);
            updateCourseListLabel();
        }
        else  {
            ErrorWindow.callError("Insufficient number of courses.", "No courses have been added.");
        }
    }

    /**
     * Generate timetable from selected courses
     */
    @FXML
    void generateCourses() {
        generateButton.setDisable(true);

        TimetableGenerationRequestModel request = new TimetableGenerationRequestModel(courseList);
        timetableList = timetableGenerationInputBoundary.generate(request);
        filteredTimetableList = new FilterResponseModel(timetableList.getTimetableList());

        viewTimetable();
        generateButton.setDisable(false);
    }

    /**
     * Next timetable button
     */
    @FXML
    void nextTimetableAction() {
        if (filteredTimetableList != null){
            if (currentTimetableIndex + 1 < filteredTimetableList.getTimetables().size()){
                currentTimetableIndex++;
                viewTimetable();
            }else {
                ErrorWindow.callError("Last Timetable", "This is the last timetable!");
            }
        }
    }

    /**
     * Previous timetable button
     */
    @FXML
    void previousTimetableAction() {
        if (currentTimetableIndex - 1 >= 0){
            currentTimetableIndex--;
            viewTimetable();
        } else {
            ErrorWindow.callError("First Timetable", "This is the first timetable!");
        }
    }

    /**
     * Filter timetables by tags in tagsField
     */
    @FXML
    void filterAction() {
        FilterInputBoundary filterInputBoundary = new FilterInteractor();
        FilterRequestModel request = new FilterRequestModel(tagField.getText(), timetableList.getTimetableList());
        filteredTimetableList = filterInputBoundary.filter(request);
        if (!filteredTimetableList.getTimetables().isEmpty()){
            currentTimetableIndex = 0;
            viewTimetable();
        }else {
            ErrorWindow.callError("No Matching Timetables", "There are no timetables that fit " +
                    "your requirements, try using different tags");
        }
    }

    /**
     * Clear Filter TextField and reset Filter list
     */
    @FXML
    void clearFilterAction() {
        tagField.setText("");
        filteredTimetableList.setTimetables(timetableList.getTimetableList());
        currentTimetableIndex = 0;
        viewTimetable();
    }

    /**
     * SearchFieldTyped helps call the required functions to populate the search bar with appropriate suggestions
     * for auto complete to work properly, while also setting when the searchField can or cannot be edited.
     */
    @FXML
    void searchFieldTyped() {
        new Thread(() -> {
            spinner.setVisible(true);
            // Takes first 3 words from user input and uses that as org for the getSimpleCourses function.
            // Gets the keys from getSimpleCourses output and uses them as autocomplete suggestions.
            searchField.setEditable(false);
            AutoCompleteRequestModel request = new AutoCompleteRequestModel(searchField.getText(), yearField.getText(),
                    semesterField.getValue());
            AutoCompleteResponseModel response = autoCompleteInputBoundary.search(request);
            TextFields.bindAutoCompletion(searchField, response.getCourses());
            searchField.setEditable(true);
            spinner.setVisible(false);
        }).start();
    }

    /**
     * Save Timetables from data.json
     */
    @FXML
    void saveTimetables() {
        PersistenceInputBoundary persistenceInputBoundary = new PersistenceInteractor();
        persistenceInputBoundary.save(new PersistenceDataModel(timetableList.getTimetableList()));
    }

    /**
     * Load Timetables from data.json
     */
    @FXML
    void loadTimetables() {
        PersistenceInputBoundary persistenceInputBoundary = new PersistenceInteractor();
        PersistenceDataModel data = persistenceInputBoundary.load();

        timetableList.setTimetableList(data.getTimetables());
        filteredTimetableList.setTimetables(timetableList.getTimetableList());

        viewTimetable();
    }

    /**
     * Sort by RateMyProf button
     */
    @FXML
    void sortRMPAction() {
        RateMyProfRequestModel request = new RateMyProfRequestModel(filteredTimetableList.getTimetables());
        RateMyProfResponseModel response = rmp.sort(request);
        filteredTimetableList.setTimetables(response.getTimetableList());
        currentTimetableIndex = 0;
        viewTimetable();
    }

    /**
     * Sort by Waitlist button
     */
    @FXML
    void sortWaitlistAction() {
        WaitlistInputBoundary waitlist = new WaitlistInteractor();
        WaitlistRequestModel request = new WaitlistRequestModel(filteredTimetableList.getTimetables());
        WaitlistResponseModel response = waitlist.sort(request);
        filteredTimetableList.setTimetables(response.getSortedTimetables());
        currentTimetableIndex = 0;
        viewTimetable();
    }

    /**
     * Update the TimetableHbox and other related components on change in currentTimetableIndex
     */
    void viewTimetable() {
        TimetableViewInputBoundary timetableViewInputBoundary = new TimetableViewInteractor();
        TimetableViewRequestModel request = new TimetableViewRequestModel(filteredTimetableList.getTimetables(),
                currentTimetableIndex);
        TimetableViewResponseModel response = timetableViewInputBoundary.getView(request);

        ObservableList<Node> children = timetableBox.getChildren();
        children.clear();
        children.add(response.getNode());
        HBox.setHgrow(response.getNode(), Priority.ALWAYS);

        tagsLabel.setText("Tags: " + response.getTags());
        timetableCountLabel.setText("of " + filteredTimetableList.getTimetables().size());
        selectedTimetableField.setText(String.valueOf(currentTimetableIndex + 1));
    }

    /**
     * Displays the user's selected courses in a label
     */
    void updateCourseListLabel() {
        String courses = String.join(", ", courseList);
        courseListLabel.setText("Added Courses: " + courses);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String currentYear = String.valueOf(LocalDate.now().getYear());
        semesterField.getItems().add("Fall");
        semesterField.getItems().add("Spring");
        semesterField.setValue("Fall");
        yearField.setText(currentYear);

        // Disable button until RMP Interactor Works
        new Thread(() -> {
            try {
                rmpButton.setDisable(true);
                rmp = new RateMyProfInteractor();
                rmpButton.setDisable(false);
            } catch (IOException e) {
                ErrorWindow.callError("Failed to load RateMyProfessor", "Failed to connect with the RateMyProfessor API!");
            }
        }).start();
    }
}
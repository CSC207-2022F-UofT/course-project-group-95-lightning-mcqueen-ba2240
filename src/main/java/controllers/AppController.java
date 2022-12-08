package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
import use_cases.persistance.PersistenceDataModel;
import use_cases.persistance.PersistenceInputBoundary;
import use_cases.persistance.PersistenceInteractor;
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
    private Text courseListLabel;

    @FXML
    private ComboBox<String> semesterField;

    @FXML
    private TextField yearField;

    private final AutoCompleteInteractor autoCompleteInteractor = new AutoCompleteInteractor();

    private final ArrayList<String> courseList = new ArrayList<>();

    private final AutoCompleteInputBoundary autoCompleteInputBoundary = new AutoCompleteInteractor();
    private final TimetableGenerationInputBoundary timetableGenerationInputBoundary = new
            TimetableGenerationInteractor();

    private RateMyProfInputBoundary rmp;


    // add course into courseList and input the new addition to be updated in the UI.
    @FXML
    void addCourse(ActionEvent event) {
        String course = searchField.getText();
        courseList.add(course);
        updateCourseListLabel();
        searchField.setText("");
    }

    @FXML
    void deleteCourse(ActionEvent event) {
        if (!courseList.isEmpty()) {
            courseList.remove(courseList.size() - 1);
            updateCourseListLabel();
        }
        else  {
            ErrorWindow.callError("Insufficient number of courses.", "No courses have been added.");
        }
    }

    @FXML
    void generateCourses() {
        generateButton.setDisable(true);

        TimetableGenerationRequestModel request = new TimetableGenerationRequestModel(courseList);
        timetableList = timetableGenerationInputBoundary.generate(request);
        filteredTimetableList = new FilterResponseModel(timetableList.getTimetableList());
        timetableCountLabel.setText("of " + timetableList.getTimetableList().size());

        viewTimetable();
        generateButton.setDisable(false);
    }

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


    // displays the user's selected courses in a label
    void updateCourseListLabel() {
        StringBuilder courseField = new StringBuilder("Added Courses:");
        for (String course: courseList) {
            courseField.append(" ").append(course).append(",");
        }
        courseListLabel.setText(courseField.toString());
    }

    @FXML
    void generateCourses(ActionEvent event) {
    }

    /**
     * SearchFieldTyped helps call the required functions to populate the search bar with appropriate suggestions
     * for auto complete to work properly, while also setting when the searchField can or cannot be edited.
     * @param event parameter exists as a requirement for this function to be created but is not utilized.
     */
    @FXML
    void searchFieldTyped(KeyEvent event) {
        new Thread(() -> {
            // Takes first 3 words from user input and uses that as org for the getSimpleCourses function.
            // Gets the keys from getSimpleCourses output and uses them as autocomplete suggestions.
            searchField.setEditable(false);
            AutoCompleteRequestModel request = new AutoCompleteRequestModel(searchField.getText(), yearField.getText(),
                    semesterField.getValue());
            AutoCompleteResponseModel response = autoCompleteInteractor.search(request);
            TextFields.bindAutoCompletion(searchField, response.getCourses());
            searchField.setEditable(true);
        }).start();
    }

    @FXML
    void saveTimetables() {
        PersistenceInputBoundary persistenceInputBoundary = new PersistenceInteractor();
        persistenceInputBoundary.save(new PersistenceDataModel(timetableList.getTimetableList()));
    }

    @FXML
    void loadTimetables() {
        PersistenceInputBoundary persistenceInputBoundary = new PersistenceInteractor();
        PersistenceDataModel data = persistenceInputBoundary.load();

        timetableList = new TimetableGenerationResponseModel(data.getTimetables());
        filteredTimetableList = new FilterResponseModel(timetableList.getTimetableList());
        timetableCountLabel.setText("of " + timetableList.getTimetableList().size());

        viewTimetable();
    }

    @FXML
    void sortRMPAction() {
        RateMyProfRequestModel request = new RateMyProfRequestModel(filteredTimetableList.getTimetables());
        RateMyProfResponseModel response = rmp.sort(request);
        filteredTimetableList.setTimetables(response.getTimetableList());
        currentTimetableIndex = 0;
        viewTimetable();
    }

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
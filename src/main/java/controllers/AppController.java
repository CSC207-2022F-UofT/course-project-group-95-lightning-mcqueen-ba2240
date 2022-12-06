package controllers;

import entities.base.Timetable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;
import presenters.ErrorWindow;
import use_cases.auto_complete.AutoCompleteInputBoundary;
import use_cases.auto_complete.AutoCompleteInteractor;
import use_cases.auto_complete.AutoCompleteRequestModel;
import use_cases.auto_complete.AutoCompleteResponseModel;
import use_cases.timetable_generation.TimetableGenerationInputBoundary;
import use_cases.timetable_generation.TimetableGenerationInteractor;
import use_cases.timetable_generation.TimetableGenerationRequestModel;
import use_cases.timetable_generation.TimetableGenerationResponseModel;
import use_cases.timetable_view.TimetableViewInputBoundary;
import use_cases.timetable_view.TimetableViewInteractor;
import use_cases.timetable_view.TimetableViewRequestModel;
import use_cases.timetable_view.TimetableViewResponseModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the main view
 */

public class AppController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private TextField selectedTimetableField;

    @FXML
    private Text courseListLabel;

    @FXML
    private Text timetableCountLabel;

    @FXML
    private ComboBox<String> semesterField;

    @FXML
    private TextField yearField;

    @FXML
    private HBox timetableBox;

    private final AutoCompleteInputBoundary autoCompleteInputBoundary = new AutoCompleteInteractor();
    private final TimetableGenerationInputBoundary timetableGenerationInputBoundary = new
            TimetableGenerationInteractor();
    private final TimetableViewInputBoundary timetableViewInputBoundary = new TimetableViewInteractor();

    private final ArrayList<String> courseList = new ArrayList<>();
    private List<Timetable> timetableList = new ArrayList<>();
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

    @FXML
    void generateCourses() {
        TimetableGenerationRequestModel request = new TimetableGenerationRequestModel(courseList);
        TimetableGenerationResponseModel response = timetableGenerationInputBoundary.generate(request);
        timetableList = response.getTimetableList();
        timetableCountLabel.setText("of " + timetableList.size());
        viewTimetable();
    }

    @FXML
    void nextTimetableAction() {
        if (currentTimetableIndex + 1 < timetableList.size()){
            currentTimetableIndex++;
            viewTimetable();
        }else {
            ErrorWindow.callError("Last Timetable", "This is the last timetable!");
        }
    }

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
     * SearchFieldTyped helps call the required functions to populate the search bar with appropriate suggestions
     * for auto complete to work properly, while also setting when the searchField can or cannot be edited.
     */
    @FXML
    void searchFieldTyped() {
        new Thread(() -> {
            // Takes first 3 words from user input and uses that as org for the getSimpleCourses function.
            // Gets the keys from getSimpleCourses output and uses them as autocomplete suggestions.
            searchField.setEditable(false);
            AutoCompleteRequestModel request = new AutoCompleteRequestModel(searchField.getText(), yearField.getText(),
                    semesterField.getValue());
            AutoCompleteResponseModel response = autoCompleteInputBoundary.search(request);
            TextFields.bindAutoCompletion(searchField, response.getCourses());
            searchField.setEditable(true);
        }).start();
    }

    /**
     * Update the TimetableHbox and other related components on change in currentTimetableIndex
     */
    void viewTimetable() {
        TimetableViewRequestModel request = new TimetableViewRequestModel(timetableList.get(currentTimetableIndex));
        TimetableViewResponseModel response = timetableViewInputBoundary.getView(request);

        ObservableList<Node> children = timetableBox.getChildren();
        children.clear();
        children.add(response.getNode());
        HBox.setHgrow(response.getNode(), Priority.ALWAYS);

        selectedTimetableField.setText(String.valueOf(currentTimetableIndex + 1));
    }

    /**
     * Displays the user's selected courses in a label
     */
    void updateCourseListLabel() {
        StringBuilder courseField = new StringBuilder("Added Courses:");
        for (String course: courseList) {
            courseField.append(" ").append(course).append(",");
        }
        courseListLabel.setText(courseField.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String currentYear = String.valueOf(LocalDate.now().getYear());
        semesterField.getItems().add("Fall");
        semesterField.getItems().add("Spring");
        semesterField.setValue("Fall");
        yearField.setText(currentYear);
    }
}

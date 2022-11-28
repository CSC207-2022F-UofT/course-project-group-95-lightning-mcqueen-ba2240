package controllers;

import gateways.API;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller for the main view
 */

public class AppController implements Initializable {

    @FXML
    private Button generateButton;

    @FXML
    private TextField searchField;

    @FXML
    private Text courseListLabel;

    @FXML
    private ComboBox<String> semesterField;

    @FXML
    private TextField yearField;

    public AppController() throws IOException {
    }

    @FXML
    void generateCourses(ActionEvent event) {

    }

    private final API api = new API();

    private final String currentYear = String.valueOf(LocalDate.now().getYear());

    private API.Semester selectedSemester;

    private final ArrayList<String> courseList = new ArrayList<>();




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
        if (courseList.size()>0) {
            courseList.remove(courseList.size() - 1);
            updateCourseListLabel();
        }
        else  {
            callError("Insufficient number of courses.", "No courses have been added.");
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
    void semesterFieldSelected() {
        if (semesterField.getValue().equals("Fall")) {
            selectedSemester = API.Semester.FALL;
        }
        if (semesterField.getValue().equals("Spring")) {
            selectedSemester = API.Semester.SPRING;
        }
    }

    //Function handles searchField typing related actions including autoComplete
    @FXML
    void searchFieldTyped(KeyEvent event) {
        semesterFieldSelected();
        new Thread(() -> {
            // Takes first 3 words from user input and uses that as org for the getSimpleCourses function.
            // Gets the keys from getSimpleCourses output and uses them as autocomplete suggestions.
            if (searchField.getText().length() == 3) {
                searchField.setEditable(false);
                HashMap<String, String> courses;
                try {
                    courses = api.getSimpleCourses(yearField.getText(), selectedSemester, searchField.getText());
                } catch (IOException e) {
                    searchField.setEditable(true);
                    throw new RuntimeException(e);
                }

                TextFields.bindAutoCompletion(searchField, courses.keySet());
                searchField.setEditable(true);
            }
        }).start();

        // Add selected course from searchField to courseList. Show an error message with appropriate descriptions when
        // courseList has more than 5 courses or user is trying to add a duplicate course to courseList.
        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode() == KeyCode.ENTER) {
                    if (courseList.contains(searchField.getText())) {
                        callError("Error: Course already added!",
                                "This course is already in your list. Please choose another course");
                    } else if (courseList.size() >= 5) {
                        callError("Error: Maximum # of courses reached.",
                                "Your list already contains the maximum number of courses (5) for this semester.");
                    } else {
                        courseList.add(searchField.getText());
                    }
                }
            }
        });
    }

    //function calls the error message that will be displayed on screen
    public void callError(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);

            alert.showAndWait();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        semesterField.getItems().add("Fall");
        semesterField.getItems().add("Spring");
        yearField.setText(currentYear);
    }
}

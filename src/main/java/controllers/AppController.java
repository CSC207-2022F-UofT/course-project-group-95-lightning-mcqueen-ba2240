package controllers;

import gateways.course_api.CourseAPI;
import gateways.course_api.StGArtSciAPI;
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
import org.controlsfx.control.textfield.TextFields;
import use_cases.auto_complete.AutoCompleteInteractor;
import use_cases.auto_complete.AutoCompleteRequestModel;
import use_cases.auto_complete.AutoCompleteResponseModel;

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
    private ComboBox<String> semesterField;

    @FXML
    private TextField yearField;

    private final AutoCompleteInteractor autoCompleteInteractor = new AutoCompleteInteractor();

    private final ArrayList<String> courseList = new ArrayList<>();

    @FXML
    void generateCourses(ActionEvent event) {
    }

    //Function handles searchField typing related actions including autoComplete
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

        // Add selected course from searchField to courseList. Show an error message with appropriate descriptions when
        // courseList has more than 5 courses or user is trying to add a duplicate course to courseList.
//        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent key) {
//                if (key.getCode() == KeyCode.ENTER) {
//                    if (courseList.contains(searchField.getText())) {
//                        callError("Error: Course already added!",
//                                "This course is already in your list. Please choose another course");
//                    } else if (courseList.size() >= 5) {
//                        callError("Error: Maximum # of courses reached.",
//                                "Your list already contains the maximum number of courses (5) for this semester.");
//                    } else {
//                        courseList.add(searchField.getText());
//                    }
//                }
//            }
//        });
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

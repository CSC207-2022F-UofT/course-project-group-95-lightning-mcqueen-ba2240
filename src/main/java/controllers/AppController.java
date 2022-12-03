package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;
import use_cases.auto_complete.AutoCompleteInteractor;
import use_cases.auto_complete.AutoCompleteRequestModel;
import use_cases.auto_complete.AutoCompleteResponseModel;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String currentYear = String.valueOf(LocalDate.now().getYear());
        semesterField.getItems().add("Fall");
        semesterField.getItems().add("Spring");
        semesterField.setValue("Fall");
        yearField.setText(currentYear);
    }
}

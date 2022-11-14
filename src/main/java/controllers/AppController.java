package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
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

    @FXML
    void generateCourses(ActionEvent event) {

    }

    @FXML
    void searchFieldTyped(KeyEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        semesterField.getItems().add("Fall");
        semesterField.getItems().add("Spring");
    }
}

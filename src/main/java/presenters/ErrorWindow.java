package presenters;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ErrorWindow {
    public static void callError(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);

            alert.showAndWait();
        });
    }
}

package use_cases.timetable_view;

import javafx.scene.Node;

public class TimetableViewResponseModel {
    private Node node;

    public TimetableViewResponseModel(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}

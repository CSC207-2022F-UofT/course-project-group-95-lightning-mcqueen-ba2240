package use_cases.timetable_view;

import javafx.scene.Node;

public class TimetableViewResponseModel {
    private Node node;
    private String tags;

    public TimetableViewResponseModel(Node node, String tags) {
        this.node = node;
        this.tags = tags;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

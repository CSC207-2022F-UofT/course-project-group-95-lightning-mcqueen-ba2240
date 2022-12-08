package use_cases.timetable_view;

import javafx.scene.Node;

public class TimetableViewResponseModel {
    private Node node;
    private String tags;

    /**
     * A constructor for FilterResponseModel.
     * @param node node of type Node
     * @param tags tags represented as String
     */
    public TimetableViewResponseModel(Node node, String tags) {
        this.node = node;
        this.tags = tags;
    }

    /**
     * A getter for Node.
     * @return return a JavaFX displayable object node
     */
    public Node getNode() {
        return node;
    }

    /**
     * A setter for the node.
     * @param node JavaFX displayable object
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * A getter for Tags.
     * @return return a String of tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * A setter for the node.
     * @param tags tags as Strings
     */
    public void setTags(String tags) {
        this.tags = tags;
    }
}

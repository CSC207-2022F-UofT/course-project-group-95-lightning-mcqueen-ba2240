package entities.base;

public interface Collapsible {
    /**
     * Check if two Meetings can be treated as a single object
     * @param meeting The Meeting to be compared to
     * @return boolean of whether two meetings are Collapsible
     */
    boolean isCollapsible(Meeting meeting);

    /**
     * Merge two meetings objects into a single object
     * @param meeting The Meeting to be merged
     */
    void collapse(Meeting meeting);
}

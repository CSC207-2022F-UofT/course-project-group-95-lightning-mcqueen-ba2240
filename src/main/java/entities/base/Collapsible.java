package entities.base;

public interface Collapsible {
    boolean isCollapsible(Meeting meeting);

    void collapse(Meeting meeting);
}

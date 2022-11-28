package entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 * This dataclass stores the information for a given timetable.
 */

public class Timetable {
    private ArrayList<Meeting> meetings;

    private HashSet<String> tags;

    public Timetable(){}

    /**
     * Construct a Timetable, setting the meetings, and the tags.
     * @param meetings an array of meetings (Lectures/Tutorials) belonging to the course
     * @param tags an array of Strings containing various tags that we will use to filter timetables.
     */
    public Timetable(ArrayList<Meeting> meetings, HashSet<String> tags) {
        this.meetings = meetings;
        this.tags = tags;
    }

    /**
     * A getter for the meetings.
     * @return the course meetings as a Meeting array
     */
    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * A getter for the tags
     * @return the tags as a String array
     */
    public HashSet<String> getTags() {
        return tags;
    }

    // Need setters for Jackson to work
    /**
     * A setter for the meetings.
     * @param meetings the course meetings as a Meeting array
     */
    public void setMeetings(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
    }

    /**
     * A getter for the tags
     * @param tags the tags as a String array
     */
    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timetable)) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(meetings, timetable.meetings) && Objects.equals(tags, timetable.tags);
    }
}

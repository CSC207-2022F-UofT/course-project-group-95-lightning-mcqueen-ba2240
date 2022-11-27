package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This dataclass stores the information for a given timetable.
 */

public class Timetable {
    private final List<Meeting> meetings;
    private final HashSet<String> tags;

    /**
     * Construct a Timetable, setting the meetings, and the tags.
     * @param meetings an array of meetings (Lectures/Tutorials) belonging to the course
     * @param tags an array of Strings containing various tags that we will use to filter timetables.
     */
    public Timetable(List<Meeting> meetings, HashSet<String> tags) {
        this.meetings = meetings;
        this.tags = tags;
    }

    /**
     * A getter for the meetings.
     * @return the course meetings as a Meeting array
     */
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * A getter for the tags
     * @return the tags as a String array
     */
    public HashSet<String> getTags() {
        return tags;
    }

    public List<Session> getSortedSessions() {
        List<Session> sessions = new ArrayList<>();
        for (Meeting meeting: this.meetings){
            sessions.addAll(meeting.getSessions());
        }
        Collections.sort(sessions);
        return sessions;
    }
}

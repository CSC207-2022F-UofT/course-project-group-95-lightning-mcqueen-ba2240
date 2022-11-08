package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This dataclass stores the information for a given course.
 *
 */
public class Course {
    private final int id;
    private final String name;
    private final String code;
    private final List<Meeting> meetings;

    /**
     * Construct a Course, setting the course id, name, instructor and meetings.
     * @param id the unique course id used by the UofT Arts & Science Department e.g. 61820
     * @param name the name of the course used by the UofT Arts & Science Department e.g. "Software Design"
     * @param code the standard course code used by UofT Arts & Science Department e.g. CSC207H1
     */
    public Course(int id, String name, String code, Meeting... meetings){
        this.id = id;
        this.name = name;
        this.code = code;
        this.meetings = new ArrayList<>(Arrays.asList(meetings));
    }

    /**
     * A getter for the course id.
     * @return the course id as an int
     */
    public int getId() {
        return this.id;
    }

    /**
     * A getter for the course name.
     * @return the course name as a String
     */
    public String getName() {
        return this.name;
    }

    /**
     * A getter for the course name.
     *
     * @return the course meetings as a Meeting array
     */
    public List<Meeting> getMeetings() {
        return this.meetings;
    }

    /**
     * A getter for the course code
     * @return the course code as a String
     */
    public String getCode() {
        return code;
    }

    /**
     * Add a new meeting to the course
     * @param meeting the meeting to be added to the course
     */
    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", meetings=" + meetings +
                '}';
    }
}

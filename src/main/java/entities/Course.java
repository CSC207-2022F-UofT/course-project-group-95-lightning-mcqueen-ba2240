package entities;

/**
 * This dataclass stores the information for a given course.
 *
 */
public class Course {
    private final int id;
    private final String name;
    private final String code;
    private final Meeting[] meetings;

    /**
     * Construct a Course, setting the course id, name, instructor and meetings.
     * @param id the course id used by the UofT Arts & Science Department
     * @param name the name of the course used by the UofT Arts & Science Department
     * @param meetings an array of meetings (Lectures/Tutorials) belonging to the course
     */

    public Course(int id, String name, String code, Meeting[] meetings){
        this.id = id;
        this.name = name;
        this.code = code;
        this.meetings = meetings;
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
     * @return the course meetings as a Meeting array
     */
    public Meeting[] getMeetings() {
        return this.meetings;
    }

    /**
     * A getter for the course code
     * @return the course code as a String
     */
    public String getCode() {
        return code;
    }
}

/**
 * This dataclass stores the information for a given course.
 *
 *
 */
public class Course {
    private final int id;
    private final String name;
    private final String instructor;
    private final Meeting[] meetings;

    /**
     * Construct a Course, setting the course id, name, instructor and meetings.
     * @param id the course id used by the UofT Arts & Science Department
     * @param name the name of the course used by the UofT Arts & Science Department
     * @param instructor the professor/student who is instructing the course
     * @param meetings an array of meetings (Lectures/Tutorials) belonging to the course
     */

    public Course(int id, String name, String instructor, Meeting[] meetings){
        this.id = id;
        this.name = name;
        this.instructor = instructor;
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
     * A getter for the course instructor.
     * @return the course instructor as a String
     */
    public String getInstructor() {
        return this.instructor;
    }

    /**
     * A getter for the course name.
     * @return the course meetings as a Meeting[]
     */
    public Meeting[] getMeetings() {
        return this.meetings;
    }
}

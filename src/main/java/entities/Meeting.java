package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This dataclass stores the information for a given meeting.
 *
 */
public class Meeting {
    private final String section;
    private final Type type;
    private final List<Session> sessions;
    private final String instructor;
    private final int capacity;
    private final int enrollment;
    private final int waitlist;
    private Float rateMyProf;

    /**
     * Construct a Meeting, setting the section, meeting type, duration, sessions, instructor, rateMyProf score.
     * @param section the section of the meeting (LEC"0101", TUT"5101")
     * @param type the type of the meeting (Lecture, Tutorial, Practical)
     * @param instructor the professor/student who is instructing the entities.Meeting
     * @param capacity the number of students that can enroll in the course
     * @param enrollment the number of students that have enrolled in the course
     * @param waitlist the number of students waitlisted for the course
     * @param sessions a variable argument for sessions in the Meeting
     */
    public Meeting(String section, Type type, String instructor, int capacity, int enrollment, int waitlist,
                   Session... sessions) {
        this.section = section;
        this.type = type;
        this.instructor = instructor;

        this.capacity = capacity;
        this.enrollment = enrollment;
        this.waitlist = waitlist;

        this.sessions = new ArrayList<>(Arrays.asList(sessions));
        this.rateMyProf = null;
    }

    /**
     * A setter for the entities.Meeting's rateMyProf score.
     * @param score the score to be set as the rateMyProf score
     */
    public void setRateMyProf(Float score) {
        this.rateMyProf = score;
    }

    /**
     * A getter for the entities.Meeting's section
     * @return the entity.Meeting's section as a String
     */
    public String getSection() {
        return section;
    }

    /**
     * A getter for the entities.Meeting's type.
     * @return the entities.Meeting's type as a String
     */
    public Type getType() {
        return this.type;
    }

    /**
     * A getter for the entities.Meeting's code e.g. LEC0101
     * @return the entities.Meeting's code as a String
     */
    public String getCode() {
        return this.type.toString() + this.section;
    }

    /**
     * A getter for the entities.Meeting's sessions.
     * @return the entities.Meeting's sessions as a Session[]
     */
    public List<Session> getSessions() {
        return this.sessions;
    }

    /**
     * A getter for the entities.Meeting's instructor.
     * @return the entities.Meeting's instructor as a String
     */
    public String getInstructor() {
        return this.instructor;
    }

    /**
     * A getter for the entities.Meeting's rateMyProf score.
     * @return the entities.Meeting's rateMyProf score as an Object (can either be null or of type double)
     */
    public Float getRateMyProf() {
        return this.rateMyProf;
    }

    /**
     * A getter for the entities.Meeting's capacity.
     * @return the entities.Meeting's capacity as an integer
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * A getter for the entities.Meeting's enrollment.
     * @return the entities.Meeting's enrollment as an integer
     */
    public int getEnrollment() {
        return enrollment;
    }

    /**
     * A getter for the entities.Meeting's waitlist.
     * @return the entities.Meeting's waitlist as an integer
     */
    public int getWaitlist() {
        return waitlist;
    }

    /**
     * Add a new Session to the Meeting
     * @param session the meeting to be added to the course
     */
    public void addSessions(Session session) {
        this.sessions.add(session);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "section='" + section + '\'' +
                ", type=" + type +
                ", sessions=" + sessions +
                ", instructor='" + instructor + '\'' +
                ", capacity=" + capacity +
                ", enrollment=" + enrollment +
                ", waitlist=" + waitlist +
                ", rateMyProf=" + rateMyProf +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Meeting)){
            return false;
        }
        Meeting meeting = (Meeting) o;
        return capacity == meeting.capacity && enrollment == meeting.enrollment && waitlist == meeting.waitlist && section.equals(meeting.section) && type == meeting.type && sessions.equals(meeting.sessions) && instructor.equals(meeting.instructor) && Objects.equals(rateMyProf, meeting.rateMyProf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, type, sessions, instructor, capacity, enrollment, waitlist, rateMyProf);
    }

    /**
     * Enum for Meeting Type
     */
    public enum Type {
        LEC,
        TUT,
        PRA
    }
}

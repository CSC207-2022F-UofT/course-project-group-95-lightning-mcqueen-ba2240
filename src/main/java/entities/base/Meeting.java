package entities.base;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import entities.stgartsci.StGArtSciMeeting;

import java.io.IOException;
import java.util.*;

/**
 * This dataclass stores the information for a given meeting.
 *
 */
public class Meeting {
    private String section;
    private Type type;
    private List<Session> sessions;
    private String instructor;
    private int capacity;
    private int enrollment;
    private int waitlist;
    private Float rateMyProf;

    public Meeting(){}

    /**
     * Construct a Meeting, setting the section, meeting type, duration, sessions, instructor, rateMyProf score.
     * @param section the section of the meeting
     * @param type the type of the meeting
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
     * A getter for the entities.Meeting's section
     * @param section the new section as String
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * A getter for the entities.Meeting's type.
     * @return the entities.Meeting's type as a String
     */
    public Type getType() {
        return this.type;
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
     * A setter for the entities.Meeting's type.
     * @param type the entities.Meeting's type as a String
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * A setter for the entities.Meeting's sessions.
     * @param sessions the entities.Meeting's sessions as a Session[]
     */
    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    /**
     * A setter for the entities.Meeting's instructor.
     * @param instructor the entities.Meeting's instructor as a String
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * A setter for the entities.Meeting's capacity.
     * @param capacity  the entities.Meeting's capacity as an integer
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * A setter for the entities.Meeting's enrollment.
     * @param enrollment  the entities.Meeting's enrollment as an integer
     */
    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }

    /**
     * A getter for the entities.Meeting's waitlist.
     * @param waitlist the entities.Meeting's waitlist as an integer
     */
    public void setWaitlist(int waitlist) {
        this.waitlist = waitlist;
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
     * Returns a Set containing the time signature of all Sessions in the Meeting
     * @return Set of the Session's time signatures
     */
    public Set<String> timeHash(){
        Set<String> hash = new HashSet<>();
        for (Session session: sessions){
            hash.add(session.timeHash());
        }
        return hash;
    }

    @JsonDeserialize(using = Type.TypeDeserializer.class)
    public interface Type{
        /**
         * Deserialization class for Meeting Types
         */
        class TypeDeserializer extends StdDeserializer<Type> {
            public TypeDeserializer() {
                this(null);
            }

            public TypeDeserializer(Class<?> vc) {
                super(vc);
            }

            @Override
            public Type deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String t = p.getCodec().readTree(p).toString().replace("\"", "");
                if (StGArtSciMeeting.StGArtSciType.parse(t) != null){
                    return StGArtSciMeeting.StGArtSciType.parse(t);
                }
                return DefaultType.DEFAULT;
            }
        }
    }

    /**
     * Enum for Meeting Type
     */
    public enum DefaultType implements Type{
        DEFAULT
    }
}

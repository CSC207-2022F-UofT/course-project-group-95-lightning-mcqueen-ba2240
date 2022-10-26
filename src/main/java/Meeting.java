/**
 * This dataclass stores the information for a given meeting.
 *
 */
public class Meeting {
    private final String type;
    private final double duration;
    private final Session[] sessions;
    private final String instructor;
    private Object rateMyProf;
    private final int waitlistSize;

    /**
     * Construct a Meeting, setting the meeting type, duration, sessions, instructor, rateMyProf score and waitlistSize.
     * @param type the type of the meeting (Lecture, Tutorial, Practical)
     * @param duration the length of the Meeting in hours
     * @param sessions a Session[] containing the different sessions for the Meeting
     * @param instructor the professor/student who is instructing the Meeting
     * @param rateMyProf the rate my professor score associated with the instructor (null if score doesn't exist)
     * @param waitlistSize the number of people on the waitlist as an integer
     */
    public Meeting(String type, double duration, Session[] sessions, String instructor, double rateMyProf, int waitlistSize) {
        this.type = type;
        this.duration = duration;
        this.sessions = sessions;
        this.instructor = instructor;
        this.rateMyProf = rateMyProf;
        this.waitlistSize = waitlistSize;
    }

    public Meeting(String type, double duration, Session[] sessions, String instructor, int waitlistSize) {
        this.type = type;
        this.duration = duration;
        this.sessions = sessions;
        this.instructor = instructor;
        this.rateMyProf = null;
        this.waitlistSize = waitlistSize;
    }

    /**
     * A setter for the Meeting's rateMyProf score.
     * @param score the score to be set as the rateMyProf score
     */
    public void setRateMyProf(double score) {
        this.rateMyProf = score;
    }

    /**
     * A getter for the Meeting's type.
     * @return the Meeting's type as a String
     */
    public String getType() {
        return this.type;
    }

    /**
     * A getter for the Meeting's duration.
     * @return the Meeting's duration as a double
     */
    public double getDuration() {
        return this.duration;
    }

    /**
     * A getter for the Meeting's sessions.
     * @return the Meeting's sessions as a Session[]
     */
    public Session[] getSessions() {
        return this.sessions;
    }

    /**
     * A getter for the Meeting's instructor.
     * @return the Meeting's instructor as a String
     */
    public String getInstructor() {
        return this.instructor;
    }

    /**
     * A getter for the Meeting's rateMyProf score.
     * @return the Meeting's rateMyProf score as an Object (can either be null or of type double)
     */
    public Object getRateMyProf() {
        return this.rateMyProf;
    }

    /**
     * A getter for the Meeting's waitlistSize
     * @return the Meeting's waitlistSize as an int
     */
    public int getWaitlistSize() {
        return this.waitlistSize;
    }
}

package entities.stgartsci;

import entities.base.Collapsible;
import entities.base.Meeting;
import entities.base.Session;

public class StGArtSciMeeting extends Meeting implements Collapsible {

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
    public StGArtSciMeeting(String section, Type type, String instructor, int capacity, int enrollment, int waitlist,
                            Session... sessions) {
        super(section, type, instructor, capacity, enrollment, waitlist, sessions);
    }

    /**
     * Meetings in the same Course can be merged if they are not
     * Lectures and take place at the same time
     * @param meeting The Meeting to be compared to
     * @return boolean of whether two meetings are Collapsible
     */
    @Override
    public boolean isCollapsible(Meeting meeting) {
        // If Meetings are not a lecture and have the same time signature
        if (this.getType() != StGArtSciType.LEC && this.getType().equals(meeting.getType())){
            return this.timeHash().equals(meeting.timeHash());
        }
        return false;
    }

    /**
     * Meetings in the same Course can be merged if they are not
     * Lectures and take place at the same time. This is done by
     * appending the Meeting code to the end of the section parameter
     * @param meeting The Meeting to be merged
     */
    @Override
    public void collapse(Meeting meeting) {
        if (isCollapsible(meeting)){
            String oldSection = meeting.getSection();
            String suffix = oldSection.substring(oldSection.length()- 4);
            this.setSection(this.getSection() + "\\" + suffix);
        }
    }

    /**
     * UofT St. George Arts & Science Meeting Types
     */
    public enum StGArtSciType implements Type{
        LEC,
        TUT,
        PRA;

        public static Type parse(String s) {
            if (s.equals("LEC")){
                return LEC;
            }
            if (s.equals("TUT")){
                return TUT;
            }
            if (s.equals("PRA")){
                return PRA;
            }
            return null;
        }
    }
}

package entities.stgartsci;

import entities.base.Collapsible;
import entities.base.Meeting;
import entities.base.Session;

public class StGArtSciMeeting extends Meeting implements Collapsible {

    public StGArtSciMeeting(String section, Type type, String instructor, int capacity, int enrollment, int waitlist,
                            Session... sessions) {
        super(section, type, instructor, capacity, enrollment, waitlist, sessions);
    }

    @Override
    public boolean isCollapsible(Meeting meeting) {
        // If Meetings are not a lecture and have the same time signature
        if (this.getType() != DefaultType.DEFAULT && this.getType().equals(meeting.getType())){
            return this.timeHash().equals(meeting.timeHash());
        }
        return false;
    }

    @Override
    public void collapse(Meeting meeting) {
        if (isCollapsible(meeting)){
            String oldSection = meeting.getSection();
            String suffix = oldSection.substring(oldSection.length()- 4);
            this.setSection(this.getSection() + "\\" + suffix);
        }
    }

    public enum StGArtSciType implements Type{
        LEC,
        TUT,
        PRA;

        public static Type parse(String s) {
            if (s.equals("LEC")){
                return LEC;
            } if (s.equals("TUT")){
                return TUT;
            } if (s.equals("PRA")){
                return PRA;
            }
            return DefaultType.DEFAULT;
        }
    }
}

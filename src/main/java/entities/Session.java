package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * This dataclass stores the information for a given session.
 */
public class Session {
    private final DayOfWeek meetingDay;
    private final LocalTime meetingStartTime;
    private final LocalTime meetingEndTime;
    private final String meetingRoom;
    /**
     * Construct a Session, setting the session day, start time, end time and room.
     *
     * @param meetingDay       the day the session is taking place MONDAY, TUESDAY, ...
     * @param meetingStartTime the start time of the meeting in 24H format ('12:00', '17:30')
     * @param meetingEndTime   the end time of the meeting in 24H format ('12:00', '17:30')
     * @param meetingRoom      the UofT room code of the session (BA2240)
     */
    public Session(DayOfWeek meetingDay, LocalTime meetingStartTime, LocalTime meetingEndTime, String meetingRoom) {
        this.meetingDay = meetingDay;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.meetingRoom = meetingRoom;
    }

    /**
     * A getter for the session day.
     * @return the session day as a String
     */
    public DayOfWeek getMeetingDay() {
        return meetingDay;
    }

    /**
     * A getter for the session start time
     * @return the session start time as a LocalTime object
     */
    public LocalTime getMeetingStartTime() {
        return meetingStartTime;
    }

    /**
     * A getter for the session end time
     * @return the session start time as a LocalTime object
     */
    public LocalTime getMeetingEndTime() {
        return meetingEndTime;
    }

    /**
     * A getter for the session meeting room.
     * @return the course id as a String
     */
    public String getMeetingRoom() {
        return meetingRoom;
    }

    @Override
    public String toString() {
        return "Session{" +
                "meetingDay=" + meetingDay +
                ", meetingStartTime=" + meetingStartTime +
                ", meetingEndTime=" + meetingEndTime +
                ", meetingRoom='" + meetingRoom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Session)){
            return false;
        }
        Session session = (Session) o;
        return meetingDay == session.meetingDay && meetingStartTime.equals(session.meetingStartTime) && meetingEndTime.equals(session.meetingEndTime) && meetingRoom.equals(session.meetingRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingDay, meetingStartTime, meetingEndTime, meetingRoom);
    }
}

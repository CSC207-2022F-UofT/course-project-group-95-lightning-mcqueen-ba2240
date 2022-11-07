package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * This record dataclass stores the information for a given session.
 */
public record Session(DayOfWeek meetingDay, LocalTime meetingStartTime, LocalTime meetingEndTime, String meetingRoom) {
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
        this.meetingEndTime = meetingStartTime;
        this.meetingRoom = meetingRoom;
    }


    /**
     * A getter for the session day.
     *
     * @return the session day as a String
     */
    @Override
    public DayOfWeek meetingDay() {
        return meetingDay;
    }

    /**
     * A getter for the session start time
     *
     * @return the session start time as a LocalTime object
     */
    @Override
    public LocalTime meetingStartTime() {
        return meetingStartTime;
    }

    /**
     * A getter for the session end time
     *
     * @return the session start time as a LocalTime object
     */
    @Override
    public LocalTime meetingEndTime() {
        return meetingEndTime;
    }

    /**
     * A getter for the session meeting room.
     *
     * @return the course id as a String
     */
    @Override
    public String meetingRoom() {
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
}

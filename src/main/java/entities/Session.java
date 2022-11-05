package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
     * @param meetingDay the day the session is taking place ('MO', 'TU', ...)
     * @param meetingStartTime the start time of the meeting in 24H format ('12:00', '17:30')
     * @param meetingEndTime the end time of the meeting in 24H format ('12:00', '17:30')
     * @param meetingRoom the UofT room code of the session (BA2240)
     */
    public Session(String meetingDay, String meetingStartTime, String meetingEndTime, String meetingRoom) throws Exception{
        this.meetingDay = parseDayOfWeek(meetingDay);
        this.meetingStartTime = LocalTime.parse(meetingStartTime);
        this.meetingEndTime = LocalTime.parse(meetingEndTime);
        this.meetingRoom = meetingRoom;
    }

    /**
     * Parse the given meeting string day String
     * @param day the day the session is taking place ('MO', 'TU', ...)
     * @return DayOfWeek object with the corresponding day of the week
     * @throws Exception if the day param is not valid
     */
    private DayOfWeek parseDayOfWeek(String day) throws Exception {
        switch (day){
            case "MO":
                return DayOfWeek.MONDAY;
            case "TU":
                return DayOfWeek.TUESDAY;
            case "WE":
                return DayOfWeek.WEDNESDAY;
            case "TH":
                return DayOfWeek.THURSDAY;
            case "FR":
                return DayOfWeek.FRIDAY;
            case "SA":
                return DayOfWeek.SATURDAY;
            case "SU":
                return DayOfWeek.SUNDAY;
        }
        throw new Exception("Invalid day string");
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
}

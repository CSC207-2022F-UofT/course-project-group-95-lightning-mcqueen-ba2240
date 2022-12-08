package entities.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * This dataclass stores the information for a given session.
 */
public class Session implements Comparable<Session>{
    private DayOfWeek day;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime startTime;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime endTime;

    public Session() {}

    /**
     * Construct a Session, setting the session day, start time, end time and room.
     *
     * @param day       the day the session is taking place MONDAY, TUESDAY, ...
     * @param startTime the start time of the meeting in 24H format ("12:00", "17:30")
     * @param endTime   the end time of the meeting in 24H format ("12:00", "17:30")
     */
    public Session(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * A getter for the session day.
     * @return the session day as a DayOfWeek Object
     */
    public DayOfWeek getDay() {
        return day;
    }

    /**
     * A getter for the session start time
     * @return the session start time as a LocalTime object
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * A getter for the session end time
     * @return the session start time as a LocalTime object
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * A setter for the session day.
     * @param day the session day as a DayOfWeek Object
     */
    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    /**
     * A getter for the session start time
     * @param startTime the session start time as a LocalTime object
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * A getter for the session end time
     * @param endTime  the session start time as a LocalTime object
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Session{" +
                "day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return day == session.day && startTime.equals(session.startTime) && endTime.equals(session.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, endTime);
    }

    /**
     * Returns a unique hash representing the time signature of a Session e.g. MONDAY12:0013:00
     * @return String representation of the time signature
     */
    public String timeHash() {
        return day.toString() + startTime.toString() + endTime.toString();
    }

    @Override
    public int compareTo(Session o) {
        if (this.day.getValue() < o.day.getValue()) {
            return 1;
        }
        if (this.startTime.isBefore(o.startTime)) {
            return 1;
        }
        return -1;
        //No need to sort on invalid timetables therefore no overlap i.e., 0 case
    }

    public static class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

        public LocalTimeDeserializer(){this(null);}

        public LocalTimeDeserializer(Class<?> vc){super(vc);}
        @Override
        public LocalTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
            return LocalTime.parse(arg0.getText());
        }
    }

    public static class LocalTimeSerializer extends StdSerializer<LocalTime> {

        public LocalTimeSerializer(){this(null);}

        public LocalTimeSerializer(Class<LocalTime> vc){super(vc);}
        @Override
        public void serialize(LocalTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
            arg1.writeString(arg0.toString());
        }
    }
}

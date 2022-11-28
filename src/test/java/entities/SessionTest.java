package entities;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class SessionTest {
    @Test
    public void testTimeHash() {
        Session session = new Session(
                DayOfWeek.MONDAY,
                LocalTime.parse("12:20"),
                LocalTime.parse("14:40"), ""
        );

        String expected = "MONDAY12:2014:40";
        String actual = session.timeHash();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testTimeHash2() {
        Session session = new Session(
                DayOfWeek.FRIDAY,
                LocalTime.parse("16:10"),
                LocalTime.parse("20:35"), ""
        );

        String expected = "FRIDAY16:1020:35";
        String actual = session.timeHash();

        Assertions.assertEquals(expected, actual);
    }
}

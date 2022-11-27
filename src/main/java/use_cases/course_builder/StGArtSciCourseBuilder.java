package use_cases.course_builder;

import entities.base.Course;
import entities.base.Meeting;
import entities.base.Session;
import entities.stgartsci.StGArtSciMeeting;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * A Builder class that is in charge of parsing the raw string
 * data provided by the API to make the entities required.
 * Caches RateMyProf scores to prevent repeated calls
 */
public class StGArtSciCourseBuilder implements CourseBuilder{
    private Course course;
    private Meeting meeting;
    private Session session;
    private final HashMap<String, Float> rmpCache = new HashMap<>();

    /**
     * Start building a new Course object
     * @param id the unique course id used by the UofT Arts & Science Department e.g. 61820
     * @param name the name of the course used by the UofT Arts & Science Department e.g. "Software Design"
     * @param code the standard course code used by UofT Arts & Science Department e.g. CSC207H1
     */
    public void newCourse(String id, String name, String code) {
        this.course = new Course(Integer.parseInt(id), name, code);
    }

    /**
     * Add a meeting object to the cache
     * @param section the section of the meeting (LEC"0101", TUT"5101")
     * @param type the type of the meeting (Lecture, Tutorial, Practical)
     * @param instructor the professor/student who is instructing the entities.Meeting
     * @param capacity the number of students that can enroll in the course
     * @param enrollment the number of students that have enrolled in the course
     * @param waitlist the number of students waitlisted for the course
     */
    public void newMeeting(String section, String type, String instructor,
                           int capacity, int enrollment, int waitlist) {
        this.meeting = new StGArtSciMeeting(section, StGArtSciMeeting.StGArtSciType.parse(type),
                instructor, capacity, enrollment, waitlist);
    }

    /**
     * Push the Meeting object from the cache onto the Course
     */
    public void pushMeeting() {
        course.addMeeting(meeting);
    }

    /**
     * Add a session object to the cache
     * @param meetingDay the day the session is taking place MONDAY, TUESDAY, ...
     * @param meetingStartTime the start time of the meeting in 24H format ('12:00', '17:30')
     * @param meetingEndTime the end time of the meeting in 24H format ('12:00', '17:30')
     */
    public void newSession(String meetingDay, String meetingStartTime,
                           String meetingEndTime, boolean rmp) {
        this.session = new Session(parseDayOfWeek(meetingDay), LocalTime.parse(meetingStartTime),
                LocalTime.parse(meetingEndTime));
        if (rmp) {
            checkRMP();
        }
    }

    /**
     * Check the RateMyProfessor in cache or API, save if not in cache
     */
    private void checkRMP() {
        String instructor = meeting.getInstructor();
        if (!instructor.isEmpty()){
            if (rmpCache.containsKey(instructor)){
                meeting.setRateMyProf(rmpCache.get(instructor));
            }else {
                // TODO: RateMyProf Lookup and Caching
            }
        }
    }

    /**
     * Push the Session object from the cache onto the Meeting
     */
    public void pushSession() {
        meeting.addSessions(session);
    }

    /**
     * Parse the given meeting string day String
     * @param day the day the session is taking place ('MO', 'TU', ...)
     * @return DayOfWeek object with the corresponding day of the week or null if invalid
     */
    private DayOfWeek parseDayOfWeek(String day) {
        switch (day) {
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
        return null;
    }

    /**
     * Returns the built Course object
     * @return the cached Course object
     */
    public Course build() {
        return course;
    }
}

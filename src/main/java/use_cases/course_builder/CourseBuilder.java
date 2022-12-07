package use_cases.course_builder;

import entities.base.Course;

public interface CourseBuilder {
    /**
     * Start building a new Course object
     * @param id the unique course id used by the UofT Arts & Science Department e.g. 61820
     * @param name the name of the course used by the UofT Arts & Science Department e.g. "Software Design"
     * @param code the standard course code used by UofT Arts & Science Department e.g. CSC207H1
     */
    void newCourse(String id, String name, String code);

    /**
     * Add a meeting object to the cache
     * @param section the section of the meeting
     * @param type the type of the meeting (Lecture, Tutorial, Practical)
     * @param instructor the professor/student who is instructing the entities.Meeting
     * @param capacity the number of students that can enroll in the course
     * @param enrollment the number of students that have enrolled in the course
     * @param waitlist the number of students waitlisted for the course
     */
    void newMeeting(String section, String type, String instructor, int capacity, int enrollment, int waitlist);

    /**
     * Push the Meeting object from the cache onto the Course
     */
    void pushMeeting();

    /**
     * Add a session object to the cache
     * @param meetingDay the day the session is taking place MONDAY, TUESDAY, ...
     * @param meetingStartTime the start time of the meeting in 24H format ('12:00', '17:30')
     * @param meetingEndTime the end time of the meeting in 24H format ('12:00', '17:30')
     */
    void newSession(String meetingDay, String meetingStartTime, String meetingEndTime, boolean rmp);

    /**
     * Push the Session object from the cache onto the Meeting
     */
    void pushSession();

    /**
     * Returns the built Course object
     * @return the cached Course object
     */
    Course build();
}

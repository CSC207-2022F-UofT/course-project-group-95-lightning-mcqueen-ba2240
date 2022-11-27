package gateways.course_api;

import entities.Course;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class StGArtSciAPI extends CourseAPI{
    private static final String API_URL = "https://timetable.iit.artsci.utoronto.ca/api/";

    /**
     * Look up Courses from an organisation on the UofT Arts & Science API
     * or local cache. Get Course key and Course Title in a HashMap and cache the result
     * @param year is the year when the course takes place
     * @param semester relevant Semester enum
     * @param org Course organisation e.g. CSC
     * @return a HashMap with the CourseKey as the Key and the Course Code and Title as the Value
     * @throws IOException if the HTTP request fails
     */
    public HashMap<String, String> getNames(String year, Semester semester, String org) throws IOException {
        // Check cache
        if (cache.containsKey(org)){
            return cache.get(org);
        }

        // API_URL/{YEAR}9/course?org={ORG_CODE}
        String params = String.format("%s9/courses?code=%s", year, org);
        JSONObject res = request(API_URL + params);

        // Store the Course Key and Title as KV pairs
        HashMap<String, String> courses = new HashMap<>();
        Iterator<String> keys = res.keys();
        while (keys.hasNext()){
            String key = keys.next();
            JSONObject course = (JSONObject) res.get(key);

            String section = course.getString("section");
            // Store Courses that take place within the selected semester or for both semesters
            if (semester.match(section)){
                // Value: Course Code: Course Title
                courses.put(key, String.format("%s: %s", course.get("code"), course.get("courseTitle")));
            }
        }

        // Add to cache
        cache.put(org, courses);

        return courses;
    }

    /**
     * Build a Course object using key looked up from a SimpleCourse API Call
     * @param key Course key used to identify course in response
     * @param rmp the option to lookup RateMyProfessor score
     * @return a Course object of the course looked up
     * @throws IOException if the HTTP request fails
     */
    public Course getCourse(String key, boolean rmp) throws IOException {
        // API_URL/{YEAR}9/course?code={CODE}
        String params = String.format("%s9/courses?code=%s", key.substring(11, 15), key.substring(0, 8));
        JSONObject res = CourseAPI.request(API_URL + params).getJSONObject(key);

        // Create a new course using the response JSON
        courseBuilder.newCourse(
                res.getString("courseId"),
                res.getString("courseTitle"),
                res.getString("code")
        );

        // Retrieve the meeting object and iterate over the keys
        JSONObject meetingsObject = res.getJSONObject("meetings");
        Iterator<String> meetings = meetingsObject.keys();
        while(meetings.hasNext()) {
            String meetingKey = meetings.next();
            JSONObject meeting = meetingsObject.getJSONObject(meetingKey);

            // Check if the meeting is cancelled
            if (!meeting.getString("cancel").equals("Cancelled")) {
                // Find instructor name
                String instructor = "";
                // Check if instructor value is not empty array
                if (meeting.get("instructors") instanceof JSONObject) {
                    JSONObject instructorsObject = meeting.getJSONObject("instructors");
                    Iterator<String> instructors = instructorsObject.keys();

                    // Get first instructor
                    if (instructors.hasNext()) {
                        JSONObject instructorObject = instructorsObject.getJSONObject(instructors.next());
                        instructor = instructorObject.getString("firstName") +
                                " " + instructorObject.getString("lastName");
                    }
                }

                // Create a new meeting object using the Meeting JSON
                courseBuilder.newMeeting(
                        meeting.getString("sectionNumber"),
                        meeting.getString("teachingMethod"),
                        instructor,
                        meeting.getInt("enrollmentCapacity"),
                        meeting.getInt("actualEnrolment"),
                        meeting.getInt("actualWaitlist")
                );

                // Retrieve the sessions withing the meeting and iterate over the keys
                JSONObject sessionsObject = meeting.getJSONObject("schedule");
                Iterator<String> sessions = sessionsObject.keys();
                while (sessions.hasNext()) {
                    String sessionKey = sessions.next();
                    JSONObject session = sessionsObject.getJSONObject(sessionKey);

                    // Create a new Session object and push it onto the cached Meeting
                    String startTime = session.get("meetingStartTime").toString();
                    String endTime = session.get("meetingEndTime").toString();
                    if (!startTime.equals("null") && !endTime.equals("null")) {
                        courseBuilder.newSession(
                                Objects.toString(session.get("meetingDay"), ""),
                                startTime,
                                endTime,
                                Objects.toString(session.get("assignedRoom1"), ""),
                                rmp
                        );
                    }
                    courseBuilder.pushSession();
                }
                // Push the Meeting object into the cached Course object
                courseBuilder.pushMeeting();
            }
        }

        return courseBuilder.build();
    }

    public enum StGArtSciSemester implements Semester{
        FALL("F"),
        SPRING("S"),
        YEAR("Y");

        private final String code;

        StGArtSciSemester(String code){
            this.code = code;
        }

        @Override
        public boolean match(String code) {
            return code.equals(this.code) || code.equals(YEAR.code);
        }
    }
}

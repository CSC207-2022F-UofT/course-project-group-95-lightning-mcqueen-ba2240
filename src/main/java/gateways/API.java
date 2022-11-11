package gateways;

import entities.Course;
import org.json.JSONObject;
import use_cases.CourseBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * A gateway for the UofT Arts and Science API that communicates
 * with the CourseFactory use case to return Course objects and
 * a simplified list of courses for lookup. Stores result in a
 * cache for repeated lookups
 */
public class API {
    private static final String API_URL = "https://timetable.iit.artsci.utoronto.ca/api/";
    private final CourseBuilder courseBuilder = new CourseBuilder();

    private final HashMap<String, HashMap<String, String>> simpleCourseCache = new HashMap<>();

    /**
     * Make an HTTP Get request to the UofT Arts and Science API and return the data as a JSONObject
     * @param url API endpoint and params
     * @return JSON object containing the response
     * @throws IOException if the HTTP request fails
     */
    private static JSONObject request(String url) throws IOException {
        // Create a URL object and make a GET request
        URL api = new URL(API_URL + url);
        URLConnection con = api.openConnection();

        // Read the data return and store it in a String
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // Parse the returned JSON into a JSON object
        return new JSONObject(content.toString());
    }

    /**
     * Look up Courses from a organisation on the UofT Arts & Science API
     * or local cache. Get Course key and Course Title in a HashMap and cache the result
     * @param year is the year when the course takes place
     * @param semester relevant Semester enum
     * @param org Course organisation e.g. CSC
     * @return a HashMap with the CourseKey as the Key and the Course Code and Title as the Value
     * @throws IOException if the HTTP request fails
     */
    public HashMap<String, String> getSimpleCourses(String year, Semester semester, String org) throws IOException {
        // Check cache
        if (simpleCourseCache.containsKey(org)){
            return simpleCourseCache.get(org);
        }

        // API_URL/{YEAR}9/course?org={ORG_CODE}
        String url = String.format("%s9/courses?org=%s", year, org);
        JSONObject res = API.request(url);

        // Store the Course Key and Title as KV pairs
        HashMap<String, String> courses = new HashMap<>();
        Iterator<String> keys = res.keys();
        while (keys.hasNext()){
            String key = keys.next();
            JSONObject course = (JSONObject) res.get(key);

            String section = course.getString("section");
            // Store Courses that take place within the selected semester or for both semesters
            if (section.equals(semester.code) || section.equals(Semester.YEAR.code)){
                // Value: Course Code: Course Title
                courses.put(key, String.format("%s: %s", course.get("code"), course.get("courseTitle")));
            }
        }

        // Add to cache
        simpleCourseCache.put(org, courses);

        return courses;
    }

    /**
     * Build a Course object using key looked up from a SimpleCourse API Call
     * @param key Course key used to identify course in response
     * @param rmp the option to lookup RateMyProffesor score
     * @return a Course object of the course looked up
     * @throws IOException if the HTTP request fails
     */
    public Course getCourse(String key, boolean rmp) throws IOException {
        // API_URL/{YEAR}9/course?code={CODE}
        String url = String.format("%s9/courses?code=%s", key.substring(11, 15), key.substring(0, 8));
        JSONObject res = API.request(url).getJSONObject(key);

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

            // Find instructor name
            String instructor = "";
            // Check if instructor value is not empty array
            if (meeting.get("instructors") instanceof JSONObject){
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
            while(sessions.hasNext()) {
                String sessionKey = sessions.next();
                JSONObject session = sessionsObject.getJSONObject(sessionKey);

                // Create a new Session object and push it onto the cached Meeting
                courseBuilder.newSession(
                        session.getString("meetingDay"),
                        session.getString("meetingStartTime"),
                        session.getString("meetingEndTime"),
                        session.getString("assignedRoom1"),
                        rmp
                );
                courseBuilder.pushSession();
            }
            // Push the Meeting object into the cached Course object
            courseBuilder.pushMeeting();
        }

        return courseBuilder.build();
    }

    /**
     * Enum to store Semester and Semester Code
     */
    public enum Semester {
        FALL("F"),
        SPRING("S"),
        YEAR("Y");

        private final String code;

        Semester(String code) {
            this.code = code;
        }
    }
}

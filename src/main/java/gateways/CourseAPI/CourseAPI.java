package gateways.CourseAPI;

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
abstract public class CourseAPI {
    protected final CourseBuilder courseBuilder = new CourseBuilder();
    protected final HashMap<String, HashMap<String, String>> cache = new HashMap<>();

    /**
     * Make an HTTP Get request to the API and return the data as a JSONObject
     * @param url API endpoint and params
     * @return JSON object containing the response
     * @throws IOException if the HTTP request fails
     */
    protected static JSONObject request(String url) throws IOException {
        // Create a URL object and make a GET request
        URL api = new URL(url);
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
        String res = content.toString();
        if (res.equals("[]")){
            res = "{}";
        }
        return new JSONObject(res);
    }

    /**
     * Look up Courses names to display to the user in the API
     * or local cache. Get Course key and Course Title in a HashMap and cache the result
     * @param year is the year when the course takes place
     * @param semester relevant Semester enum
     * @param org Course organisation e.g. CSC
     * @return a HashMap with the CourseKey as the Key and the Course Code and Title as the Value
     * @throws IOException if the HTTP request fails
     */
    public abstract HashMap<String, String> getNames(String year, Semester semester, String org) throws IOException;

    /**
     * Build a Course object using key looked up from a SimpleCourse API Call
     * @param key Course key used to identify course in response
     * @param rmp the option to lookup RateMyProfessor score
     * @return a Course object of the course looked up
     * @throws IOException if the HTTP request fails
     */
    public abstract Course getCourse(String key, boolean rmp) throws IOException;

    /**
     * Enum to store Semester and Semester Code
     */
    public interface Semester {
        boolean match(String code);
    }
}

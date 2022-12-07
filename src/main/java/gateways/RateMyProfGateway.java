package gateways;

import org.json.JSONArray;
import org.json.JSONObject;
import use_cases.RateMyProfSorter.RateMyProfGatewayAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * A gateway for the RateMyProf API that has public static methods for getting the number of professors associated
 * with UofT on the RateMyProf database, the number of pages of professors, and the professors mapped with their score
 * provided that their score is a defined number
 */
public class RateMyProfGateway implements RateMyProfGatewayAccessInterface {
    private final Map<String, Double> professorMapping;

    /**
     * An object representing the Gateway for the RateMyProf API which stores the mapping from professors to their score
     * in a private instance attribute
     * @throws IOException if the HTTP request fails
     */
    public RateMyProfGateway() throws IOException {
        int numberPages = getNumberPages();
        this.professorMapping = getProfessors(numberPages);
    }

    /**
     * A getter for the professor mapping generated from the RateMyProf gateway
     * @return a mapping from professors to their RateMyProf score, if it exists
     */
    @Override
    public Map<String, Double> getProfessorMapping() {
        return professorMapping;
    }

    /**
     * Make an HTTP Get request to the RateMyProf database and return the data as a JSONObject
     * @param page the page to query from the RateMyProf database
     * @return JSON object containing the response
     * @throws IOException if the HTTP request fails
     */
    public static JSONObject request(String page) throws IOException {
        // Create a URL object and make a GET request
        String url = String.format("https://www.ratemyprofessors.com/filter/professor/?&page=%s&filter=teacherlastname_sort_s+asc&query=*%%3A*&queryoption=TEACHER&queryBy=schoolId&sid=1484", page);
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
        return new JSONObject(content.toString());
    }

    /**
     * Find the number of professors currently stored by rate my prof
     * @return the number of professors as an int
     */
    public static int getNumberProfessors() throws IOException {
        return (int) request("1").get("searchResultsTotal");
    }

    /**
     * Calculate the maximum number of pages of professors in the UofT RateMyProf database using the number of professors
     * @return an int of the number of pages
     * @throws IOException if the HTTP request fails
     */
    public static int getNumberPages() throws IOException {
        return (getNumberProfessors() / 20) + 1;
    }

    /**
     * Return a HashMap mapping professors to their RateMyProf score as a Double
     * The keys are of String type and follow the format: "[first name] [last name], [department]"
     * @param pages the number of pages to check for professors (conservative overestimate)
     * @return HashMap mapping professors to their RateMyProf score as a Double
     * @throws IOException if the HTTP request fails
     */
    public static Map<String, Double> getProfessors(int pages) throws IOException {
        HashMap<String, Double> professors = new HashMap<>();

        // iterate through each page
        for (int i = 1; i <= pages; i++) {
            JSONObject data = request(String.valueOf(i));
            JSONArray professor_list = data.getJSONArray("professors");

            // iterate through the JSONArray of professors and add each professor to the mapping
            int list_length = professor_list.length();
            for (int j = 0; j < list_length; j++) {
                JSONObject professor = professor_list.getJSONObject(j);
                String last_name = (String) professor.get("tLname");
                String first_name = (String) professor.get("tFname");
                String score = (String) professor.get("overall_rating");
                String department = (String) professor.get("tDept");
                if (!score.equals("N/A")) {
                    professors.put(first_name.charAt(0) + " " + last_name, Double.parseDouble(score));
                }
            }
        }
        return professors;
    }
}

package gateways;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


public class RateMyProf {
    private static Map<String, String> professors;


    private static JSONObject request(String page) throws IOException {
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
    private static int getNumberProfessors() throws IOException {
        return (int) request("1").get("searchResultsTotal");
    }

    private static int getNumberPages() throws IOException {
        return (getNumberProfessors() / 20) + 1;
    }

    /**
     * Return a HashMap mapping professors to their RateMyProf score as a Double
     * The keys are of String type and follow the format: "[first name] [last name], [department]"
     * @param pages
     * @return
     * @throws IOException
     */
    private static Map<String, Double> getProfessors(int pages) throws IOException {
        HashMap<String, Double> professors = new HashMap<>();

        // iterate through each page
        for (int i = 0; i <= pages; i++) {
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
                    professors.put(first_name + " " + last_name + ", " + department, Double.parseDouble(score));
                }
            }
        }
        return professors;
    }


    public static void main(String[] args) throws IOException {
        JSONObject data = request("199");
        System.out.println(data);
        System.out.println(getNumberProfessors());
        System.out.println(getNumberPages());
        JSONArray profs = data.getJSONArray("professors");
        System.out.println(profs.length());
        System.out.println(getProfessors(getNumberPages()));

    }
}

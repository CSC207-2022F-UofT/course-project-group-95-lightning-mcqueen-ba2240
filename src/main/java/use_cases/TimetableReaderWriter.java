package use_cases;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Timetable;

import java.io.File;

import java.io.IOException;
import java.util.*;

/**
 * Saves Timetable as .json file and returns as a List
 */
public class TimetableReaderWriter {

    // Creates an instance of type ObjectMapper and stored in variable JsonSave in order to use the functionality
    // of Jackson, to write and read Json
    private static final ObjectMapper JsonSave = new ObjectMapper();
    private static String filePath = "./data.json";

    /**
     * Converts the given Timetable ArrayList to a JSON Object and saves JSON Object to a .JSON file
     * @param timetable ArrayList of Timetable's to be saved
     */
    public void saveToJson(ArrayList<Timetable> timetable) {
        try {
            JsonSave.writeValue(new File(filePath), timetable);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads the saved JSON file and takes json to ArrayList
     * @return List of Timetables
     */
    public List<Timetable> readFromJson() {
        try {
            // Obtaining existing file as new File(filePath)
            Timetable[] timetables = JsonSave.readValue(new File(filePath), Timetable[].class);
            // Converts from Array of timetables to list of timetables
            return Arrays.asList(timetables);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}







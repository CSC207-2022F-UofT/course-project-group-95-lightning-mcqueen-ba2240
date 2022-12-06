package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.base.Timetable;


import java.io.File;

import java.io.IOException;
import java.util.*;

/**
 * Saves Timetable as .json file and returns as a List
 */
public class TimetableReaderWriter {

    // Creates an instance of type ObjectMapper and stored in variable JsonSave in order to use the functionality
    // of Jackson, to write and read JSON
    private static final ObjectMapper JsonSave = new ObjectMapper();
    private static final String FILE_PATH = "./data.json";

    /**
     * Converts the given Timetable ArrayList to a JSON Object and saves JSON Object to a .JSON file
     * @param timetable ArrayList of Timetable's to be saved
     */
    public void saveToJson(List<Timetable> timetable) {
        try {
            JsonSave.writeValue(new File(FILE_PATH), timetable);
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
            Timetable[] timetables = JsonSave.readValue(new File(FILE_PATH), Timetable[].class);
            // Converts from Array of timetables to list of timetables
            return Arrays.asList(timetables);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

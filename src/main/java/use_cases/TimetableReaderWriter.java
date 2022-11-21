package use_cases;

import entities.Timetable;
import org.json.JSONArray;

import java.io.*;
import java.util.ArrayList;




public class TimetableReaderWriter {

    private static final int TAB = 4;
    private static String filePath = "./data.json";

    public void saveToJson(ArrayList<Timetable> timetable) {

        JSONArray jarray = new JSONArray(timetable.toArray());
        try {
            FileWriter writer = new FileWriter(new File(filePath));
            writer.write(jarray.toString(TAB));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}







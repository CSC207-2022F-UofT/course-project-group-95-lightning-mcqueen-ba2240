package use_cases.persistance;

import entities.TimetableReaderWriter;
import presenters.ErrorWindow;

public class PersistenceInteractor implements PersistenceInputBoundary{

    @Override
    public void save(PersistenceDataModel request) {
        TimetableReaderWriter writer = new TimetableReaderWriter();
        try{
            writer.saveToJson(request.getTimetables());
        } catch (Exception e){
            ErrorWindow.callError("Failed to save", "Unable to save locally");
        }
    }

    @Override
    public PersistenceDataModel load() {
        TimetableReaderWriter reader = new TimetableReaderWriter();
        try {
            return new PersistenceDataModel(reader.readFromJson());
        } catch (Exception e){
            ErrorWindow.callError("Failed to load", "Unable to load locally");
        }
        return null;
    }
}

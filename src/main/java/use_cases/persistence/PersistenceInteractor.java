package use_cases.persistence;

import entities.TimetableReaderWriter;
import presenters.ErrorWindow;

/**
 * The PersistenceInteractor class is an implementation of the PersistenceInputBoundary interface.
 * It provides the functionality to save and load a PersistenceDataModel object to and from local storage.
 */
 public class PersistenceInteractor implements PersistenceInputBoundary{

    /**
     * Saves a given PersistenceDataModel object to local storage.
     * @param request the PersistenceDataModel object to be saved
     */
    @Override
    public void save(PersistenceDataModel request) {
        TimetableReaderWriter writer = new TimetableReaderWriter();
        try{
            writer.saveToJson(request.getTimetables());
        } catch (Exception e){
            ErrorWindow.callError("Failed to save", "Unable to save locally");
        }
    }

    /**
     * Loads a PersistenceDataModel object from local storage.
     * @return the PersistenceDataModel object loaded from local storage
     */
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

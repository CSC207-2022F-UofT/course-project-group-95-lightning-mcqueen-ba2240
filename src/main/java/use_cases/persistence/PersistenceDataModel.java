package use_cases.persistence;

import entities.base.Timetable;

import java.util.List;

/**
 * The PersistenceDataModel class is a container for a list of Timetable objects.
 */
public class PersistenceDataModel {
    List<Timetable> timetables;

    /**
     * Constructs a PersistenceDataModel with a given list of Timetable objects.
     * @param timetables the list of Timetable objects to be stored in this PersistenceDataModel
     */
    public PersistenceDataModel(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    /**
     * Returns the list of Timetable objects stored in this PersistenceDataModel.
     * @return the list of Timetable objects stored in this PersistenceDataModel
     */
    public List<Timetable> getTimetables() {
        return timetables;
    }

    /**
     * Sets the list of Timetable objects to be stored in this PersistenceDataModel.
     * @param timetables the list of Timetable objects to be stored in this PersistenceDataModel
     */
    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}

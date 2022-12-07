package use_cases.persistance;

public interface PersistenceInputBoundary {
    void save(PersistenceDataModel request);

    PersistenceDataModel load();
}

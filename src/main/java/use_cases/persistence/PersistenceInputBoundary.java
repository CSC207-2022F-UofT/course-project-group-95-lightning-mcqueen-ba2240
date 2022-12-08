package use_cases.persistence;

public interface PersistenceInputBoundary {
    void save(PersistenceDataModel request);

    PersistenceDataModel load();
}

package DomainLayer.EM.Repositories;

import DataLayer.Dao;
import DomainLayer.EM.Preferences;
import DataLayer.EM.PreferencesDao;
import DomainLayer.Repository;

import java.util.List;

public class PreferencesRepository implements Repository<Preferences> {
    private static PreferencesRepository instance;
    private Dao<Preferences> preferencesDao;

    public PreferencesRepository() {
        this.preferencesDao = new PreferencesDao();
    }

    public static synchronized PreferencesRepository getInstance() {
        if (instance == null) {
            instance = new PreferencesRepository();
        }
        return instance;
    }

    @Override
    public void add(Preferences preferences) {
        preferencesDao.create(preferences);
    }

    @Override
    public void update(Preferences preferences){
        preferencesDao.update(preferences);
    }

    @Override
    public List<Preferences> findAll() {
        return preferencesDao.getAll();
    }

    @Override
    public void delete(Preferences preferences) {
        preferencesDao.delete(preferences);
    }

}

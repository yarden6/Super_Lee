package BuisnessLayer.Repositories;

import BuisnessLayer.Preferences;
import DataLayer.Dao;
import DataLayer.PreferencesDao;

import java.util.List;

public class PreferencesRepository implements Repository<Preferences>{
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

//    public void add(Preferences preferences, int id) {
//        preferencesDao.create(preferences, id);
//    }

    @Override
    public void update(Preferences preferences){
        preferencesDao.update(preferences);
    }

//    public void update(Preferences preferences, int id) {
//        preferencesDao.update(preferences, id);
//    }

    @Override
    public List<Preferences> findAll() {
        return preferencesDao.getAll();
    }

    @Override
    public void delete(Preferences preferences) {
        preferencesDao.delete(preferences);
    }

}

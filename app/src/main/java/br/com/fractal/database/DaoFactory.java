package br.com.fractal.database;

import br.com.fractal.BuildConfig;
import br.com.fractal.application.FractalApplication;

import javax.inject.Inject;

public class DaoFactory {
    private static final String DATABASE_NAME = "DataBase.sqlite";
    private static DaoFactory instance = null;
    private static DaoMaster daoMaster = null;
    private static DaoSession daoSession = null;
    private static String DATABASE_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";

    @Inject
    public DaoFactory() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(FractalApplication.getContext(), DATABASE_NAME, null);
        this.daoMaster = new DaoMaster(openHelper);
        daoSession = new DaoSession(daoMaster);
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public BeerDao getBeerDao() {
        return daoSession.getBeerDao();
    }
}

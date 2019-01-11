package br.com.fractal.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by pasqualini on 14/03/17.
 */

public abstract class AbstractDao<T> {
    private String TAG = AbstractDao.class.getCanonicalName();
    protected SQLiteDatabase db;
    protected DaoMaster.DevOpenHelper devOpenHelper = null;
    protected boolean canClose;

    public AbstractDao(SQLiteDatabase db, DaoMaster.DevOpenHelper devOpenHelper) {
        this.db = db;
        this.devOpenHelper = devOpenHelper;
    }

    public abstract T hasEntity(T entity);

    public abstract String getTablename();

    public abstract long insertEntity(T entity);

    public abstract long insertEntityIfNotExist(T entity);

    public abstract long insertOrReplace(T entity);

    public abstract void insertEntityList(List<T> entityList);

    public abstract boolean updateEntity(T entity);

    public abstract boolean deleteEntity(T entity);

    public abstract List<T> loadAll();

    public SQLiteDatabase getDb() throws SQLException {
        return db;
    }

    public SQLiteDatabase open() throws SQLException {
        if (!db.isOpen()) {
            db = devOpenHelper.getWritableDatabase();
        }

        return db;
    }

    public SQLiteDatabase openForRead() throws SQLException {
        if (!db.isOpen()) {
            db = devOpenHelper.getReadableDatabase();
        }

        return db;
    }

    public boolean setCanClose(boolean canClose) {
        boolean previousCanClose = this.canClose;
        this.canClose = canClose;
        return previousCanClose;
    }

    public boolean isCanClose() {
        return this.canClose;
    }

    public SQLiteDatabase close() throws SQLException {
        if (canClose) {
            if (db.isOpen()) {
                Log.i(TAG, "Closing Database Connection....");
                db.close();
            }
        }
        return db;
    }

    public abstract boolean emptyTable();
}

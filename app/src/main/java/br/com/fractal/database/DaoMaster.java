package br.com.fractal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DaoMaster {

    private SQLiteDatabase database;
    private DevOpenHelper devOpenHelper;
    static final int SCHEMA_VERSION = 1;
    private static File databasePath = null;

    private static String TAG = DaoMaster.class.getSimpleName();

    static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        Log.w(TAG, "createAlltables");

        BeerDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        Log.d("daoMaster", "dropAllTables");

        BeerDao.dropTable(db, ifExists);
    }

    private static String getDatabasePath(Context ctx, String databaseFilename) {
        return ctx.getDatabasePath(databaseFilename).getAbsolutePath();
    }

    static String buildDatabaseFilename(Context ctx, String name) {
        String ret = getDatabasePath(ctx, name);
        return ret;
    }

    DaoMaster(DevOpenHelper devOpenHelper) {
        this.devOpenHelper = devOpenHelper;
        this.database = devOpenHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public DevOpenHelper getDevOpenHelper() {
        return devOpenHelper;
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, buildDatabaseFilename(context, name), null, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, buildDatabaseFilename(context, name), factory, SCHEMA_VERSION);
            databasePath = new File(buildDatabaseFilename(context, name));
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if (!DBExists())
                createAllTables(db, false);
        }

        private boolean DBExists() {
            SQLiteDatabase db = null;

            try {
                String path = databasePath.getAbsolutePath();
                db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
//                db.setLocale(Locale.getDefault());
//                db.setLockingEnabled(true);
//                db.setVersion(SCHEMA_VERSION);

            } catch (SQLiteException e) {
                Log.e("SQLHelper", "Erro ao abrir database", e);
            }

            if (db != null) {
                db.close();
            }

            return db != null;
        }
    }

    public static class DevOpenHelper extends OpenHelper {

        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("daoMaster", "onUpgrade oldVersion:" + oldVersion + " newVersion:" + newVersion);

            switch (oldVersion) {
//                case 1:
//                case 3:
//                    db.execSQL("ALTER TABLE TABLE_ANIMAL ADD COLUMN status INTEGER");
            }

            dropAllTables(db, true);
            createAllTables(db, true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("daoMaster", "onCreate database");
            createAllTables(db, true);
        }
    }

}

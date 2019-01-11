package br.com.fractal.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import br.com.fractal.model.Beer
import java.util.*

class BeerDao(db: SQLiteDatabase, devOpenHelper: DaoMaster.DevOpenHelper) : AbstractDao<Beer>(db, devOpenHelper) {

    companion object {
        val TABLE_NAME = "BEER"
        //COLUMNS
        val COLUMN_ID = "id"
        val COLUMN_NAME = "NAME"

        @JvmStatic
        fun createTable(db: SQLiteDatabase, ifNotExists: Boolean) {
            val constraint = if (ifNotExists) "IF NOT EXISTS " else ""
            db.execSQL(
                "CREATE TABLE " + constraint + BeerDao.TABLE_NAME + " ( " + COLUMN_ID + " INTEGER, "
                        + COLUMN_NAME + " TEXT ) "
            )
        }

        @JvmStatic
        fun dropTable(db: SQLiteDatabase, ifExists: Boolean) {
            val sql = "DROP TABLE " + (if (ifExists) "IF EXISTS " else "") + TABLE_NAME
            db.execSQL(sql)
        }
    }


    override fun hasEntity(entity: Beer): Beer? {
        return if (entity.id != null) hasEntityById(entity.id!!) else null
    }

    fun hasEntityById(id: String): Beer? {
        openForRead()
        var entity: Beer? = null
        val selectClausule = ("SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = " + id)

        val cursor = db.rawQuery(selectClausule, null)
        if (cursor!!.moveToFirst()) {
            entity = Beer()
            readEntity(cursor, entity, 0)
            cursor.close()
        }
        cursor.close()
        close()

        return entity
    }

    override fun insertEntity(entity: Beer): Long {
        open()
        var id: Long = -1
        try {
            id = db.insert(TABLE_NAME, null, populateValues(entity))
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {

        }
        close()
        return id
    }

    override fun insertEntityIfNotExist(entity: Beer): Long {
        if (hasEntity(entity) != null)
            return 0

        open()
        var id: Long = -1
        try {
            id = db.insert(TABLE_NAME, null, populateValues(entity))
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {

        }
        close()
        return id
    }

    override fun insertOrReplace(entity: Beer): Long {
        if (hasEntity(entity) != null) {
            updateEntity(entity)
            return 0
        } else
            return insertEntity(entity)
    }

    override fun getTablename(): String {
        return TABLE_NAME
    }

    private fun populateValues(entity: Beer): ContentValues {
        val cv = ContentValues()
        cv.put(COLUMN_ID, entity.id)
        cv.put(COLUMN_NAME, entity.name)
        return cv
    }

    override fun insertEntityList(entityList: List<Beer>) {
        open()
        try {
            entityList.forEach {
                db.insert(TABLE_NAME, null, populateValues(it))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            close()
        }
    }

    override fun updateEntity(entity: Beer): Boolean {
        open()
        val cv = ContentValues()
        val where = " " + COLUMN_ID + " = " + entity.id
        var v = true
        try {
            cv.put(COLUMN_NAME, entity.name)
            db.update(TABLE_NAME, cv, where, null)
        } catch (ex: Exception) {
            v = false
        } finally {

        }
        return v
    }

    override fun deleteEntity(entity: Beer): Boolean {
        open()
        val where = " " + COLUMN_ID + " = " + entity.id
        var v = true
        try {
            v = db.delete(TABLE_NAME, where, null) >= 0
        } catch (ex: Exception) {
            v = false
        } finally {

        }
        return v
    }

    override fun loadAll(): List<Beer> {
        openForRead()
        var entityList: MutableList<Beer> = mutableListOf()
        val selectClausule = "SELECT * FROM $TABLE_NAME"

        val cursor = db.rawQuery(selectClausule, null)
        if (cursor!!.moveToFirst()) {
            entityList = ArrayList()
            do {
                val entity = Beer()
                readEntity(cursor, entity, 0)
                entityList.add(entity)
            } while (cursor.moveToNext())
            cursor.close()
        }
        cursor.close()
        close()

        return entityList
    }

    fun readEntity(cursor: Cursor, entity: Beer, offset: Int) {
        entity.id = if (cursor.isNull(offset + 0)) null else cursor.getString(offset + 0)
        entity.name = if (cursor.isNull(offset + 1)) null else cursor.getString(offset + 1)

    }


    override fun emptyTable(): Boolean {
        open()
        val where = " 1 = 1 "

        return try {
            db.delete(TABLE_NAME, where, null) >= 0
        } catch (ex: Exception) {
            false
        }
    }

    fun deleteAll() {
        open()
        db.delete(TABLE_NAME, null, null)
    }
}
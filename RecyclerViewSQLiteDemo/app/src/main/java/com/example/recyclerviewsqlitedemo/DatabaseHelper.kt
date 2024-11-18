package com.example.recyclerviewsqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "Users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_AGE INTEGER)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(name: String, age: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_AGE, age)
        }
        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result != -1L
    }

    fun getAllUsers(sorted: Boolean = false): List<User> {
        val userList = mutableListOf<User>()
        val db = this.readableDatabase
        val query = if (sorted) {
            "SELECT * FROM $TABLE_USERS ORDER BY $COLUMN_NAME ASC"
        } else {
            "SELECT * FROM $TABLE_USERS"
        }
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
                userList.add(User(id, name, age))  // Add User object to the list
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }


    fun updateUser(id: Int, newName: String, newAge: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, newName)
            put(COLUMN_AGE, newAge)
        }
        val result = db.update(TABLE_USERS, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return if (result > 0) {
            Log.d("DatabaseHelper", "Update successful for ID: $id")
            true
        } else {
            Log.d("DatabaseHelper", "Update failed for ID: $id")
            false
        }
    }

    fun deleteUser(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_USERS, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
}

package com.rivferd.formapps.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "registration_db"
        const val DB_VERSION = 1
        const val TABLE_NAME = "participant"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_NUMBER = "phone_number"
        const val COLUMN_NAME_GENDER = "gender"
        const val COLUMN_NAME_LOCATION = "location"
        const val COLUMN_NAME_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE $TABLE_NAME (
                $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME_NAME TEXT,
                $COLUMN_NAME_NUMBER TEXT,
                $COLUMN_NAME_GENDER TEXT,
                $COLUMN_NAME_LOCATION TEXT,
                $COLUMN_NAME_IMAGE BLOB
            );
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("""
            DROP TABLE IF EXISTS participant;
        """.trimIndent())
        onCreate(db)
    }
}
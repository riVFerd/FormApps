package com.rivferd.formapps.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class ParticipantModel(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insert(name: String, phoneNumber: String, gender: String, location: String, image: ByteArray) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME_NAME, name)
            put(DatabaseHelper.COLUMN_NAME_NUMBER, phoneNumber)
            put(DatabaseHelper.COLUMN_NAME_GENDER, gender)
            put(DatabaseHelper.COLUMN_NAME_LOCATION, location)
            put(DatabaseHelper.COLUMN_NAME_IMAGE, image)
        }
        dbHelper.writableDatabase.insert(DatabaseHelper.TABLE_NAME, null, values)
    }

    fun selectAll(): Cursor {
         return dbHelper.readableDatabase.rawQuery("""
            SELECT * FROM participant;
        """.trimIndent(), null)
    }

//    Delete all row in database, Only for testing
    fun reset() {
        dbHelper.writableDatabase.execSQL("""
            DELETE FROM participant;
        """.trimIndent())
    }

}
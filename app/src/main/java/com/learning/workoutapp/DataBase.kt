package com.learning.workoutapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "7MinWorkout.db"
        private const val TABLE_HISTORY= "History"
        private const val KEY_ID = "_id"
        private const val KEY_DATE = "completed_data"
    }
    override fun onCreate(db: SQLiteDatabase?) {
//        CREATE TABLE "table_name" (
//            "id"	INTEGER,
//            "date"	TEXT,
//            PRIMARY KEY("id")
//        )
        val createDatabase = ("CREATE TABLE $TABLE_HISTORY ($KEY_ID INTEGER, $KEY_DATE TEXT," +
                "PRIMARY KEY($KEY_ID))")
        db?.execSQL(createDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }
    fun addNewHistory(date: String): Long{
        val contentValues = ContentValues()
        contentValues.put(KEY_DATE, date)
        val db = this.writableDatabase
        val result =  db.insert(TABLE_HISTORY, null, contentValues)
        db.close()
        return result
    }
    @SuppressLint("Recycle")
    fun viewHistory(): ArrayList<History>{
        val dateList = ArrayList<History>()
        var cursor : Cursor? = null
//      SELECT *FROM table_name
        val db = this.writableDatabase
        try{
            cursor = db.rawQuery("SELECT *FROM $TABLE_HISTORY", null)
        }catch (e: Exception){
            e.printStackTrace()
        }
        if(cursor!!.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                dateList.add(History(id, date))
            }while (cursor.moveToNext())
        }
        return dateList
    }
}
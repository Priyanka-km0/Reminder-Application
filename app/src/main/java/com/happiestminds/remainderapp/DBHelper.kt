package com.happiestminds.remainderapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context,"reminder.db",null,1) {

    //to use sqliteOpenHelper, create subclass that overrides oncreate and onupgrade callback methods


    override fun onCreate(db: SQLiteDatabase?) {
        //create tables -  to be executed only once
        db?.execSQL(TABLE_QUERY)
}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //executed when there is version mismatch
        //drop table , create new one , modify schema of existing table

    }

    companion object{
        const val TABLE_NAME = "ReminderData"
        const val CLM_TITLE = "title"
        const val CLM_DESC = "description"
        const val CLM_DATE = "date"
        const val CLM_TIME = "time"
        private const val TABLE_QUERY = "create table $TABLE_NAME ($CLM_TITLE text , $CLM_DESC text, $CLM_DATE text,$CLM_TIME text)"
    }
}
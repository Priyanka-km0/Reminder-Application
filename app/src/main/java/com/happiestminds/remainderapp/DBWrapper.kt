package com.happiestminds.remainderapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.icu.text.CaseMap.Title
import android.provider.CalendarContract.Reminders
import java.sql.Date
import java.sql.Time

class DBWrapper(ctx : Context) {
    val helper = DBHelper(ctx)
    //get the data repository in write mode
    val db = helper.writableDatabase

    fun addReminder(rem: ReminderList): Boolean {
        //insert
  //      put information into the database
        val values = ContentValues()
        values.put(DBHelper.CLM_TITLE, rem.title)
        values.put(DBHelper.CLM_DESC, rem.desc)
        values.put(DBHelper.CLM_DATE, rem.date)
        values.put(DBHelper.CLM_TIME, rem.time)

//insert  a row : db.insert

        val rowid = db.insert(DBHelper.TABLE_NAME, null, values)
        if (rowid.toInt() == -1) {
            return false
        }
        return true
    }
    //query
    fun getAllReminder(): Cursor {
            //query
            val clms = arrayOf(DBHelper.CLM_TITLE,DBHelper.CLM_DESC,DBHelper.CLM_DATE,DBHelper.CLM_TIME)
            return db.query(DBHelper.TABLE_NAME,clms,null,null,null,null,null)

    }

    fun deleteReminder(rem: String){
        //delete
        db.delete(DBHelper.TABLE_NAME,"${DBHelper.CLM_TITLE} = ?", arrayOf(rem))
    }
    }










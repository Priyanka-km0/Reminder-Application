package com.happiestminds.remainderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast


class ListActivity : AppCompatActivity() {

    lateinit var listReminderView: ListView
    lateinit var adapter:ArrayAdapter<ReminderList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listReminderView = findViewById(R.id.listviewReminder)

        val title = intent.getStringExtra("titles")
        val desc = intent.getStringExtra("desc")
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")

        //adapter
        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listReminder)
        listReminderView.adapter = adapter

        setup()

        //display of dialog box

        listReminderView.setOnItemClickListener {adapter, view, position, id ->
            val dlg = MyDialog()
            val selectedPosition = listReminder[position]
            dlg.isCancelable = false

            //bundle passes data/arguments btw destination using bundle object
            val dataBundle = Bundle()
            dataBundle.putString("title","${selectedPosition.title}")
            dataBundle.putString("msg", """
                |title :${selectedPosition.title}
                |Description: ${selectedPosition.desc}
                |date: ${selectedPosition.date}
                |time: ${selectedPosition.time}
            """.trimMargin())
            dlg.arguments = dataBundle
            dlg.show(supportFragmentManager, null)
        }
    }

    //to get all the reminders
    private fun setup() {
        val cursor = DBWrapper(this).getAllReminder()
        if(cursor.count>0){
            val idx_title = cursor.getColumnIndexOrThrow(DBHelper.CLM_TITLE)
            val idx_desc = cursor.getColumnIndexOrThrow(DBHelper.CLM_DESC)
            val idx_time = cursor.getColumnIndexOrThrow(DBHelper.CLM_TIME)
            val idx_date = cursor.getColumnIndexOrThrow(DBHelper.CLM_DATE)

           listReminder.clear()
            //move the cursor to the first row - moveToFirst()
            cursor.moveToFirst()
            do {
                val title = cursor.getString(idx_title)
                val desc = cursor.getString(idx_desc)
                val time = cursor.getString(idx_time)
                val date = cursor.getString(idx_date)

                val rem = ReminderList(title,desc,date,time)
                //fetching from database and sending  to list
                listReminder.add(rem)
            }while(cursor.moveToNext())

            adapter.notifyDataSetChanged()

            Log.d("ListActivity","List:$listReminder")
            Toast.makeText(this,"Found:${listReminder.count()}",Toast.LENGTH_LONG).show()
        }
    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}





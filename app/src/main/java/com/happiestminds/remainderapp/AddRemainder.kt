package com.happiestminds.remainderapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.graphics.Insets.add
import androidx.core.view.isVisible
import java.text.SimpleDateFormat
import java.util.*

class AddRemainder : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    lateinit var timeTextView: TextView
    lateinit var dateTextView: TextView

    lateinit var dateButton: Button
    lateinit var timeButton: Button

    lateinit var titleEdittext: EditText
    lateinit var descEditText: EditText

    lateinit var calendarButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_remainder)

        timeButton = findViewById(R.id.timeB)
        dateButton = findViewById(R.id.dateB)
        timeTextView = findViewById(R.id.timeT)
        dateTextView = findViewById(R.id.dateT)
        titleEdittext = findViewById(R.id.titleT)
        descEditText = findViewById(R.id.descT)
        calendarButton = findViewById(R.id.calendarB)
    }

    fun buttonClick(view: View) {
        val dlg = DatePickerDialog(this)
        dlg.setOnDateSetListener { dPicker, year, month, day ->
            dateTextView.text = "$day-${month + 1}-$year"
        }
        dlg.show()

    }

    fun timeClicked(view: View) {
        //the listener to call when time is set
        val dlg = TimePickerDialog(this, this, 10, 0, true)
        dlg.show()
    }

    override fun onTimeSet(p0: TimePicker?, hh: Int, mm: Int) {
        timeTextView.text = "$hh:$mm"
        timeButton.isVisible = true
    }
    @SuppressLint("SuspiciousIndentation")
    fun submitClick(view: View) {
        when (view.id) {
            R.id.SubmitB -> {
                val titles = titleEdittext.text.toString()
                val descriptions = descEditText.text.toString()
                val date = dateTextView.text.toString()
                val time = timeTextView.text.toString()

                Log.d("AddReminder", "title: $titleEdittext, description: $descEditText, " +
                        "time: $timeTextView,date:$dateTextView,")
                if(titles.isEmpty()){
                    titleEdittext.error = "Title is mandatory"
                }
                if(descriptions.isEmpty()){
                    descEditText.error = "Add Description"
                }
                if(date.isEmpty()){
                    dateTextView.error = "Set Date"
                }
                if(time.isEmpty()){
                    timeTextView.error = "Set Time"
                }

                if (titles.isNotEmpty() && descriptions.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                    Toast.makeText(this,
                        "You entered title: $titles, desc: $descriptions , date : $date, time: $time",
                        Toast.LENGTH_LONG).show()
                    //launch next activity

                    //add new reminder
                    var newReminder = ReminderList(titleEdittext.text.toString(),
                        descEditText.text.toString(),
                        dateTextView.text.toString(),

                        timeTextView.text.toString())
                    listReminder.add(newReminder)

                    //add reminder

                    if (DBWrapper(this).addReminder(newReminder)) {
                        Toast.makeText(this, "$newReminder", Toast.LENGTH_LONG).show()
                    } else
                        Toast.makeText(this, "Reminder NOT Added", Toast.LENGTH_LONG).show()
                    val showIntent = Intent(this, ListActivity().javaClass)
                    showIntent.putExtra("Title", titleEdittext.text.toString())
                    startActivity(showIntent)
                }
                else {
                    Toast.makeText(this, "Please Enter all data",
                        Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    fun clearClick(view: View) {
        Log.d("AddRemainder", "Clear clicked..")
        timeTextView.text = ""
        dateTextView.text = ""
        titleEdittext.setText("")
        descEditText.setText("")
    }

    @SuppressLint("SimpleDateFormat")
    fun calenderBtnClick(view: View) {
        if (titleEdittext.text.toString().isNotEmpty() && timeTextView.text.toString().isNotEmpty() &&dateTextView.text.toString().isNotEmpty()
        ) {


             var dataReceived = ReminderList(
                titleEdittext.text.toString(),
                descEditText.text.toString(),
                timeTextView.text.toString(),
                dateTextView.text.toString()
          )
             //AddRemainder.add(dataReceived)
            val dateString = "${dataReceived.time} ${dataReceived.date}"
            Log.d("Add reminder", "Date: $dateString")

            val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
            val date = format.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            Log.d("Add reminder", "milli: ${cal.timeInMillis}")

            var value = ContentValues();


            value.put(CalendarContract.Events.DTSTART, cal.timeInMillis)
            value.put(CalendarContract.Events.DTEND, cal.timeInMillis + 60 * 1000);
            value.put(CalendarContract.Events.TITLE, dataReceived.title);
            value.put(CalendarContract.Events.DESCRIPTION, dataReceived.desc);
            value.put(CalendarContract.Events.CALENDAR_ID, 1);
            value.put(CalendarContract.Events.EVENT_TIMEZONE, "IST")
            value.put(CalendarContract.Events.HAS_ALARM, 1)


            var id1: Int
            var alarmid: Int
            var attendeesUri = Uri.parse("content://com.android.calendar/attendees")

            var uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, value);
            Log.d("Add Reminder", "calenderBtnClick:  $uri")
            val evenID = uri?.lastPathSegment?.toInt()

            Toast.makeText(this, " Added to Calendar", Toast.LENGTH_SHORT).show();
            //reminder insert
            val cr: ContentResolver = contentResolver

            var values = ContentValues();
            values.put(CalendarContract.Reminders.EVENT_ID, evenID);
            values.put(
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.METHOD_DEFAULT
            );
            values.put(
                CalendarContract.Reminders.MINUTES,
                CalendarContract.Reminders.MINUTES_DEFAULT
            );
            val reminderUri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
            Log.d("Add reminder", "reminder uri: $reminderUri")
        }
   }

}


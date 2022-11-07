package com.happiestminds.remainderapp


import android.content.Intent
import android.content.pm.PackageManager
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View



class MainActivity : AppCompatActivity() {
    //onCreate - called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val MENU_EXIT = 2

    //for adding the options - optionMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //design menu by adding few menu items
        menu?.add(2,MENU_EXIT,3,"Exit")

        return super.onCreateOptionsMenu(menu)
    }

    //this method passes the menu item selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }



    fun buttonClick(view: View) {
  //explicit intent - within the application, redirects to next activity
        val addReminderIntent = Intent(this,AddRemainder().javaClass)
        startActivity(addReminderIntent)


    }

    fun remainderClick(view: View) {
        val showListIntent = Intent(this,ListActivity().javaClass)
        startActivity(showListIntent)
    }

    //uses permission for calendar
    override fun onResume() {
        if (checkSelfPermission(Manifest.permission.READ_CALENDAR)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR),1)
        }
        super.onResume()
    }
}

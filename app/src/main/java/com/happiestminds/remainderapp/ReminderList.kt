package com.happiestminds.remainderapp

var listReminder = mutableListOf<ReminderList>()

data class ReminderList(var title: String, var desc: String, var date: String, var time:String){

    override fun toString(): String {
        return "$title"
    }
}




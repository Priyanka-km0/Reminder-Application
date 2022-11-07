package com.happiestminds.remainderapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dlg : Dialog? = null

        //retreive bundle
        val message = arguments?.getString("msg")
       // val position = (arguments?.getString("pos"))?.toInt()

        val title = arguments?.getString("title")

//use builder class for convenient dialog construction
        //let - scope function : returns : lambda result, context object : it
        //create dialog here
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Do you want to delete")
            builder.setMessage(message)
            builder.setPositiveButton("YES"){dialog,i ->

                //deleting title
                DBWrapper(requireContext()).deleteReminder(title!!)
                activity?.finish()

//alert dialog box which can have maximum of  3 buttons
            }
            builder.setNegativeButton("NO"){ dialog, i ->     dialog.cancel()
            }
            builder.setNeutralButton("cancel"){dialog, i ->   dialog.cancel()
            }
            //create an alert dialog box and return it
            dlg = builder.create()
        }
        return dlg!!
    }
}


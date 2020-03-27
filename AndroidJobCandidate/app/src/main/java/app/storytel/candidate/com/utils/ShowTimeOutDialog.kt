package app.storytel.candidate.com.utils

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity

fun ShowAlertDialog(context:Context, call:Unit): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(context)

    dialogBuilder.setMessage("We're having issue loading the data. ")
            .setCancelable(false)
            .setPositiveButton("Try Again", DialogInterface.OnClickListener { _, _ ->
                call
            })

    val alert = dialogBuilder.create()
    alert.setTitle("Something is wrong")
    return alert
}
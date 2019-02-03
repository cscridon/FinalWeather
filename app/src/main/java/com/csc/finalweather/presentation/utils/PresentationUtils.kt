package com.csc.finalweather.presentation.utils

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.Toast
import android.app.Activity
import com.csc.finalweather.R


const val LOADING_STATE = "LOADING_STATE" //view load state

fun showToastMessage(context: Context, message:String){
     Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
 }

fun showAlertDialog(context:Context?,dialogBuilder: AlertDialog.Builder.() -> Unit) {
    if (context!=null){
        val builder = AlertDialog.Builder(context)
        builder.dialogBuilder()
        val dialog = builder.create()
        dialog.show()
    }
}

fun AlertDialog.Builder.positiveButton(text: String = "Ok", handleClick: (which: Int) -> Unit = {}) {
    this.setPositiveButton(text, { dialogInterface, which-> handleClick(which) })
}

fun AlertDialog.Builder.negativeButton(text: String = "Cancel", handleClick: (which: Int) -> Unit = {}) {
    this.setNegativeButton(text, { dialogInterface, which-> handleClick(which) })
}

fun checkIfTablet(activity: Activity):Boolean{
    val isTablet = activity.getResources().getBoolean(R.bool.isTablet)
    return isTablet
}
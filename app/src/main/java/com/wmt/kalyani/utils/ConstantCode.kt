package com.wmt.kalyani.utils

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.wmt.kalyani.R

object ConstantCode {

    val BASE_URL = "http://68.183.48.101:3333/users/"

    fun savesharedpreference(context: Context?, key: String?, value: String?) {
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getsharedpreference(context: Context?, key: String?, defaultvalue: String?): String? {
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val value: String? = sharedPreferences.getString(key, defaultvalue)
        return value
    }
    fun ShowpopupDialoge(context: Context?, msg: String?) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage(msg)
        alertDialog.setButton(
            "OK"
        ) { dialog, whichButton -> }
        alertDialog.show()
    }


}
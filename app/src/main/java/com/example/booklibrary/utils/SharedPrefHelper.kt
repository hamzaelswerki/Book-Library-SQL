package com.example.booklibrary.utils

import android.content.Context

class SharedPrefHelper {


    companion object{

        val MY_PREFERENCES="MY_PREFEREN"

    fun getEmail(context: Context): String? {
        val sharedpreferences =
            context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
        return sharedpreferences.getString("email", "null")
    }

    fun setEmail(context: Context, email: String?) {
        val sharedpreferences =
            context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }


}
}
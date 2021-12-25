package com.example.booklibrary.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.R
import com.example.booklibrary.ui.fragments.SplashFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.frame, SplashFragment()).commit()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
          super.onOptionsItemSelected(item)
        return false;
    }
}
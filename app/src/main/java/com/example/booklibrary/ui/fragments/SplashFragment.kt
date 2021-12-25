package com.example.booklibrary.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.booklibrary.R
import com.example.booklibrary.utils.SharedPrefHelper

class SplashFragment : Fragment() {
    private val SPLASH_DISPLAY_LENGTH = 650


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    openActivity()
    }

    fun openActivity() {
        Handler().postDelayed({

            if (SharedPrefHelper.getEmail(context!!)?.equals("null")!!) {
                fragmentManager!!.beginTransaction().replace(R.id.frame, LoginFragment()).commit()
            }else{
                fragmentManager!!.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
            }

        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    override fun onSaveInstanceState(outState: Bundle) {
    }
}
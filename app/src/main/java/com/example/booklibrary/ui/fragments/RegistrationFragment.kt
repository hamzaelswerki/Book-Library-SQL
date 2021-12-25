package com.example.booklibrary.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.booklibrary.dataBase.DataBaseHelper
import com.example.booklibrary.R
import com.example.booklibrary.utils.SharedPrefHelper
import com.example.booklibrary.model.User
import com.example.booklibrary.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private lateinit var databaseHelper: DataBaseHelper


    lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=  FragmentRegistrationBinding.inflate(layoutInflater)
        return  binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())
        binding.appCompatButtonRegistration.setOnClickListener {


            if (!TextUtils.isEmpty(binding.textInputEditTextEmail.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextEmail.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextName.text)

            ){

                if (!databaseHelper!!.checkUser(binding.textInputEditTextEmail!!.text.toString().trim())) {
                    var user = User(name = binding.textInputEditTextName!!.text.toString().trim(),
                        email = binding.textInputEditTextEmail!!.text.toString().trim(),
                        password = binding.textInputEditTextPassword!!.text.toString().trim(), img = "no_img")

                    if (  databaseHelper!!.insertUser(user)){
                        Toast.makeText(requireContext(),"added successfully",Toast.LENGTH_SHORT).show()
                        SharedPrefHelper.setEmail(requireContext(), binding.textInputEditTextEmail.text.toString())
                        fragmentManager!!.beginTransaction().replace(R.id.frame, HomeFragment()).
                        addToBackStack("").commit()
                    }else{
                        Toast.makeText(requireContext(),"failed add",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(),"failed",Toast.LENGTH_SHORT).show()
                }


            }else{
                Toast.makeText(requireContext(),"fields is empty",Toast.LENGTH_SHORT).show()

            }

        }

    }



}
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
import com.example.booklibrary.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var databaseHelper: DataBaseHelper


    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=  FragmentLoginBinding.inflate(layoutInflater)
        return  binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())
        binding.appCompatButtonLogin.setOnClickListener {


            if (!TextUtils.isEmpty(binding.textInputEditTextEmail.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextPassword.text)){

            if (databaseHelper.checkUser(binding.textInputEditTextEmail.text.toString().trim { it <= ' ' },
                    binding.textInputEditTextPassword.text.toString().trim { it <= ' ' })) {
                SharedPrefHelper.setEmail(requireContext(), binding.textInputEditTextEmail.text.toString())

                fragmentManager!!.beginTransaction().replace(R.id.frame, HomeFragment()).addToBackStack("").commit()

            } else {
                    Toast.makeText(requireContext(),"error in email or password",Toast.LENGTH_SHORT).show()

            }
            }else{
                Toast.makeText(requireContext(),"fields is empty",Toast.LENGTH_SHORT).show()

            }

        }

        binding.textViewLinkRegister.setOnClickListener {

            fragmentManager!!.beginTransaction().replace(R.id.frame,
                RegistrationFragment()).addToBackStack("").commit()
        }

    }



}
package com.example.booklibrary.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.booklibrary.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var databaseHelper: DataBaseHelper

    lateinit var binding: FragmentProfileBinding

    var img=""
     var userId=-1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())

        binding.imgPick.setOnClickListener {
            val gallary=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallary,100)
        }

        val user=databaseHelper.getUser(SharedPrefHelper.getEmail(requireContext()))
                binding.textInputEditTextName.setText(user.name)
                binding.textInputEditTextEmail.setText(user.email)
        if (!user.img.equals("no_img")) {
            binding.imgPick.setImageURI(Uri.parse(user.img))
        }

        userId=user.id
        binding.updateBtn.setOnClickListener {

            if (!TextUtils.isEmpty(binding.textInputEditTextEmail.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextEmail.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextName.text)){

                var user = User(
                    id = userId,
                    name = binding.textInputEditTextName!!.text.toString().trim(),
                    email = binding.textInputEditTextEmail!!.text.toString().trim(),
                    password = binding.textInputEditTextPassword!!.text.toString().trim(),
                    img = img
                )

                if (  databaseHelper!!.updateUser(user)){
                    Toast.makeText(requireContext(),"update successfully", Toast.LENGTH_SHORT).show()
                    SharedPrefHelper.setEmail(requireContext(), binding.textInputEditTextEmail.text.toString())
                  parentFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).addToBackStack("").commit()

                }else{
                    Toast.makeText(requireContext(),"failed update", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK&&requestCode==100){
            binding.imgPick.setImageURI(data!!.data)
            img=data.data.toString()
        }

    }



}
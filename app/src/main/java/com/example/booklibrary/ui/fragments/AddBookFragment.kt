package com.example.booklibrary.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.booklibrary.dataBase.DataBaseHelper
import com.example.booklibrary.databinding.FragmentAddBookBinding
import com.example.booklibrary.model.Book


class AddBookFragment : Fragment() {


    lateinit var binding: FragmentAddBookBinding
    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddBookBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())


        binding.addBook.setOnClickListener {


           if (checkAllFields())  {
                var book = Book(
                    id = -1,
                    name = binding.textInputEditTextName!!.text.toString().trim(),
                    author = binding.textInputEditTextAuthor!!.text.toString().trim(),
                    year = binding.textInputEditTextyear!!.text.toString().trim(),

                    category = binding.textInputEditTextcategory!!.text.toString().trim(),
                    description = binding.textInputEditTextdescription!!.text.toString().trim(),
                    language = binding.textInputEditTextlanguage!!.text.toString().trim(),

                    number_of_pages = binding.textInputEditTextnumberofpages!!.text.toString()
                        .trim(),
                    copies_number = binding.textInputEditTextcopiesnumber!!.text.toString().trim(),
                    shelf = binding.textInputEditTextshelf!!.text.toString().trim(),
                    isFav = "false",
                    rental_date = "",

                    )


                if (  databaseHelper!!.insertBook(book)){
                    Toast.makeText(requireContext(), "added successfully", Toast.LENGTH_SHORT).show()
                    SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("added successfully")
                        .show()
                }else{
                    SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("added Faild")
                        .show()
                }

             }else{
                 Toast.makeText(requireContext(), "fields is empty", Toast.LENGTH_SHORT).show()

             }
        }
    }
    fun checkAllFields():Boolean{
        return    !TextUtils.isEmpty(binding.textInputEditTextName.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextAuthor.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextyear.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextcategory.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextdescription.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextlanguage.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextnumberofpages.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextcopiesnumber.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextshelf.text)
    }

}
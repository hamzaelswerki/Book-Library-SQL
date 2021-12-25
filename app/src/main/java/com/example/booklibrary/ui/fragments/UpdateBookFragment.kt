package com.example.booklibrary.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.booklibrary.dataBase.DataBaseHelper
import com.example.booklibrary.databinding.FragmentUpdateBookBinding
import com.example.booklibrary.model.Book
import java.util.*


class UpdateBookFragment(val book: Book) : Fragment() {


    lateinit var binding: FragmentUpdateBookBinding
    private lateinit var databaseHelper: DataBaseHelper

    var isFav="false"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUpdateBookBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DataBaseHelper(requireContext())

        binding.textInputEditTextName.setText(book.name)
        binding.textInputEditTextAuthor.setText(book.author)
        binding.textInputEditTextyear.setText(book.year)
        binding.textInputEditTextcategory.setText(book.category)


        binding.textInputEditTextdescription.setText(book.description)
        binding.textInputEditTextlanguage.setText(book.language)
        binding.textInputEditTextnumberofpages.setText(book.number_of_pages)
        binding.textInputEditTextcopiesnumber.setText(book.copies_number)
        binding.textInputEditTextshelf.setText(book.shelf)
        binding.edDate.setText(book.rental_date)

        if (book.isFav.equals("true")){
            binding.checkBoxIsFava.isChecked=true
           isFav=book.isFav
        }else{
            binding.checkBoxIsFava.isChecked=false
            isFav=book.isFav
        }

        binding.edDate.setOnClickListener {
            val currentDate= Calendar.getInstance()
            val day=currentDate.get(Calendar.DAY_OF_MONTH)
            val month=currentDate.get(Calendar.MONTH)
            val year=currentDate.get(Calendar.YEAR)
            val picker=DatePickerDialog(requireContext(),
                    DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                        binding.edDate.setText("$i : $i2 : $i3")
                    },year,month,day)
            picker.show()


        }

        binding.updateBook.setOnClickListener {

           if (
                !TextUtils.isEmpty(binding.textInputEditTextName.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextAuthor.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextyear.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextcategory.text)

                &&!TextUtils.isEmpty(binding.textInputEditTextdescription.text)

                &&!TextUtils.isEmpty(binding.textInputEditTextlanguage.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextnumberofpages.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextcopiesnumber.text)
                &&!TextUtils.isEmpty(binding.textInputEditTextshelf.text))  {

               if (binding.checkBoxIsFava.isChecked){
                   isFav = "true"

               }else{
                   isFav = "false"
               }

                var updateBook = Book(
                    id = book.id,
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
                      isFav=isFav,
                      rental_date= binding.edDate.text.toString().trim()
                    )


                if (  databaseHelper!!.updateBook(updateBook)){
                    SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("updated successfully")
                        .show()
                }else{
                    SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("updated Faild")
                        .show()
                }
             }else{
                 Toast.makeText(requireContext(), "fields is empty", Toast.LENGTH_SHORT).show()

             }
        }
    }

}
package com.example.booklibrary.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booklibrary.adapter.BookAdapter
import com.example.booklibrary.dataBase.DataBaseHelper
import com.example.booklibrary.R
import com.example.booklibrary.utils.SharedPrefHelper
import com.example.booklibrary.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    lateinit var  binding: FragmentFavoriteBinding
    private lateinit var databaseHelper: DataBaseHelper
    lateinit var adapter: BookAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

binding= FragmentFavoriteBinding.inflate(inflater)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())

        binding.toolbar.inflateMenu(R.menu.home_menu);


        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                adapter.getFilter()?.filter(s.toString())
            }
        })


    }

    override fun onResume() {
        super.onResume()
        adapter= BookAdapter(databaseHelper.getFavBooks(), activity!!)
        binding.listBooks.adapter=adapter
        binding.listBooks.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }





    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {

                parentFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment()).addToBackStack("").commit()

                true
            }
            R.id.signOut -> {
                SharedPrefHelper.setEmail(requireContext(), "null")
                parentFragmentManager.beginTransaction().replace(R.id.frame, LoginFragment()).addToBackStack("").commit()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


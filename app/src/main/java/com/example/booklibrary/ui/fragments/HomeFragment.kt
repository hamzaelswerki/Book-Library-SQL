package com.example.booklibrary.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booklibrary.adapter.BookAdapter
import com.example.booklibrary.dataBase.DataBaseHelper
import com.example.booklibrary.R
import com.example.booklibrary.utils.SharedPrefHelper
import com.example.booklibrary.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var  binding: FragmentHomeBinding
    private lateinit var databaseHelper: DataBaseHelper
    lateinit var adapter: BookAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

binding= FragmentHomeBinding.inflate(inflater)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())

        binding.toolbar.inflateMenu(R.menu.home_menu);
        binding.addBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frame,
                    AddBookFragment()).addToBackStack("").commit()
        }

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
        adapter= BookAdapter(databaseHelper.getBooks(""), activity!!)
        binding.listBooks.adapter=adapter
        binding.listBooks.layoutManager=GridLayoutManager(context, 2)
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
            R.id.favorite -> {

                parentFragmentManager.beginTransaction().replace(R.id.frame, FavoriteFragment()).addToBackStack("").commit()

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


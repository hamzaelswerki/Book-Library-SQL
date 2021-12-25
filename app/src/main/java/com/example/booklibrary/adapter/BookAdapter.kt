package com.example.booklibrary.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.booklibrary.dataBase.DataBaseHelper
import com.example.booklibrary.R
import com.example.booklibrary.adapter.BookAdapter.ViewHolder
import com.example.booklibrary.model.Book
import com.example.booklibrary.ui.fragments.UpdateBookFragment
import java.util.*

class BookAdapter(val books: ArrayList<Book>, var homeActivity: FragmentActivity) : RecyclerView.Adapter<ViewHolder>() {

    var booksLists: ArrayList<Book>? = null
    var listFilteredCountries: ArrayList<Book>? = null

    private var fragmentActivity: FragmentActivity? = null
    private var countriesFilter: CountriesFilter? = null

    init {
        booksLists = books
        listFilteredCountries = books
        fragmentActivity = homeActivity
        countriesFilter = CountriesFilter()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_book, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvBookName.text = listFilteredCountries!![position].name
        holder.tvBookAuther.text = listFilteredCountries!![position].author
        holder.tvBookYears.text = listFilteredCountries!![position].year

        holder.imgDelete.setOnClickListener{
            val builder=AlertDialog.Builder(homeActivity)
            builder.setTitle("Delete Book?")
            builder.setMessage("Are you want to Delete?")
            builder.setPositiveButton("Yes"){ _,_ ->
            val db= DataBaseHelper(homeActivity)
                if ( db.deleteBook(listFilteredCountries!![position].id)){

                    listFilteredCountries!!.removeAt(position)
                    notifyDataSetChanged()
                }else{
                    Toast.makeText(homeActivity,"Delete Failed ",Toast.LENGTH_SHORT).show()
                }

            }
            builder.setNegativeButton("No"){
                d,_ ->
                    d.dismiss()
            }
            builder.create().show()


        }
        holder.itemView.setOnClickListener {
           val fm = fragmentActivity!!.getSupportFragmentManager();

            val updateBookFragment= UpdateBookFragment(listFilteredCountries!!.get(position))
            fm.beginTransaction().replace(R.id.frame,updateBookFragment ).addToBackStack(" ").commit();
        }
    }

    override fun getItemCount(): Int {
        return listFilteredCountries!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvBookName: TextView
        var tvBookAuther: TextView
        var tvBookYears: TextView
        var imgDelete: ImageView

        init {
            tvBookName = itemView.findViewById(R.id.bookName)
            tvBookYears = itemView.findViewById(R.id.bookYears)
            tvBookAuther = itemView.findViewById(R.id.bookAuther)
            imgDelete = itemView.findViewById(R.id.imgDelete)
        }
    }

    fun getFilter(): Filter? {
        return countriesFilter
    }

    inner class CountriesFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val searchQuery = charSequence.toString()
            val filterResults = FilterResults()
            val tempList: MutableList<Book> = ArrayList()
            for (book in booksLists!!) {
                if (book.name.contains(searchQuery)) {
                    tempList.add(book)
                }
            }
            filterResults.values = tempList
            filterResults.count = tempList.size
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            listFilteredCountries = filterResults.values as ArrayList<Book>
            notifyDataSetChanged()
        }
    }

}
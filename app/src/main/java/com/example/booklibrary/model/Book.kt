package com.example.booklibrary.model

data class Book(val id: Int = -1,
                val name: String, val author: String, val year: String

                ,val category: String, val description: String, val language: String
                ,val number_of_pages: String, val copies_number: String,
                val shelf: String,val isFav:String,val rental_date:String)


    {
    constructor():this(-1,"","","","","",
        "","","","","false","")
}

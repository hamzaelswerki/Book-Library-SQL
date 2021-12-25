package com.example.booklibrary.model

data class User(val id: Int = -1, val name: String, val email: String, val password: String,val img:String){
    constructor():this(-1,"","","","")
}

package com.example.booklibrary.dataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.booklibrary.model.User
import com.example.booklibrary.model.Book
import java.util.*

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_IMG + " TEXT"

            + ")")


    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"



    private val CREATE_BOOK_TABLE = ("CREATE TABLE " + TABLE_BOOK + "("
            + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            COLUMN_BOOK_NAME + " TEXT," +
            COLUMN_BOOK_AUTHOR + " TEXT," +
            COLUMN_BOOK_YEAR + " TEXT," +
            COLUMN_BOOK_CATEGORY + " TEXT," +
            COLUMN_BOOKE_DESCRIPTION + " TEXT," +
            COLUMN_BOOK_LANGUAGE + " TEXT," +
            COLUMN_BOOK_NUMBER_PAGES + " TEXT," +
            COLUMN_BOOK_COPIES_NUMBER + " TEXT," +
            COLUMN_BOOK_SHELF + " TEXT,"+
            COLUMN_BOOK_ISFAV + " TEXT,"+
            COLUMN_BOOK_RENTAL_DATE + " TEXT"

            + ")")

    private val DROP_BOOK_TABLE = "DROP TABLE IF EXISTS $TABLE_BOOK"
    companion object {
        private val DATABASE_VERSION = 6
        private val DATABASE_NAME = "mydatabase1.db"
        private val TABLE_USER = "user"
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
        private val COLUMN_USER_IMG = "user_IMG"


        private val TABLE_BOOK = "book"
        private val COLUMN_BOOK_ID = "book_id"

        private val COLUMN_BOOK_NAME = "book_name"
        private val COLUMN_BOOK_AUTHOR = "book_author"
        private val COLUMN_BOOK_YEAR = "book_year"

        private val COLUMN_BOOK_CATEGORY = "book_category"
        private val COLUMN_BOOKE_DESCRIPTION = "book_description"
        private val COLUMN_BOOK_LANGUAGE = "book_language"

        private val COLUMN_BOOK_NUMBER_PAGES = "book_number_of_pages"
        private val COLUMN_BOOK_COPIES_NUMBER = "book_copies_number"
        private val COLUMN_BOOK_SHELF =  "book_shelf"
        private val COLUMN_BOOK_ISFAV =  "book_isFav"
        private val COLUMN_BOOK_RENTAL_DATE =  "book_rental_date"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_BOOK_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_BOOK_TABLE)
        onCreate(db)
    }




    fun insertUser(user: User):Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_USER_IMG, user.img)
      val x=  db.insert(TABLE_USER, null, values)>0
        db.close()
        return  x;
    }
    fun updateUser(user: User) :Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_USER_IMG, user.img)
       var x= db.update(
            TABLE_USER, values, "$COLUMN_USER_ID = ?", arrayOf(user.id.toString()))>0
        db.close()
        return x;
    }
    fun checkUser(email: String, password: String): Boolean {
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }
    fun checkUser(email: String): Boolean {
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }
    fun getUser(email: String?): User {
var user= User()
        val db = this.writableDatabase
        val selectionArgs = arrayOf(email)
        val cursor = db.rawQuery(
        "select * from "+ TABLE_USER +" where user_email = ? ", selectionArgs);
        cursor.moveToFirst();
        if (cursor!=null&&cursor.moveToFirst()&& cursor.getCount() >0) {
          user = User(
              id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
              name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
              email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
              password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
              img = cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMG))
          )
        }else{
            Log.d("ttt", "eror")
        }
        cursor.close()
        db.close()
            return user
    }
    fun insertBook(book: Book):Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_BOOK_NAME, book.name)
        values.put(COLUMN_BOOK_AUTHOR, book.author)
        values.put(COLUMN_BOOK_YEAR, book.year)

        values.put(COLUMN_BOOK_CATEGORY, book.category)
        values.put(COLUMN_BOOKE_DESCRIPTION, book.description)
        values.put(COLUMN_BOOK_LANGUAGE, book.language)

        values.put(COLUMN_BOOK_NUMBER_PAGES, book.number_of_pages)
        values.put(COLUMN_BOOK_COPIES_NUMBER, book.copies_number)
        values.put(COLUMN_BOOK_SHELF, book.shelf)
        values.put(COLUMN_BOOK_ISFAV, book.isFav)
        values.put(COLUMN_BOOK_RENTAL_DATE, book.rental_date)


        val x=  db.insert(TABLE_BOOK, null, values)>0
        db.close()
        return  x;
    }
    fun updateBook(book: Book) :Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_BOOK_NAME, book.name)
        values.put(COLUMN_BOOK_AUTHOR, book.author)
        values.put(COLUMN_BOOK_YEAR, book.year)

        values.put(COLUMN_BOOK_CATEGORY, book.category)
        values.put(COLUMN_BOOKE_DESCRIPTION, book.description)
        values.put(COLUMN_BOOK_LANGUAGE, book.language)

        values.put(COLUMN_BOOK_NUMBER_PAGES, book.number_of_pages)
        values.put(COLUMN_BOOK_COPIES_NUMBER, book.copies_number)
        values.put(COLUMN_BOOK_SHELF, book.shelf)
        values.put(COLUMN_BOOK_ISFAV, book.isFav)
        values.put(COLUMN_BOOK_RENTAL_DATE, book.rental_date)
        var x= db.update(TABLE_BOOK, values, "$COLUMN_BOOK_ID = ${book.id}", null)>0
        db.close()
        return x;
    }
    fun getBooks(category: String): ArrayList<Book> {
        val bookList = ArrayList<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from $TABLE_BOOK",null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast){

            var book = Book(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME)),
                    author =  cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)),
                    year = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_YEAR)),

                    category =  cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_CATEGORY)),
                    description = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKE_DESCRIPTION)),
                    language =cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_LANGUAGE)),

                    number_of_pages = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER_PAGES)),
                    copies_number = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_COPIES_NUMBER)),
                    shelf =cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_SHELF)),
                    isFav = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ISFAV)),
                    rental_date = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_RENTAL_DATE)),
                    )
            bookList.add(book)
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return bookList
    }
    fun getFavBooks(): ArrayList<Book> {
        val bookList = ArrayList<Book>()
        val db = this.readableDatabase
       val cursor = db.rawQuery("select * from $TABLE_BOOK where $COLUMN_BOOK_ISFAV='true'",null)

        cursor.moveToFirst()

        while (!cursor.isAfterLast){

            var book = Book(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME)),
                    author =  cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)),
                    year = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_YEAR)),

                    category =  cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_CATEGORY)),
                    description = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKE_DESCRIPTION)),
                    language =cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_LANGUAGE)),

                    number_of_pages = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER_PAGES)),
                    copies_number = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_COPIES_NUMBER)),
                    shelf =cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_SHELF)),
                    isFav = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ISFAV)),
                    rental_date = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_RENTAL_DATE)),
                    )
            bookList.add(book)
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return bookList
    }
    fun deleteBook(id:Int): Boolean {
        val db = this.writableDatabase

        return db.delete(TABLE_BOOK,"$COLUMN_BOOK_ID = $id", null)>0
    }


}